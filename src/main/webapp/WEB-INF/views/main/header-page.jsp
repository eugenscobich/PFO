<%@page import="ru.pfo.Constant"%>
<%@page import="ru.pfo.util.PropertiesUtil"%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.pfo.ru/tags" prefix="nl"%>
<div id="header">
	<div class="head-row1">
		<ul class="menu">
			<li class="menu-item first"><a href="<c:url value="/"/>" title="<spring:message code="header-menu-home-title"/>"><spring:message code="home"/></a>
			</li>
			<li class="menu-item"><a href="<c:url value="/video"/>" title="<spring:message code="header-menu-video-title"/>"><spring:message code="header-menu-video"/></a>
			</li>
			<li class="menu-item"><a href="<c:url value="/categories"/>" title="<spring:message code="header-menu-categories-title"/>"><spring:message code="categories"/></a>
			</li>
			<li class="menu-item"><a href="<c:url value="/"/>" title="<spring:message code="header-menu-home-title"/>"><spring:message code="home"/></a>
			</li>
			<li class="menu-item"><a href="<c:url value="/"/>" title="<spring:message code="header-menu-home-title"/>"><spring:message code="home"/></a>
			</li>
			<li class="menu-item last"><a href="<c:url value="/"/>" title="<spring:message code="header-menu-home-title"/>"><spring:message code="home"/></a>
			</li>
		</ul>
	</div>
	<div class="head-row2">
		<div class="col1">
			<%
			boolean isDefaultSiteName = request.getServerName().contains(PropertiesUtil.getProperty(Constant.PROPS_KEY_SERVER_NAME));
			%>
			<c:choose>
				<c:when test="<%=isDefaultSiteName%>">
					<a href="<c:url value="/"/>" title="Home"><img src="<c:url value="/resources/images/logo.png"/>" alt="Home"	class="logo" /> </a>
				</c:when>
				<c:otherwise>
					<a href="<c:url value="/"/>" title="Home"><img src="<c:url value="/resources/images/logo1.png"/>" alt="Home" class="logo" /> </a>
				</c:otherwise>
			</c:choose>
		</div>
		<c:if test="${not empty siteModel.bestVideoNewses}">
		<div class="col2">
			<div class="slider">
				<div class="box-left">
					<img alt="<spring:message code="home"/>" style="cursor: pointer;" id="left_but" src="<c:url value="/resources/images/previous.png"/>" />
				</div>
				<div class="box-top">
					<nl:bestNewseslist videoNewses="${siteModel.bestVideoNewses}"/>
					<br class="clear" />
				</div>
				<div class="box-right">
					<img alt="<spring:message code="home"/>" style="" id="right_but"
						src="<c:url value="/resources/images/next.png"/>" />
				</div>
			</div>
			<div class="slider-title">
				<img alt="<spring:message code="the-best-of-the-best-alt"/>" src="<c:url value="/resources/images/the-best-of-the-best.png"/>">
			</div>
		</div>
		</c:if>
	</div>
</div>
<script type="text/javascript">

	$(document).ready(function() {

		var x_pos = 0;
		var li_items_n = 0;
		var right_clicks = 0;
		var viewWindow = 3;
		var left_clicks = 0;
		var currPosition = 0;
		var li_col = $("#slider-list li");

		li_col.each(function(index) {
			//size = li.getSize();
			x_pos += $(this).width() + 10;
			li_items_n++;
		});

		right_clicks = li_items_n - viewWindow;
		total_clicks = li_items_n - viewWindow;

		$('#slider-list').css('position', 'relative');
		$('#slider-list').css('left', currPosition + 'px');
		$('#slider-list').css('width', x_pos + 'px');

		var is_playing = false;

		var completed = function() {
			is_playing = false;
		};

		$('#left_but').click(function() {
			
			if (!is_playing) {
				if (left_clicks > 0) {
					is_playing = true;
					currPosition = currPosition + 170;
					
					$('#slider-list').animate({
						'left' : currPosition + 'px'
					}, 700, "linear", completed);
					right_clicks++;
					left_clicks--;
				} else {
					is_playing = true;
					currPosition = -170 * total_clicks;
					$('#slider-list').animate({
						'left' : currPosition + 'px'
					}, 700, "linear", completed);
					right_clicks = 0;
					left_clicks = total_clicks;
				}
			}
		});

		$('#right_but').click(function() {

			if (!is_playing) {

				cur_offset = $('#slider-list').position().left;
				if (right_clicks > 0) {
					is_playing = true;
					currPosition = currPosition - 170;
					$('#slider-list').animate({
						'left' : currPosition + 'px'
					}, 700, "linear", completed);
					right_clicks--;
					left_clicks++;
				} else {
					is_playing = true;
					currPosition = 0;
					$('#slider-list').animate({
						'left' : currPosition + 'px'
					}, 700, "linear", completed);
					left_clicks = 0;
					right_clicks = total_clicks;
				}

			}
		});

	});
</script>