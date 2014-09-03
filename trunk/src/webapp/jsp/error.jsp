<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Error</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/fonts"/>/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>
	<script>
	function goBack() {
   		window.history.back()
	}
	</script>
</head>

<body>
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp">
    		<c:choose>
    			<c:when test="${(not empty errorType) && (errorType == 'userNotLogged')}">
					<jsp:param name="pageName" value="error_not_logged"/>
				</c:when>
    			<c:otherwise>
    				<jsp:param name="pageName" value="error_logged"/>
    			</c:otherwise>
    		</c:choose>
		</jsp:include>

		<!-- Class Header -->
		<div class ="col-md-2"></div>
		
		<div class="col-md-8">
			<h2 align="center">An Error has occurred</h2>
			<br>
			<p align="center">Something has gone wrong. We are sorry for the inconvenience.</p>
			<br>
			<!-- display the message -->
    		<c:import url="/jsp/include/showMessage.jsp"/>
    		<div class="text-center">
    		<c:choose>
    			<c:when test="${(not empty errorType) && (errorType == 'userNotLogged')}">
    				<div class="col-md-6"><a href="<c:url value="/"/>" class="btn btn-primary">Home page</a></div>
    				<div class="col-md-6"><a href="<c:url value="/signin"/>" class="btn btn-primary">Sign in</a></div>
       			</c:when>
    			<c:otherwise>
    				<button class="btn btn-primary" onclick="goBack()">Go Back</button>
    			</c:otherwise>
    		</c:choose>
    		</div>
		</div>
		<div class="col-md-2"></div>
	</div>
	<!--/container-->
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />
</body>
</html>
