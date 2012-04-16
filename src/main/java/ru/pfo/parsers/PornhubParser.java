package ru.pfo.parsers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.pfo.model.Category;
import ru.pfo.model.VideoNews;
import ru.pfo.service.ICategoryService;
import ru.pfo.service.ISettingsService;
import ru.pfo.service.ITranslator;
import ru.pfo.service.IVideoNewsService;

@Component("pornhubParser")
public class PornhubParser implements IParser {

	private final Logger LOG = Logger.getLogger(PornhubParser.class);

	private static final String SITE_URL = "http://www.pornhub.com";

	@Autowired
	private ISettingsService settingsService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IVideoNewsService videoNewsService;

	@Autowired
	private ITranslator translator;

	@Override
	public void run() {
		LOG.info("Start run parser");
		int numberOfPages = settingsService.getSettings().getNumberOfPages();

		Elements newses = new Elements();

		for (int i = 1; i <= numberOfPages; i++) {
			Document doc = null;
			try {
				doc = Jsoup.connect(SITE_URL + "/video?page=" + i).userAgent("Mozilla").get();
				Element ul = doc.select("ul.nf-videos").get(0);
				newses.addAll(ul.getElementsByTag("li"));
			} catch (Exception e) {
				LOG.error(e, e);
				continue;
			}
		}

		List<VideoNews> videoNewses = parseVideoNewses(newses);
		parseCategoryAndSaveAll(videoNewses);
		LOG.info("End run parser");
	}

	private List<VideoNews> parseVideoNewses(Elements newses) {
		List<VideoNews> videoNewses = new ArrayList<VideoNews>();

		long refreshInt = settingsService.getSettings().getRefreshInterval();
		long timeInterval = refreshInt / newses.size();
		long dateTime = refreshInt;

		for (Element news : newses) {
			try {
				// Parse viewKey
				Element a = news.select("a.img").get(0);
				String url = a.attr("href");
				int i = url.indexOf("viewkey=");
				url = url.substring(i + 8, url.length());
				if (videoNewsService.isExist(url)) {
					LOG.debug("The videoNews with video key: " + url + " already exist. Skip.");
					continue;
				}
				VideoNews vn = new VideoNews();
				vn.setVideoKey(url);
				// Parse titles
				String title = a.attr("title");
				vn.setTitle(title);
				String[] translatedTitles = translator.getTranslatedText(title);
				vn.setTitle_ru(translatedTitles[0]);
				vn.setTitle_tr(translatedTitles[1]);
				// Parse Image src
				Element img = a.select("img.rotating").get(0);
				String imgSrc = img.attr("src");
				i = imgSrc.indexOf("?");
				imgSrc = imgSrc.substring(0, i - 4);
				vn.setImgUrl(imgSrc);
				// Parse rating
				Element div = news.select("div.rating-container div.value").get(0);
				String ratingText = div.text();
				ratingText = ratingText.substring(0, ratingText.length() - 1);
				Integer rating = Integer.valueOf(ratingText);
				vn.setRating(rating);
				// Parse duration
				Element var = news.select("var.duration").get(0);
				String durationText = var.text();
				String[] minsec = durationText.split(":");
				int min = Integer.parseInt(minsec[0]);
				int sec = Integer.parseInt(minsec[1]);
				Integer duration = min * 60 + sec;
				vn.setDuration(duration);
				// Set time
				Date nowDate = new Date();
				Date d = new Date(nowDate.getTime() + dateTime * 1000);
				vn.setAddedDate(d);
				dateTime -= timeInterval;

				// Parse views
				// var = news.select("span.views var").get(0);
				// String viewsText = var.text();
				// Long views = Long.valueOf(viewsText);
				// vn.setViews(views);
				vn.setViews(0l);

				vn.setHiden(true);
				videoNewses.add(vn);
			} catch (Exception e) {
				LOG.error(e, e);
				continue;
			}

		}
		return videoNewses;
	}

	private List<Category> parseCategoryAndSaveAll(List<VideoNews> videoNewses) {
		List<Category> categories = new ArrayList<Category>();

		for (VideoNews videoNews : videoNewses) {
			try {
				Document doc = Jsoup.connect(SITE_URL + "/view_video.php?viewkey=" + videoNews.getVideoKey())
						.userAgent("Mozilla").get();

				videoNews.setVideoUrl(getVideoUrl(doc));
				videoNews.setLastUrlUpdate(new Date());

				videoNewsService.save(videoNews);
				LOG.debug("Added NEW videoNews with key: " + videoNews.getVideoKey());

				Elements elements = doc.select("div.video-info-block p");
				Element p = null;
				for (Element element : elements) {
					if (element.tagName().equals("p") && element.text().indexOf("Categories") >= 0) {
						p = element;
						break;
					} else {
						new Exception("No category found.");
					}
				}

				elements = p.getElementsByTag("a");
				for (Element element : elements) {
					String href = element.attr("href");
					int i = href.indexOf("=");
					String categoryStr = href.substring(i + 1, href.length());

					if (categoryService.addVideoNewsToCategory(videoNews, categoryStr)) {
						LOG.debug("Added videoNews with key: " + videoNews.getVideoKey() + " to category with uuid: "
								+ categoryStr);
						continue;
					}
					Category category = new Category();
					String name_ru = translator.getTranslatedText(element.text())[0];
					category.setName(name_ru);
					category.setUuid(categoryStr);
					category.getVideoNewses().add(videoNews);
					categoryService.save(category);
					LOG.debug("Added videoNews with key: " + videoNews.getVideoKey() + " to NEW category with uuid: "
							+ category.getUuid());
				}

			} catch (Exception e) {
				LOG.error(e, e);
				continue;
			}
		}
		return categories;
	}

	private String getVideoUrl(Document doc) throws UnsupportedEncodingException {
		if (doc == null) {
			return null;
		}

		// Get videoUrl
		Element div = doc.select("div.video-wrapper").get(0);
		String flashVar = div.toString();
		int k = flashVar.indexOf("video_url");
		String temp = flashVar.substring(k, flashVar.length());
		int p = temp.indexOf("http");
		flashVar = temp.substring(p, temp.length());
		int j = flashVar.indexOf("\"");
		String videoUrl = flashVar.substring(0, j);
		videoUrl = URLDecoder.decode(videoUrl, "UTF-8");
		LOG.debug("Found video URL: " + videoUrl);
		return videoUrl;
	}

	@Override
	public String getVideoUrl(VideoNews videoNews) {
		Document doc = null;
		try {
			doc = Jsoup.connect(SITE_URL + "/view_video.php?viewkey=" + videoNews.getVideoKey()).userAgent("Mozilla")
					.get();
			return getVideoUrl(doc);
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

}
