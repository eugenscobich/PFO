<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title><spring:message code="main-title"/></title>
	<meta name="keywords" content="<spring:message code="keywords"/>" />
	<meta name="description" content="<spring:message code="description"/>" />
	<meta name="robots" content="follow, all" />
	<link rel="shortcut icon" href="<c:url value="/resources/images/icon.ico"/>" type="image/x-icon" />
	<link rel="stylesheet" href='<c:url value="/resources/css/style.css"/>' type="text/css"/>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.6.4.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/common.js"/>"></script>
</head>

<body>
<!--LiveInternet counter--><script type="text/javascript"><!--
new Image().src = "//counter.yadro.ru/hit?r"+
escape(document.referrer)+((typeof(screen)=="undefined")?"":
";s"+screen.width+"*"+screen.height+"*"+(screen.colorDepth?
screen.colorDepth:screen.pixelDepth))+";u"+escape(document.URL)+
";"+Math.random();//--></script><!--/LiveInternet-->

<div id="wrapper">
	
	<tiles:insertAttribute name="header" />
	<div id="middle">
		<div id="container">
			<div id="content">
				<tiles:insertAttribute name="baner" />
				<tiles:insertAttribute name="content" />
			</div><!-- #content-->
		</div><!-- #container-->

		<div id="sidebar">
			<tiles:insertAttribute name="sidebar" />		
		</div><!-- .sidebar#sideRight -->

	</div><!-- #middle-->
		
	<div id="footer">
		<tiles:insertAttribute name="footer" />
	</div><!-- #footer -->

</div><!-- #wrapper -->
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-28080473-1']);
  _gaq.push(['_setDomainName', 'porno4pok.ru']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</body>
</html>
