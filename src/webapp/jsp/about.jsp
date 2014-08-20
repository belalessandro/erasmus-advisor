<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<title>About Erasmus Advisor</title>
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
		<!-- menu -->
		<jsp:include page="/jsp/include/menu.jsp">
			<jsp:param name="pageName" value="about"/>
		</jsp:include>

		<!-- corpo della pagina -->
		<div class="col-md-9 general_main_border">
			<h2 align="center">About Erasmus Advisor</h2>
			<br>
			This web application has been realized by
			<b>Alessandro Beltramin</b>, <b>Federico Chiariotti</b>, <b>Mauro Piazza</b>, 
			<b>Luca Schiappadini</b> and <b>Nicola Tommasi</b>
			as part of the Databases course given at the University of Padua.
			<br><br>
			In the web site we have included code developed by others, that owns their specific licenses.
			In particular we have used: <br><br>
			<nav>
				<ul>
					<li>Bootstrap 3.1.1 (<a href="http://getbootstrap.com/" target="_blank">http://getbootstrap.com/</a>).</li>
					<li>Font Awesome 4.1.0 (<a href="http://fortawesome.github.io/Font-Awesome/" target="_blank">http://fortawesome.github.io/Font-Awesome/</a>).</li>
					<li>jQuery (<a href="http://jquery.com/" target="_blank">http://jquery.com/</a>).</li>
					<li>Tablesorter 2.0 (<a href="http://tablesorter.com/docs/" target="_blank">http://tablesorter.com/docs/</a>).</li>
					<li>Bootstrap-datepicker (<a href="bootstrap-datepicker.readthedocs.org/en/release/" target="_blank">bootstrap-datepicker.readthedocs.org/en/release/</a>).</li>
					<li>Bootstrap-select (<a href="http://silviomoreto.github.io/bootstrap-select/" target="_blank">http://silviomoreto.github.io/bootstrap-select/</a>).</li>
					<li>Bootstrap star rating (<a href="http://plugins.krajee.com/star-rating" target="_blank">http://plugins.krajee.com/star-rating</a>).</li>
				</ul>
			</nav>
		</div>	
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />

</body>
</html>