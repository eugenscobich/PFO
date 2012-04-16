<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insertDefinition name="search-page"/>
<div class="bloc-header">
	<span class="bloc-header"><spring:message code="categories"/></span>
	<ul class="category orange">
		<c:choose>
			<c:when test="${empty param.category}">
				<li class="active">
					<a href='<c:url value="/video"/>'>
						<span><spring:message code="all" text="Все"/></span>
				 	</a>
				 </li>
			</c:when>
			<c:otherwise>
				<li>
					<a href='<c:url value="/video"/>'>
						<span><spring:message code="all" text="Все"/></span>
				 	</a>
				 </li>
			</c:otherwise>
		</c:choose>
	
		<c:forEach var="category" items="${categories}">
			<c:choose>
				<c:when test="${category.id == param.category}">
					<li class="active">
						<a href='<c:url value="/video?category=${category.id}"/>'>
							<span>${category.name}</span>
				 		</a>
				 	</li>
				</c:when>
				<c:otherwise>
					<li>
						<a href='<c:url value="/video?category=${category.id}"/>'>
							<span>${category.name}</span>
				 		</a>
				 	</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
</div>