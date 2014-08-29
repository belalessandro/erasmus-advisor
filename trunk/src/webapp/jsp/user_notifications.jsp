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
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/fonts"/>/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
		
	<!-- Javascript -->
	<link href="<c:url value="/css"/>/bootstrap-select.min.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/star-rating.css" rel="stylesheet">
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/bootstrap-select.js"></script>
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>	
	<script src="<c:url value="/js"/>/star-rating.min.js"></script>	
	<script src="<c:url value="/js"/>/ea-form-validation.js"></script>
	<script src="<c:url value="/js"/>/ea-insert.js"></script>  
	
	<script>
	$(function() 
   	{ 
		// inizializza tablesorter, la funzione che permette di ordinare le voci delle tabelle
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
	
	/*
	* 	Function to manage accept buttons
	*
	*	primarykey: primary key of the entity
	*	type: type of entity ${entity.nome} o ${entity.id}
	*	execute: "accept" o "discard"
	*	event: 	click event 
	*/
	function acceptDiscardButtonClick(primarykey, type, execute, event)
	{
		console.log("primarykey: " + primarykey + " type: " + type + " execute: " + execute);
// 		$(event.target).parent().hide();
		// data to send
		var jsonData = new Object();
		jsonData.execute = execute;
		jsonData.primarykey = primarykey;
		jsonData.type = type;
		
		// convert to a transferreable json
	    var jsonobj = JSON.stringify(jsonData);
		
		// may be useful (to understand)
 		var button_accept = $(event.target).parent();
 		var button_discard = $(event.target).siblings();
 						
	    $.ajax({
	    	data : jsonobj,  
			contentType : "application/json",
			method : "POST",
			url : "<c:url value="/notifications"/>",
			success: function(data) { 
				
				console.log("success : " + data);
				
				if(data[type] == "enabled")
				{
					// parent node of accept/discard buttons (hiding the content)
					var td = $(button_accept).parent();
					
					$(td).html("&nbsp;");
					
					// parent node of td cell (change content color)
					var tr = $(td).parent();
					$(tr).removeClass("danger");
					
					// change state to approved
			 		$(td).siblings().each(function() {
			 			if($(this).hasClass("status"))
			 				$(this).html("Approved");
			 		});
					
					console.log(type + " accepted");
				}
				if(data[type] == "disabled")
				{
					// parent node of accept/discard buttons (hiding the content)
					var td = $(button_accept).parent();
					
					$(td).html("&nbsp;");
					
					// parent node of td cell (change content color)
					var tr = $(td).parent();
					$(tr).removeClass("danger");
					
					// change state to approved
			 		$(td).siblings().each(function() {
			 			if($(this).hasClass("status"))
			 				$(this).html("Removed");
			 		});
					
			 		console.log(type + " discarded");
				}
			},
			error: function(data) {console.log("EA ERROR: failed to report the entity.");
			console.log(data);}
	    }); // end ajax	
	} // end function acceptDiscard
	
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
				
					<c:forEach var="flowManager" items="${flowManagers}">
						<tr class="danger">
							<td>
								<button  onclick="acceptDiscardButtonClick('<c:out value="${flowManager.nomeUtente}"/>', 'flowmanager', 'accept', event)" type="button" class="btn btn-default btn-xs">
									<span class="glyphicon glyphicon-ok"></span>
								</button>
								<button onclick="acceptDiscardButtonClick('<c:out value="${flowManager.nomeUtente}"/>', 'flowmanager', 'discard', event)" type="button" class="btn btn-default btn-xs">
									<span class="glyphicon glyphicon glyphicon-remove"></span>
								</button>
							</td>
							<td><c:out value="${flowManager.nomeUtente}"/></td>
							<td><c:out value="${flowManager.email}"/></td>
							<td><c:out value="${flowManager.dataRegistrazione}"/></td>
							<td class="status">To Be Approved</td>
						</tr>
					</c:forEach>
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
						<th>Professors</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="classBean" items="${classes}"  varStatus="counter">
						<tr class="danger">
							<td>
								<button onclick="acceptDiscardButtonClick(<c:out value="${classBean.id}"/>, 'class', 'accept', event)" type="button" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-ok"></span></button>
								<button onclick="acceptDiscardButtonClick(<c:out value="${classBean.id}"/>, 'class', 'discard', event)" type="button" class="btn btn-default btn-xs"><span class="glyphicon glyphicon glyphicon-remove"></span></button>
							</td>
							<td><a href="<c:url value="/class?id="/><c:out value="${classBean.id}"/>" target="_blank"><c:out value="${classBean.nome}"/></a></td>
							<td><c:out value="${classBean.nomeUniversita}"/></td>
							<td>
								<table>
									<tbody>
										<c:forEach var="professor" items="${classProfessors[counter.index]}">
										<tr><td><c:out value="${professor.nome} ${professor.cognome}"/></td></tr>
										</c:forEach>		
									</tbody>
								</table>
							</td>
						</tr>
					</c:forEach>
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
					<c:forEach var="thesis" items="${theses}" varStatus="counter">
						<tr class="danger">
							<td>
								<button onclick="acceptDiscardButtonClick(<c:out value="${thesis.id}"/>, 'thesis', 'accept', event)" type="button" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-ok"></span></button>
								<button onclick="acceptDiscardButtonClick(<c:out value="${thesis.id}"/>, 'thesis', 'discard', event)" type="button" class="btn btn-default btn-xs"><span class="glyphicon glyphicon glyphicon-remove"></span></button>
							</td>
							<td><a href="<c:url value="/thesis?id="/><c:out value="${thesis.id}"/>" target="_blank"><c:out value="${thesis.nome}"/></a></td>
							<td><c:out value="${thesis.nomeUniversita}"/></td>
							<td><c:out value="${thesisProfessors[counter.index].nome} ${thesisProfessors[counter.index].cognome}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<!-- fine sezione responsabile di flusso -->
		</div>	
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />
</body>
</html>