<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<h1>The weather is wonderful in ${city} today!</h1>
	</div>
	<div id="wrapper">

		<div id="menu">
			<form method="get"
				action="${pageContext.request.contextPath}/outfit/list">
				<p>
					Outfit list <input type="submit" value="enter">
				</p>
			</form>
			<hr>

			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<p>Hello, ${pageContext.request.userPrincipal.name}</p>
				<a href="${pageContext.request.contextPath}/authorization/logout">logout</a>
			</c:if>

			<c:if test="${pageContext.request.userPrincipal.name == null}">
				<a href="${pageContext.request.contextPath}/login">login</a>
			</c:if>



			<br>
		</div>

		<div id="main">

			<div>

				<h3>Insert the name of the city to get the outfit advice</h3>
				<form method="get"
					action="${pageContext.request.contextPath}/advice">
					<input type="text" name="city" id="field" /> <input type="submit"
						value="search" />
				</form>

				<br>
			</div>

			<div>
				<hr>
				<h3>
					<c:if test="${error != null}">${error}<br>
					</c:if>
				</h3>

				<c:if test="${error == null}">
					<img
						src="https://yastatic.net/weather/i/icons/blueye/color/svg/${img}.svg"
						alt="погода" width="100">

					<p>Air temperature is ${temperature}&#176 Celsius. It's
						${condition} today.</p>
					<br>
					<p>Recommended clothing: ${outfit}.</p>
					<p>You may need ${accessories}.</p>
					<p>Have a nice walk!</p>

				</c:if>
			</div>

		</div>
	</div>
	<div id="footer">
		<p>Copyright © Margarita Skokova, 2020</p>
	</div>
</body>
</html>