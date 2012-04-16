package ru.pfo.tag;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TagSupport;

import ru.pfo.jsp.util.CapturingResponseWrapper;

public class BaseTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	protected String getOutputOfJspFile(String fileName, Map<String, Object> attributes)
			throws ServletException, IOException {
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
		
		for (String attr: attributes.keySet()) {
			request.setAttribute(attr, attributes.get(attr));
		}
		ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
		CapturingResponseWrapper responseWrapper = new CapturingResponseWrapper(response, bufferStream);
		ServletContext servletContext = pageContext.getServletContext();
		servletContext.getRequestDispatcher(fileName).include(request, responseWrapper);
		responseWrapper.flushBuffer();
		byte[] buffer = bufferStream.toByteArray();
		return new String(buffer);
	}
}
