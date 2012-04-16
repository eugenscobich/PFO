<%@ taglib uri="http://www.pfo.ru/tags" prefix="nl" %><%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.pfo.ru/tags" prefix="nl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${not empty message}">
<h3>${message}</h3>
</c:if>

<nl:newslist videoNewses="${videoNewses}"/>
<c:url value="/search?query=${query}" var="searchPage"/>
<nl:newslistpaginator page="${page}" totalPages="${totalPages}" pageUrl="${searchPage}"/>
        



