<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ul id="videoNewsesList" class="videoNewses">
	<c:forEach var="videoNews" items="${videoNewses}" varStatus="index">
		<li class="videoblock  <c:if test="${index.count % 4 == 0}">last</c:if>">
			<div class="wrap">
				<a href='<c:url value="${videoNews.playVideoUrl}"/>' title="${videoNews.title_ru}" class="img">
					<img src="${videoNews.imgUrl}.jpg" alt="${videoNews.title_ru}"
						class="rotating"
						onmouseover="startThumbChange(this.src, '${videoNews.id}', '0')"
						onmouseout="endThumbChange(this.src, '${videoNews.id}')"
						id="${videoNews.id}"> 
				</a>
				<h5 class="title" title="Eugen">
					<a href='<c:url value="${videoNews.playVideoUrl}"/>' title="${videoNews.title_ru}" class="title">${videoNews.title_ru}</a>
				</h5>
				<c:choose>
					<c:when test="${videoNews.rating > 50}">
						<div class="rating-container up">
							<div class="value">${videoNews.rating}%</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="rating-container down">
							<div class="value">${videoNews.rating}%</div>
						</div>
					</c:otherwise>
				</c:choose>
				<var class="duration">${videoNews.formatedDuration}</var>
				<span class="views"><var>${videoNews.views}</var>&nbsp;<spring:message code="views"/></span>
				<var class="added"><fmt:formatDate pattern="dd.MM.yy" value="${videoNews.addedDate}"/></var>
			</div>
		</li>
	</c:forEach>
</ul>