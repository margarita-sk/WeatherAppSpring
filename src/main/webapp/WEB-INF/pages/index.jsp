<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Weather forecast and clothing tips</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/styles/style1.css"/>" />
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
			<c:if test="${not empty error}">Incorrect login or password!<br>
			</c:if>

			<c:if test="${role != null}">Hello, ${role}

				<a href="j_spring_security_logout">Logout</a>
			</c:if>

			<c:if test="${role == null}">

				<c:url var="loginUrl" value="/login" />
				<form action="${loginUrl}" method="post">


					<p>Login</p>
					<input required type="text" name="login" /> <br>
					<p>Password</p>
					<input required="required" type="password" name="pass" /> <br>
					<br> <input type="submit" value="submint" name="submit" />
				</form>
			</c:if>
		</div>

		<div id="main">

			<div>

				<h3>Insert the name of the city to get the outfit advice</h3>
				<form method="get"
					action="${pageContext.request.contextPath}/outfit/advice">
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