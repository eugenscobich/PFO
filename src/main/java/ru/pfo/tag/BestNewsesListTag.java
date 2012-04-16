package ru.pfo.tag;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import ru.pfo.Constant;
import ru.pfo.model.VideoNews;
import ru.pfo.util.PropertiesUtil;

public class BestNewsesListTag extends TagSupport {

	private final static Logger LOG = Logger.getLogger(BestNewsesListTag.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int doStartTag() {

		try {
			JspWriter out = pageContext.getOut();
			out.print("<ul id=\"slider-list\">");

			for (VideoNews videoNews : getVideoNewses()) {
				HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
				String videoUrl =  videoNews.getId() + " " + videoNews.getTitle_tr() + ".html";
				String hrefUrl = request.getContextPath() + PropertiesUtil.getProperty(Constant.PROPS_KEY_PLAY_PAGE_URL) 
						+ "/" + URLEncoder.encode(videoUrl, "UTF-8");

				out.print("<li class=\"videoblock\">");
				out.print("			<a class=\"img\" title=\"" + videoNews.getTitle_ru() + "\" href=\"" + hrefUrl + "\">");
				out.print("				<img id=\"best" + videoNews.getId() + "\" onmouseout=\"endThumbChange(this.src, 'best" + videoNews.getId() + "')\""
						+ "onmouseover=\"startThumbChange(this.src, 'best" + videoNews.getId()
						+ "', '0')\" class=\"rotating\" alt=\"" + videoNews.getTitle_ru() + "\"" + "src=\""
						+ videoNews.getImgUrl() + ".jpg\">");
				out.print("			</a>");
				out.print("</li>");
			}
			out.print("</ul>");
		} catch (IOException e) {
			LOG.error(e, e);
		}
		return (SKIP_BODY);
	}

	protected List<VideoNews> videoNewses = new ArrayList<VideoNews>();

	public List<VideoNews> getVideoNewses() {
		return videoNewses;
	}

	public void setVideoNewses(List<VideoNews> videoNewses) {
		this.videoNewses = videoNewses;
	}

}
