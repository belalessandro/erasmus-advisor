<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<title><c:out value="${addedEntity}"/> Added</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	
	<!-- CSS -->
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
		
	<!-- Javascript -->
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp"/>
		<!-- menu -->
		<jsp:include page="/jsp/include/menu.jsp">
			<jsp:param name="pageName" value="added"/>
		</jsp:include>

		<!-- corpo della pagina -->
		<div class="col-md-9 general_main_border">
			<h2 align="center">Entity Added</h2>
			<br>
			<p align="center">You have successfully added <b><c:out value="${addeddEntity}"/></b> into the database.</p>
			<br>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />

</body>
</html>