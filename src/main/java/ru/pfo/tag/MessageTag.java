package ru.pfo.tag;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class MessageTag extends TagSupport {

	private final static Logger LOG = Logger.getLogger(MessageTag.class);

	private String code;
	private String arguments;

	private static final long serialVersionUID = 1L;

	private WebApplicationContext _applicationContext;

	public int doStartTag() {
		_applicationContext = RequestContextUtils.getWebApplicationContext(pageContext.getRequest(),
				pageContext.getServletContext());
		try {
			JspWriter out = pageContext.getOut();
			String[] arg = null;
			if (arguments != null) {
				arg = arguments.split(",");
			}
			String message = _applicationContext.getMessage(code, arg, "error", pageContext.getRequest().getLocale());
			Pattern pattern = Pattern.compile("`.*?`");
			Matcher matcher = pattern.matcher(message);
			while (matcher.find()) {
				String group = matcher.group();
				String newStr = "<strong>" + group.substring(1, group.length() - 1) + "</strong>";
				message = message.replace(group, newStr);
			}
			out.print(message);
		} catch (IOException e) {
			LOG.error(e, e);
		}
		return (SKIP_BODY);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getArguments() {
		return arguments;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

}
