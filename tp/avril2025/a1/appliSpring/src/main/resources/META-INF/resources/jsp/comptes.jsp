<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>comptes</title>
</head>
<body>
	<h3>liste des comptes</h3>
	<table border="1">
		<tr>
			<th>numero</th>
			<th>label</th>
			<th>solde</th>
		</tr>
		<c:forEach var="c" items="${allComptes}">
			<tr>
				<td>${c.numero}</td>
				<td>${c.label}</td>
				<td>${c.solde}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>