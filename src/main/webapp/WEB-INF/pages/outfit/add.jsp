<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Weather forecast and clothing tips</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/styles/style_green.css"/>" />
</head>
<body>
	<div id="header">
		<h1>Hello, ${pageContext.request.userPrincipal.name}</h1>
	</div>
	<div id="wrapper">
		<div id="menu">

			<c:url value="/logout" var="logoutUrl" />
			<form id="logout" action="${logoutUrl}" method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<p>Hello, ${pageContext.request.userPrincipal.name}</p>
				<a href="javascript:document.getElementById('logout').submit()">logout</a>
			</c:if>
			<hr>
			<br>
			<p>Insert the name of the city to get the outfit advice</p>
			<br>
			<form method="get" action="${pageContext.request.contextPath}/advice">
				<input type="textbox" name="city" /><br> <input type="submit"
					value="search" />
			</form>
		</div>
		<div id="main">
			<h3>
				<c:if test="${error != null}">${error}<br>
				</c:if>
			</h3>
			<br>
			<form:form method="POST"
				action="${pageContext.request.contextPath}/outfit/add"
				modelAttribute="outfit">
				<p>Outfit</p>
				<form:input path="outfitName" required="required" />
				<br>
				<br>
				<p>Min temperature to dress the outfit</p>
				<form:input path="minTemperatureToWear" required="required" />
				<br>
				<br>
				<p>Max temperature to dress the outfit</p>
				<form:input path="maxTemperatureToWear" required="required" />
				<br>
				<input type="submit" value="insert" />
			</form:form>

			<br>
			<p>
				<a href="${pageContext.request.contextPath}/outfit/list">Go back
					to the list of outfits</a>
			</p>
		</div>
	</div>
	<div id="footer">
		<p>Copyright © Margarita Skokova, 2020</p>
	</div>
</body>
</html>
