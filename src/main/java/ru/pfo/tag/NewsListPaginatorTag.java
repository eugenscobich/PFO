package ru.pfo.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import ru.pfo.Constant;
import ru.pfo.util.HttpUtil;
import ru.pfo.util.PropertiesUtil;

public class NewsListPaginatorTag extends TagSupport {

	private final static Logger LOG = Logger.getLogger(NewsListPaginatorTag.class);
	private final static String PAGE = "page";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebApplicationContext _applicationContext;
	private Integer page;
	private Integer totalPages;
	private String pageUrl;

	public int doStartTag() {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		if (totalPages <= 0) {
			return SKIP_BODY;
		}
		if (pageUrl == null || pageUrl.isEmpty()) {
			pageUrl = request.getContextPath() + PropertiesUtil.getProperty(Constant.PROPS_KEY_VIDEO_PAGE_URL);
		}

		pageUrl = HttpUtil.removeAllParameters(pageUrl);

		try {
			_applicationContext = RequestContextUtils.getWebApplicationContext(pageContext.getRequest(),
					pageContext.getServletContext());
			JspWriter out = pageContext.getOut();

			int start = 1;
			int finish = 10;

			if (page > 6) {
				start = page - 5;
				finish = page + 4;
			}

			if (finish > totalPages) {
				finish = totalPages;
				if (totalPages <= 10) {
					start = 1;
				} else {
					start = finish - 9;
				}
			}

			String queryStr = request.getQueryString();
			String requestUrl = pageUrl;
			if (queryStr != null) {
				requestUrl = requestUrl + HttpUtil.QUESTION + queryStr;
			}
			requestUrl = HttpUtil.removeParameter(requestUrl, PAGE);

			out.print("<div class=\"pagination\">");
			out.print("<ul class=\"pagination orange\">");

			if (start != 1) {
				String url = HttpUtil.addParameter(requestUrl, PAGE, 1);
				out.print("<li>");
				out.print("		<a href=\"" + url + "\">");
				String first = _applicationContext.getMessage("first", null, "Первая", pageContext.getRequest()
						.getLocale());
				out.print("			<span>" + first + "</span>");
				out.print("		</a>");
				out.print("</li>");
			}

			if (page != 1) {
				int i = page - 1;
				String url = HttpUtil.addParameter(requestUrl, PAGE, i);
				out.print("<li>");
				out.print("		<a href=\"" + url + "\">");
				out.print("			<span>&lt;&lt;</span>");
				out.print("		</a>");
				out.print("</li>");
			}

			for (int i = start; i <= finish; i++) {

				String url = HttpUtil.addParameter(requestUrl, PAGE, i);

				if (i == page) {
					out.print("<li class=\"active\">");
				} else {
					out.print("<li>");
				}
				out.print("		<a href=\"" + url + "\">");
				out.print("			<span>" + i + "</span>");
				out.print("		</a>");
				out.print("</li>");
			}

			if (page != totalPages) {
				int i = page + 1;
				String url = HttpUtil.addParameter(requestUrl, PAGE, i);
				out.print("<li>");
				out.print("		<a href=\"" + url + "\">");
				out.print("			<span>&gt;&gt;</span>");
				out.print("		</a>");
				out.print("</li>");
			}

			if (finish != totalPages) {
				String url = HttpUtil.addParameter(requestUrl, PAGE, totalPages);
				out.print("<li>");
				out.print("		<a href=\"" + url + "\">");
				String last = _applicationContext.getMessage("last", null, "Последния", pageContext.getRequest()
						.getLocale());
				out.print("			<span>" + last + "</span>");
				out.print("		</a>");
				out.print("</li>");
			}
			out.print("</ul>");
			out.print("</div>");

		} catch (IOException e) {
			LOG.error(e, e);
		}
		return (SKIP_BODY);
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
}
