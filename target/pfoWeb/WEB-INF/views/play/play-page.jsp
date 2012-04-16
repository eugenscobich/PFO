<%@page import="ru.pfo.model.VideoNews"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" src="<c:url value="/resources/js/jwplayer.js"/>"></script>
<div class="video-player-header">
	<div class="video-title-h1"><h1>${videoNews.title_ru}</h1></div>
	<div id="videoPlayer">Loading the player ...</div>
	<script type="text/javascript">
		jwplayer("videoPlayer").setup({
			flashplayer : '<c:url value="/resources/other/player.swf"/>',
			file : "${videoNews.videoUrl}",
			height : 552,
			width : 736,
			autostart: true,
			provider:'http',
			"http.startparam":"fs",
			skin: '<c:url value="/resources/other/newtubedark.zip"/>'
		});
	</script>
</div>
<div class="video-description" >
	<div>
		<p><spring:message code="categories"/>: 
			<span>
				<a href='<c:url value="/video?category=${categories[0].id}"/>'>${categories[0].name}</a>
				<c:forEach var="category" items="${categories.subList(1, categories.size())}">, <a href='<c:url value="/video?category=${category.id}"/>'>${category.name}</a></c:forEach>
			</span>
		</p>
		<%
			VideoNews vn = (VideoNews)(request.getAttribute("videoNews"));
		%>
		<p><spring:message code="duration"/>: <span><fmt:formatDate value="<%=new java.util.Date((vn.getDuration() - 1) * 1000)%>" pattern="mm:ss"/></span></p>
		<p><spring:message code="views"/>: <span>${videoNews.views}</span></p>
		<p><spring:message code="rating"/>: <span>${videoNews.rating}%</span></p>
		<p><spring:message code="added"/>: <span><fmt:formatDate value="${videoNews.addedDate}" pattern="dd.MM.yy HH:mm"/></span></p>
	</div>
</div>