<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- la struttura di questa pagina è complessa per garantire il coordinatore erasmus
	abbia nella sua pagina notifiche anche quanto visibile solo per il responsabile di flusso,
	come risulta dalle specifiche. le sezioni da visualizzare dipendono dunque dal tipo di utente -->

<!DOCTYPE html>
<html>
<head>
	<title>Notifications Panel</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
	
	<script>
	// inizializza tablesorter
	$(document).ready(function() 
   	{ 
       	$("#coordinatorTable").tablesorter({ 
	        headers: { 
	            0: { sorter: false }
	        } 
	    });
       	$("#classTable").tablesorter({ 
	        headers: { 
	            0: { sorter: false }
	        } 
	    });
       	$("#thesisTable").tablesorter({ 
	        headers: { 
	            0: { sorter: false }
	        } 
	    });
  	}); 
</script>
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
			
			<!-- inizio sezione coordinatore erasmus -->
			<!-- i risultati vanno creati dinamicamente
				alla pressione sui tasti corrisponde l'azione di conferma o rimozione -->
				
			<h4 align="center">New Flow Managers</h4>
			<div class="alert alert-dismissable alert-danger">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
				There are * 2 * Flow Manager whose identity needs to be confirmed! Deactivate the account in case of doubt.
			</div>
			<table class="table table-condensed table-hover table-striped tablesorter" id="coordinatorTable">
				<thead>
					<tr>
						<th>Actions</th>
						<th>User Name</th>
						<th>Email</th>
						<th>Registration Date</th>
						<th>State</th>
					</tr>
				</thead>
				<tbody>
					<tr class="danger">
						<td>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon-ok"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon glyphicon-remove"></span>
							</button>
						</td>
						<td>capri</td>
						<td>dmcourse@unipd.it</td>
						<td>03/04/2012</td>
						<td>To be approved</td>
					</tr>
					<tr class="danger">
						<td>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon-ok"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon glyphicon-remove"></span>
							</button>
						</td>
						<td>valcher</td>
						<td>mariavalcher@unipd.it</td>
						<td>03/04/2012</td>
						<td>To be approved</td>
					</tr>
					<tr>
						<td></td>
						<td>geppo</td>
						<td>geppino@unipd.it</td>
						<td>02/04/2012</td>
						<td>Active</td>
					</tr>
					<tr>
						<td></td>
						<td>finesso</td>
						<td>prova@prova.com</td>
						<td>01/04/2012</td>
						<td>Active</td>
					</tr>
					<tr>
						<td></td>
						<td>amenos</td>
						<td>tecnico@unipd.it</td>
						<td>01/04/2012</td>
						<td>Active</td>
					</tr>
				</tbody>
			</table>
			<br><br>
			<!-- fine sezione coordinatore erasmus -->
			
			<!-- inizio sezione responsabile di flusso -->
			
			<!-- i risultati vanno creati dinamicamente
				alla pressione sui tasti corrisponde l'azione di conferma o rimozione -->
			<h4 align="center">Reported Courses</h4>
			<div class="alert alert-danger alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
				There are new courses that need a check.
			</div>
			
			<table class="table table-condensed table-hover table-striped tablesorter" id="classTable">
				<thead>
					<tr>
						<th>Actions</th>
						<th>Name</th>
						<th>University</th>
						<th>Professor</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon-ok"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon glyphicon-remove"></span>
							</button>
						</td>
						<td><a href="#" target="_blank">Analisi del Pene</a></td>
						<td>Università agli Studi di Padova</td>
						<td>HUEHUEHUEHUE</td>
					</tr>
					<tr>
						<td>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon-ok"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon glyphicon-remove"></span>
							</button>
						</td>
						<td><a href="#" target="_blank">Culo</a></td>
						<td>Università agli Studi di Bologna</td>
						<td>Matteo Montesi</td>
					</tr>
				</tbody>
			</table>
			<br><br>
			
			<h4 align="center">Reported Theses</h4>
			<div class="alert alert-danger alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
				There are new theses that need a check.
			</div>
			
			<table class="table table-condensed table-hover table-striped tablesorter" id="thesisTable">
				<thead>
					<tr>
						<th></th>
						<th>Name</th>
						<th>University</th>
						<th>Professor</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon-ok"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon glyphicon-remove"></span>
							</button>
						</td>
						<td><a href="#" target="_blank">;DROP TABLE Users CASCADE;</a></td>
						<td>London University</td>
						<td>LOLOL</td>
					</tr>
					<tr>
						<td>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon-ok"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs">
								<span class="glyphicon glyphicon glyphicon-remove"></span>
							</button>
						</td>
						<td><a href="#" target="_blank">Analisys of Trolls</a></td>
						<td>Università agli Studi di Napoli</td>
						<td>Paolo Chiavetor</td>
					</tr>
				</tbody>
			</table>
			<!-- fine sezione responsabile di flusso -->
		</div>	
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />
</body>
</html>