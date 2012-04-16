<%@page import="ru.pfo.Constant"%>
<%@page import="ru.pfo.util.PropertiesUtil"%>
<%@page import="ru.pfo.model.VideoNews"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<br/>
<h2>Administration - Edit hiden Video News (Remaining: ${nr})</h2>
<br/>
<div id="admin">
<c:if test="${not empty videoNews}">
	<c:url var="submit" value="/admin/editVideoNews" />
	<form:form commandName="videoNews" action="${submit}" method="POST" class="edit-videoNews">
		<ul>
			<li class="videoblock">
				<%
				VideoNews vn = (VideoNews)request.getAttribute("videoNews");
				String hrefUrl = request.getContextPath() 
						+ PropertiesUtil.getProperty(Constant.PROPS_KEY_PLAY_PAGE_URL) + "/" + vn.getTitle_tr()
				+ "?videoId=" + vn.getId();
				
				%>
				<a class="img" title="${videoNews.title}" href="<%=hrefUrl%>">
					<img id="admin${videoNews.id}" onmouseout="endThumbChange('${videoNews.imgUrl}', 'admin${videoNews.id}')"
					 onmouseover="startThumbChange('${videoNews.imgUrl}', 'admin${videoNews.id}', '0')" 
					 class="rotating" alt="" src="${videoNews.imgUrl}.jpg">
				</a>
			</li>
		
		
			<li>
				<div class="title">Title:</div>
				<div class="content">${videoNews.title}</div>
			</li>
			<li>
				<div class="title">Title ru:</div>
				<div class="content"><form:input path="title_ru" class="width400"/></div>
			</li>
			<li>
				<div class="title">Title tr:</div>
				<div class="content"><form:input path="title_tr" class="width400"/></div>
			</li>
		</ul>
		<div><input type="submit" value="Save"/></div>
	</form:form>
</c:if>
</div>