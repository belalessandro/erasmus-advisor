<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- la struttura di questa pagina è complessa per garantire il coordinatore erasmus
	abbia nella sua pagina notifiche anche quanto visibile solo per il responsabile di flusso,
	come risulta dalle specifiche. quale pagina includere dipenderà dunque dalla tipologia
	dell'utente mentre bisognerà passare esplicitamente i parametri alle sottopagine,
	prima passandoli a questa pagina e poi da questa alle altre -->

<!DOCTYPE html>
<html>
<head>
	<title>Notifications Panel</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	
	<!-- CSS -->
	<link href="../css/ea-main.css" rel="stylesheet">
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../fonts/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
		
	<!-- Javascript -->
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/jquery.tablesorter.min.js"></script>
	<link href="../css/tablesorter/style.css" rel="stylesheet"> 
</head>
<body>
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp">
			<jsp:param name="pageName" value="notifications"/>
		</jsp:include>
		<!-- menu -->
		<jsp:include page="/jsp/include/menu.jsp">
			<jsp:param name="pageName" value="notifications"/>
		</jsp:include>

		<!-- corpo della pagina -->
		<div class="col-md-9 general_main_border">
			<h2 align="center">Moderation Panel</h2>
			<br>
			<jsp:include page="/jsp/include/notifications_coordinator.jsp"/>
			<jsp:include page="/jsp/include/notifications_flow_manager.jsp"/>
		</div>	
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />
</body>
</html>