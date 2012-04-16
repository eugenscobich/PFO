<%@page import="java.net.URLEncoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
String path = (String) request.getAttribute("javax.servlet.error.message"); 
Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
path = "/error?message=" + URLEncoder.encode(path, "UTF-8") + "&code=" + code.toString();%>
<c:redirect url="<%=path%>"/>

