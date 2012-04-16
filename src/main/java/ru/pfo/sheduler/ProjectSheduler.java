package ru.pfo.sheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import ru.pfo.parsers.IParser;
import ru.pfo.service.ISettingsService;

@Component("projectSheduler")
public class ProjectSheduler {

	private final Logger LOG = Logger.getLogger(ProjectSheduler.class);

	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	private long shutdownTimeout = 30l;

	@Autowired
	private ISettingsService settingsService;

	@Autowired
	private IParser pornhubParser;

	private String cronExpresion;

	@PostConstruct
	public void start() {
		cronExpresion = settingsService.getSettings().getCronExpresion();
		startWithNewCronExpresion(cronExpresion);
	}

	public void startWithNewCronExpresion(String cronExpresion) {
		this.cronExpresion = cronExpresion;
		threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setThreadNamePrefix("SpringCronJob");
		threadPoolTaskScheduler.initialize();
		threadPoolTaskScheduler.schedule(pornhubParser, new CronTrigger(cronExpresion));
	}

	@PreDestroy
	public void stop() {
		ScheduledExecutorService scheduledExecutorService = threadPoolTaskScheduler.getScheduledExecutor();
		try {
			scheduledExecutorService.shutdownNow();
			if (!scheduledExecutorService.awaitTermination(shutdownTimeout, TimeUnit.SECONDS)) {
				LOG.error("Thread Pool Task Scheduler did not terminate");
			}
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}

	}

	/*
	 * @Override public void run() { LOG.info("Sheduling"); String sitePageUrl =
	 * PropertiesUtil.getProperty("site-page-url"); int numberOfPages =
	 * settingsService.getSettings().getNumberOfPages(); String
	 * siteNewsesSelector = PropertiesUtil.getProperty("site-newses-selector");
	 * String siteNewsSelector =
	 * PropertiesUtil.getProperty("site-news-selector");
	 * 
	 * Elements newses = new Elements();
	 * 
	 * for (int i = 1; i <= numberOfPages; i++) { Document doc = null; try { doc
	 * =
	 * Jsoup.connect(sitePageUrl.concat(Integer.toString(i))).userAgent("Mozilla"
	 * ).get(); } catch (IOException e) { LOG.error(e, e); continue; } Elements
	 * newsHeadlines = doc.select(siteNewsesSelector); Element ul = null; try {
	 * ul = newsHeadlines.get(0); } catch (IndexOutOfBoundsException e) {
	 * LOG.error(e, e); continue; }
	 * newses.addAll(ul.getElementsByTag(siteNewsSelector)); }
	 * 
	 * List<VideoNews> videoNewses = parseNewses(newses);
	 * videoNewsService.removeExistingNewses(videoNewses); List<Category>
	 * categories = parseCategory(videoNewses);
	 * videoNewsService.persistVideoNewses(videoNewses);
	 * categoryService.persistCategory(categories); }
	 * 
	 * private List<Category> parseCategory(List<VideoNews> videoNewses) {
	 * 
	 * List<Category> categories = new ArrayList<Category>();
	 * 
	 * String siteUrl = PropertiesUtil.getProperty("site-url"); String
	 * categorySelector = PropertiesUtil.getProperty("category-selector");
	 * 
	 * for (VideoNews videoNews : videoNewses) { try { Document doc =
	 * Jsoup.connect
	 * (siteUrl.concat(videoNews.getVideoKey())).userAgent("Mozilla").get();
	 * String videoUrl = videoNewsService.getVideoUrl(doc);
	 * videoNews.setVideoUrl(videoUrl); videoNews.setLastUrlUpdate(new Date());
	 * Elements elements = doc.select(categorySelector); Element p = null; for
	 * (Element element : elements) { if (element.tagName().equals("p") &&
	 * element.text().indexOf("Categories") >= 0) { p = element; break; } else {
	 * new Exception("No category found."); } }
	 * 
	 * elements = p.getElementsByTag("a");
	 * 
	 * for (Element element : elements) { String href = element.attr("href");
	 * int i = href.indexOf("="); String categoryStr = href.substring(i + 1,
	 * href.length()); boolean exist = false; for (Category category :
	 * categories) { if (category.getUuid().equals(categoryStr)) {
	 * category.getVideoNewses().add(videoNews); exist = true; } } if (!exist) {
	 * Category category = new Category(); String content =
	 * GoogleTranslate.translate(element.text(), Language.ENGLISH,
	 * Language.RUSSIAN); String name = content.substring(4); i =
	 * name.indexOf("\","); String name_ru = name.substring(0, i); String s =
	 * name_ru.substring(0, 1); s = s.toUpperCase(); name_ru =
	 * s.concat(name_ru.substring(1, name_ru.length()));
	 * category.setName(name_ru); category.setUuid(categoryStr);
	 * category.getVideoNewses().add(videoNews); categories.add(category); }
	 * 
	 * }
	 * 
	 * } catch (Exception e) { LOG.error(e, e); continue; } } return categories;
	 * }
	 * 
	 * private List<VideoNews> parseNewses(Elements newses) { List<VideoNews>
	 * videoNewses = new ArrayList<VideoNews>(); String viewKeySelector =
	 * PropertiesUtil.getProperty("view-key-selector"); String siteTitleSelector
	 * = PropertiesUtil.getProperty("site-title-selector"); String
	 * imageUrlSelector = PropertiesUtil.getProperty("image-url-selector");
	 * String ratingSelector = PropertiesUtil.getProperty("rating-selector");
	 * String durationSelector =
	 * PropertiesUtil.getProperty("duration-selector");
	 * 
	 * String viewsSelector = PropertiesUtil.getProperty("views-selector"); long
	 * refreshInt = settingsService.getSettings().getRefreshInterval(); long
	 * timeInterval = refreshInt / newses.size(); long dateTime = refreshInt;
	 * for (Element news : newses) { try { VideoNews vn = new VideoNews(); //
	 * Parse viewKey Elements elements = news.select(viewKeySelector); Element a
	 * = elements.get(0); String url = a.attr("href"); int i = url.indexOf("=");
	 * url = url.substring(i + 1, url.length()); vn.setVideoKey(url); // Parse
	 * titles elements = news.select(siteTitleSelector); a = elements.get(0);
	 * parseTitles(vn, a.text()); // Parse Image src elements =
	 * news.select(imageUrlSelector); Element img = elements.get(0); String
	 * imgSrc = img.attr("src"); i = imgSrc.indexOf("?"); imgSrc =
	 * imgSrc.substring(0, i - 4); vn.setImgUrl(imgSrc); // Parse rating
	 * elements = news.select(ratingSelector); Element div = elements.get(0);
	 * String ratingText = div.text(); ratingText = ratingText.substring(0,
	 * ratingText.length() - 1); Integer rating = Integer.valueOf(ratingText);
	 * vn.setRating(rating); // Parse duration elements =
	 * news.select(durationSelector); Element var = elements.get(0); String
	 * durationText = var.text(); String[] minsec = durationText.split(":"); int
	 * min = Integer.parseInt(minsec[0]); int sec = Integer.parseInt(minsec[1]);
	 * Integer duration = min * 60 + sec; vn.setDuration(duration);
	 * 
	 * // Set time Date nawDate = new Date(); Date d = new
	 * Date(nawDate.getTime() + dateTime * 1000); vn.setAddedDate(d); dateTime
	 * -= timeInterval;
	 * 
	 * // Parse views elements = news.select(viewsSelector); var =
	 * elements.get(0); String viewsText = var.text(); Long views =
	 * Long.valueOf(viewsText); vn.setViews(views);
	 * 
	 * videoNewses.add(vn);
	 * 
	 * } catch (Exception e) { LOG.error(e, e); continue; }
	 * 
	 * } return videoNewses; }
	 * 
	 * void parseTitles(VideoNews vn, String text) { String content = null; try
	 * { content = GoogleTranslate.translate(text, Language.ENGLISH,
	 * Language.RUSSIAN); } catch (Exception e) { LOG.error(e, e); content =
	 * "[[[\"" + text + "\",\"" + text + "\",\"" + text + "\",\""; }
	 * vn.setTitle(text); if (content == null || content.isEmpty()) {
	 * vn.setTitle_ru(text); vn.setTitle_tr(text); } else { String s =
	 * content.substring(4); int i = s.indexOf("\","); String title_ru =
	 * s.substring(0, i); vn.setTitle_ru(title_ru);
	 * 
	 * s = s.substring(i); s = s.substring(3); i = s.indexOf("\",");
	 * 
	 * s = s.substring(i); s = s.substring(3); i = s.indexOf("\","); String
	 * title_tr = s.substring(0, i); vn.setTitle_tr(title_tr);
	 * 
	 * } }
	 */
	public void setCronExpresion(String cronExpresion) {
		this.cronExpresion = cronExpresion;
	}

}
