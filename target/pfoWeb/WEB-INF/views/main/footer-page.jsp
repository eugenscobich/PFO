<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib uri="http://www.pfo.ru/tags" prefix="nl" %>
<div class="footer"><p>
<nl:strongmessage code="baner-footer-message"/></p>
</div>


<sec:authorize ifAllGranted="ROLE_ADMIN">
   <a href="<c:url value="/onlogout"/>">Logout</a>
</sec:authorize>
