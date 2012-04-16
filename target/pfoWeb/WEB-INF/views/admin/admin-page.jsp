<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<br/>
<h2>Administration</h2>

<br/>

<div id="admin">
<c:url var="submit" value="/admin/saveSettings" />
<form:form commandName="settings" action="${submit}" method="POST">
<form:errors path="*" cssClass="errorblock" element="div"/>
	<ul>
		<li>
			<div class="title">Cron Expresion*:</div>
			<div class="content"><form:input path="cronExpresion" /></div>
		</li>
		<li>
			<div class="title">Refresh Interval*:</div>
			<div class="content"><form:input path="refreshInterval" /></div>
		</li>
		<li>
			<div class="title">Number of scaning pages*:</div>
			<div class="content"><form:input path="numberOfPages" /></div>
		</li>
		<li>
			<div class="title">Items on Page*:</div>
			<div class="content"><form:input path="itemsOnPage" /></div>
		</li>
		<li>
			<div class="title">Max Living Links*:</div>
			<div class="content"><form:input path="maxLivingLinks" /></div>
		</li>
	</ul>
	<div><input type="submit" value="Save"/></div>
</form:form>


<c:url var="runParser" value="/admin/runParser" />
<form:form action="${runParser}" method="POST">
	<ul>
		<li>
			<div class="title">Run Parser:</div>
			<div class="content"><input type="submit" value="Start"/></div>
		</li>
	</ul>
</form:form>

<c:url var="removeDuplicates" value="/admin/removeDuplicates" />
<form:form action="${removeDuplicates}" method="POST">
	<ul>
		<li>
			<div class="title">Remove Duplicates:</div>
			<div class="content"><input type="submit" value="Start"/></div>
		</li>
	</ul>
</form:form>


<table>
	<tr>
		<td>
			<!--LiveInternet logo--><a href="http://www.liveinternet.ru/click"
			target="_blank"><img src="//counter.yadro.ru/logo?27.6"
			title="LiveInternet: показано количество просмотров и посетителей"
			alt="" border="0" width="88" height="120"/></a><!--/LiveInternet-->
		</td>
	</tr>
</table>
</div>