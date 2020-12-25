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
		<h1>Dress for weather</h1>
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

			<c:url value="/logout" var="logoutUrl" />
			<form id="logout" action="${logoutUrl}" method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<p>Hello, ${pageContext.request.userPrincipal.name}</p>
				<a href="javascript:document.getElementById('logout').submit()">logout</a>
			</c:if>


			<c:if test="${pageContext.request.userPrincipal.name == null}">
				<a href="${pageContext.request.contextPath}/loginPage">login</a>
			</c:if>


		</div>

		<div id="main">

			<div>

				<h3>Insert the name of the city to get the outfit advice</h3>
				<form method="get"
					action="${pageContext.request.contextPath}/advice">
					<input type="text" name="city" id="field" /> <input type="submit"
						value="search" />
				</form>

				<h3>
					<c:if test="${error != null}">${error}<br>
					</c:if>
				</h3>




				<br>
			</div>

			<div>
				<br> <br>
				<h2>Why is it important to dress appropriately for the weather?</h2>
				<br>
				<p>If you dress for the weather you will feel warm in winter and
					not hot in summer. This will reduce the likelihood of getting sick
					and you will enjoy your walk more. However, there are days when you
					don't know how to dress. Changeable weather happens regardless of
					the season. But, especially often it happens in autumn and spring.
					People outside the window can dress completely differently. Some
					wear T-shirts, while others wear jackets. If you want to receive
					dress advice - just insert the name of your city.</p>
			</div>

		</div>
	</div>
	<div id="footer">
		<p>Copyright © Margarita Skokova, 2020</p>
	</div>
</body>
</html>