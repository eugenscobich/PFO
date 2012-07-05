package ru.pfo.tag;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;

import ru.pfo.Constant;
import ru.pfo.model.VideoNews;
import ru.pfo.util.PropertiesUtil;

public class NewsListTag extends BaseTag {

	private final static Logger LOG = Logger.getLogger(NewsListTag.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private WebApplicationContext _applicationContext;

	public int doStartTag() {
		test();
		/*
		_applicationContext = RequestContextUtils.getWebApplicationContext(pageContext.getRequest(),
				pageContext.getServletContext());
		try {
			JspWriter out = pageContext.getOut();
			out.print("<ul id=\"videoNewsesList\" class=\"videoNewses\">");
			int i = 1;
			for (VideoNews videoNews : getVideoNewses()) {
				HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
				String hrefUrl = request.getContextPath()
						+ PropertiesUtil.getProperty(Constant.PROPS_KEY_PLAY_PAGE_URL) + "/" + videoNews.getTitle_tr()
						+ "?videoId=" + videoNews.getId();
				if (i % 4 != 0) {
					out.print("<li class=\"videoblock\">");
				} else {
					out.print("<li class=\"videoblock last\">");
				}
				i++;
				out.print("		<div class=\"wrap\">");
				out.print("			<a class=\"img\" title=\"" + videoNews.getTitle_ru() + "\" href=\"" + hrefUrl + "\">");
				out.print("				<img id=\"" + videoNews.getId() + "\" onmouseout=\"endThumbChange('"
						+ videoNews.getImgUrl() + "', '" + videoNews.getId() + "')\""
						+ "onmouseover=\"startThumbChange('" + videoNews.getImgUrl() + "', '" + videoNews.getId()
						+ "', '0')\" class=\"rotating\" alt=\"" + videoNews.getTitle_ru() + "\"" + "src=\""
						+ videoNews.getImgUrl() + ".jpg\">");
				out.print("			</a>");
				out.print("			<h5 class=\"title\">");
				out.print("				<a class=\"title\" title=\"" + videoNews.getTitle_ru() + "\" href=\"" + hrefUrl + "\">"
						+ videoNews.getTitle_ru() + "</a>");
				out.print("			</h5>");
				out.print("			<div class=\"rating-container " + (videoNews.getRating() > 50 ? "up" : "down") + "\">");
				out.print("				<div class=\"value\">" + videoNews.getRating() + "%</div>");
				out.print("			</div>");
				int min = videoNews.getDuration() / 60;
				int sec = videoNews.getDuration() - min * 60;

				out.print("			<var class=\"duration\">" + String.format("%02d", min) + ":" + String.format("%02d", sec)
						+ "</var>");

				String pr = _applicationContext.getMessage("views", null, "просмотов", pageContext.getRequest()
						.getLocale());

				out.print("			<span class=\"views\"><var>" + videoNews.getViews() + "</var> " + pr + "</span>");

				SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy", pageContext.getRequest().getLocale());
				String date = formatter.format(videoNews.getAddedDate());
				out.print("			<var class=\"added\">" + date + "</var>");
				out.print("			</div>");
				out.print("			</li>");
			}

			out.print("</ul>");
		} catch (IOException e) {
			LOG.error(e, e);
		}*/
		return (SKIP_BODY);
	}

	private void test() {
		JspWriter out = pageContext.getOut();
		for (VideoNews videoNews: getVideoNewses()) {
			String videoUrl =  videoNews.getId() + " " + videoNews.getTitle_tr() + ".html";
			try {
				String playVideoUrl = PropertiesUtil.getProperty(Constant.PROPS_KEY_PLAY_PAGE_URL) + "/" + URLEncoder.encode(videoUrl, "UTF-8");
				videoNews.setPlayVideoUrl(playVideoUrl);
			} catch (UnsupportedEncodingException e) {
				LOG.error(e, e);
			}
		}
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("videoNewses", getVideoNewses());
		try {
			String output = getOutputOfJspFile("/WEB-INF/tag/newsList.jsp", attributes);
			out.print(output);
			out.flush();
		} catch (Exception e) {
			LOG.error(e, e);
		}
	}

	protected List<VideoNews> videoNewses = new ArrayList<VideoNews>();

	public List<VideoNews> getVideoNewses() {
		return videoNewses;
	}

	public void setVideoNewses(List<VideoNews> videoNewses) {
		this.videoNewses = videoNewses;
	}

}
