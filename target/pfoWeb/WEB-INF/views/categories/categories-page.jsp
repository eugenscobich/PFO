<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
	<div class="video-title-h1 text-center"><h1><spring:message code="categories"/></h1></div>
	<ul class="categories-list">
		<c:forEach var="category" items="${categories}" varStatus="rowCounter">
			<c:choose>
	          <c:when test="${rowCounter.count % 4 == 0}">
	            <c:set var="styleCalss" value=" last"/>
	          </c:when>
	          <c:otherwise>
	            <c:set var="styleCalss" value=""/>
	          </c:otherwise>
	        </c:choose>

			<li class="cat_pic${styleCalss}">
				<a href='<c:url value="/video?category=${category.id}"/>'>
					<img alt="" src="http://cdn1.static.pornhub.phncdn.com/images/categories/${category.uuid}.jpg">
					<span class="cat_overlay png"></span>
				</a>
				<h5>
					<a class="png" href="<c:url value="/video?category=${category.id}"/>"><strong>${category.name}</strong>
					<span>(<var>${category.size}</var>)</span></a>
				</h5>
			</li>
		</c:forEach>
	</ul>
</div>



