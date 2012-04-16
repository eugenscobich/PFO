<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.pfo.ru/tags" prefix="nl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nl:newslist videoNewses="${videoNewses}"/>
<nl:newslistpaginator page="${page}" totalPages="${totalPages}"/>
        



