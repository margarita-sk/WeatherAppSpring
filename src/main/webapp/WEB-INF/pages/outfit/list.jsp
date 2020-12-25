<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<a href="${pageContext.request.contextPath}/authorization/logout">logout</a>
			</c:if>
			<hr>
			<br>
			<p>Insert the name of the city to get the outfit advice</p>
			<form method="get"
				action="${pageContext.request.contextPath}/outfit/advice">
				<input type="text" name="city" /><br> <input type="submit"
					value="search" />
			</form>
		</div>
		<div id="main">



			<h2>Outfits:</h2>
			<br>
			<table border="1" cellpadding="8" cellspacing="0">
				<tr>
					<th>Name of outfit</th>
					<th>Min temperature to dress</th>
					<th>Max temperature to dress</th>
					<th></th>
					<th></th>

				</tr>
				<jsp:useBean id="outfits" scope="request" type="java.util.ArrayList" />
				<c:forEach items="${outfits}" var="outfit">
					<tr>
						<td>${outfit.outfitName}</td>
						<td>${outfit.minTemperatureToWear}</td>
						<td>${outfit.maxTemperatureToWear}</td>
						<td><a
							href="${pageContext.request.contextPath}/outfit/${outfit.id}">Show
						</a></td>
						<td><form:form method="delete"
								action="${pageContext.request.contextPath}/outfit/${outfit.id}">
								<button type="submit" name="delete">delete</button>
							</form:form></td>


					</tr>
				</c:forEach>
			</table>


			<%-- 			<c:if test="${role == 'administrator'}"> --%>
			<hr>
			<p>
				<a href="${pageContext.request.contextPath}/outfit/add">Add new
					outfit</a>
			</p>
			<%-- 			</c:if> --%>
			<hr>
			<p>
				<a href="${pageContext.request.contextPath}/index">Go back to
					the start page</a>
			</p>
		</div>
	</div>
	<div id="footer">
		<p>Copyright © Margarita Skokova, 2020</p>
	</div>
</body>
</html>
