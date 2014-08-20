<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<title><c:out value="${deletedEntity}"/> Deleted</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	
	<!-- CSS -->
	<link href="../css/ea-main.css" rel="stylesheet">
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../fonts/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
		
	<!-- Javascript -->
	<script src="../js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp"/>

		<!-- corpo della pagina -->
		<div class ="col-md-2"></div>
		<div class="col-md-8">
			<h2 align="center">Entity Deleted</h2>
			<br>
			<p align="center">You have successfully deleted <b><c:out value="${deletedEntity}"/></b> from the database.</p>
			<br>
		</div>
		<div class ="col-md-2"></div>
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />

</body>
</html>