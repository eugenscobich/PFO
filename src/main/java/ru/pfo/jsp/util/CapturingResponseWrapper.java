package ru.pfo.jsp.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.mock.web.DelegatingServletOutputStream;

public class CapturingResponseWrapper extends HttpServletResponseWrapper {

	private final OutputStream buffer;

	private PrintWriter writer;
	private ServletOutputStream outputStream;

	public CapturingResponseWrapper(HttpServletResponse response, OutputStream buffer) {
		super(response);
		this.buffer = buffer;
	}

	@Override
	public ServletOutputStream getOutputStream() {
		if (outputStream == null) {
			outputStream = new DelegatingServletOutputStream(buffer);
		}
		return outputStream;
	}

	@Override
	public PrintWriter getWriter() {
		if (writer == null) {
			writer = new PrintWriter(buffer);
		}
		return writer;
	}

	@Override
	public void flushBuffer() throws IOException {
		if (writer != null) {
			writer.flush();
		}
		if (outputStream != null) {
			outputStream.flush();
		}
	}
}
