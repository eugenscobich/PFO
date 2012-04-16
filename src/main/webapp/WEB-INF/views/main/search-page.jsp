<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="head-row3">
	<div class="search-box">
		<form action="<c:url value="/search"/>" accept-charset="UTF-8" method="GET" id="search-form">
			<div>
				<input type="text" maxlength="128" name="query"
					id="edit-search-text-input"
					title="<spring:message code="search-form-input-title"/>"
					class="form-text" /> <input type="submit" id="edit-search-submit"
					value="<spring:message code="search-submit"/>" class="form-submit" />

			</div>
		</form>
	</div>
</div>