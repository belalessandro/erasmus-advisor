<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- questa è la pagina che appare quando si è completato il login
	la home è solo l'interfaccia verso l'esterno -->

<!DOCTYPE html>
<html>
<head>
	<title>Erasmus Advisor</title>
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
			<jsp:param name="pageName" value="index"/>
		</jsp:include>

		<!-- corpo della pagina -->
		<div class="col-md-9 general_main_border" >
			<h2 align="center">Erasmus Advisor</h2>
			<br>
			[Qui teoricamente si potrebbe mettere una qualche breve spiegazione del sito.
			Sotto, da specifiche, ci vanno gli interessi espressi dall'utente]
			<br>
			<br>
			<!-- Da generare in JSP -->
			<!-- Notare che cliccando sul tasto remove si deve togliere l'interesse dal DB
				ossia bisogna usare Ajax -->
			<div class="panel panel-default">
				<!-- Default panel contents -->
				<div class="col-sm-1 column"></div>
				<div class="panel-heading">
					<strong>You have express interest for the following flows.</strong>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th>Flow ID</th>
							<th>Target University</th>
							<th>Target City</th>
							<th class="index_table_col_remove" align="center">Remove</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><a href="#">EN985</a></td>
							<td><a href="#">Aberdeen University</a></td>
							<td><a href="#">Aberdeen</a></td>
							<td align="center">
								<button type="button" class="btn btn-default btn-xs">
									<span class="glyphicon glyphicon glyphicon-remove"></span>
								</button>
							</td>
						</tr>
						<tr>
							<td><a href="#">OS025</a></td>
							<td><a href="#">Universitat fur Bodenkultur Wien</a></td>
							<td><a href="#">Wien</a></td>
							<td align="center">
								<button type="button" class="btn btn-default btn-xs">
									<span class="glyphicon glyphicon glyphicon-remove"></span>
								</button>
							</td>
						</tr>
						<tr>
							<td><a href="#">FR7785</a></td>
							<td><a href="#">Haute Ecole Léonard de Vinci- ECAM</a></td>
							<td><a href="#">Paris</a></td>
							<td align="center">
								<button type="button" class="btn btn-default btn-xs">
									<span class="glyphicon glyphicon glyphicon-remove"></span>
								</button>
							</td>
						</tr>
						<tr>
							<td><a href="#">ES153</a></td>
							<td><a href="#">Universidad de Alcala'</a></td>
							<td><a href="#">Alcalá de Henares</a></td>
							<td align="center">
								<button type="button" class="btn btn-default btn-xs">
									<span class="glyphicon glyphicon glyphicon-remove"></span>
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>	
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />

</body>
</html>