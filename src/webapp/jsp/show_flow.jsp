<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<!DOCTYPE html>
<html>
<head>
	<title><c:out value="${flow.id}"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	
	<!-- CSS -->
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/fonts"/>/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	
	<!-- componenti aggiuntivi -->
	<link href="<c:url value="/css"/>/bootstrap-select.min.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/star-rating.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/datepicker3.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/ui-lightness/jquery-ui-1.10.4.custom.css" rel="stylesheet">
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/jquery-ui-1.10.4.custom.js"></script>
	<script src="<c:url value="/js"/>/bootstrap-select.js"></script>
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>	
	<script src="<c:url value="/js"/>/star-rating.min.js"></script>	
	<script src="<c:url value="/js"/>/jquery.tablesorter.min.js"></script>
	<script src="<c:url value="/js"/>/ea-basic.js"></script>	
	<script src="<c:url value="/js"/>/bootstrap-datepicker.js"></script>
	<link href="<c:url value="/css"/>/tablesorter/style.css" rel="stylesheet"> 
	
	<script src="<c:url value="/js"/>/ea-form-validation.js"></script>
		
	<script>
        $(document).ready(function() { 
    			// funzione che notifica che questo flusso è di interesse per lo studente
	            $('#flow_add_interest').click(function() {  
	    	    	var r = confirm("Do you want to add this flow to your interests?");
	    			if (r == true) 
	    			{               
		                var flow = "<c:out value="${flow.id}"/>";
		            	$.post('<c:url value="/student/interests"/>', 
		                		{ operation: "insert", flowID : flow},
		                		function(responseText) { 
									$('#add-interest-success').show();
									
		                			var actualInterest = parseInt(responseText);
		                			formatInterest(actualInterest);
		                		    document.getElementById("flow_add_interest").setAttribute("style", "display: none;");
		                		    document.getElementById("flow_remove_interest").setAttribute("style", "display: block;");
		                	});
	    			} 
	    			else {  // splash! and nothing happens
	    			}      
	            });
	    		// funziona che notifica che questo flusso non è più di interesse per lo studente
	            $('#flow_remove_interest').click(function() {  
	    	    	var r = confirm("Do you want to remove this flow from your interests?");
	    			if (r == true) 
	    			{               
		                var flow = "<c:out value="${flow.id}"/>";
		            	$.post('<c:url value="/student/interests"/>', 
		                		{ operation: "delete", flowID : flow},
		                		function(responseText) { 
									$('#remove-interest-success').show();
									
		                			var actualInterest = parseInt(responseText);
		                			formatInterest(actualInterest);
		                		    document.getElementById("flow_remove_interest").setAttribute("style", "display: none;");
		                		    document.getElementById("flow_add_interest").setAttribute("style", "display: block;");
		                	});
	    			} 
	    			else {  // splash! and nothing happens
	    			}      
	            });
	    		// funziona che rimuove la partecipazione
	            $('#flow_remove_participation').click(function() {  
	    	    	var r = confirm("Do you want to remove your participation to this flow?");
	    			if (r == true) 
	    			{               
		                var flow = "<c:out value="${flow.id}"/>";
		            	$.post('<c:url value="/flow/participation"/>', 
		                		{ operation: "delete", flowID : flow},
		                		function(responseText) { 
									$('#remove-participation-success').show();
									
		                			var actualInterest = parseInt(responseText);
		                		    document.getElementById("flow_remove_participation").setAttribute("style", "display: none;");
		                		    document.getElementById("flow_add_participation").setAttribute("style", "display: block;");
		                	});
	    			} 
	    			else {  // splash! and nothing happens
	    			}      
	            });
        });		
		function formatInterest(numInterest)
		{
			if (isNaN(numInterest))
			{
    			$('#number_of_interested_stud').html("Error with the interest operation, please reload this page");
			}
			else if (numInterest == 0)
			{
    			$('#number_of_interested_stud').html("No students have expressed interest for this flow");
			}
			else if (numInterest == 1)
			{
    			$('#number_of_interested_stud').html("<b>One</b> student has expressed interest for this flow");
			}
			else
			{
    			$('#number_of_interested_stud').html("There are <b>"+numInterest+"</b> students that have expressed interest for this flow");
			}
		}
		
		// inizializza i select avanzati
		$(document).ready(function() {
		    $('.selectpicker').selectpicker({
		        style: 'btn-default',
		        size: false
		    });
		});
		// inizializza tablesorter
		$(document).ready(function() 
    	{ 
        	$("#resultTable").tablesorter();
   		}); 
	</script>
</head>
<body>
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp"/>
		<!-- menu -->
		<jsp:include page="/jsp/include/menu.jsp">
			<jsp:param name="pageName" value="show"/>
		</jsp:include>

		<!-- corpo della pagina -->
		<div class="col-md-9 general_main_border">
		
			<!-- Notifica di avvenuta modifica del flusso -->
			<c:if test="${!empty param.edited && param.edited == 'success'}">
				<div class="alert alert-success alert-dismissible" role="alert" >
				  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				  <h4 class="text-center">Flow Successfully Edited!</h4>
				</div>
			</c:if>
			
			<!-- Avviso aggiunta interesse con successo -->
			<div id="add-interest-success" class="alert alert-success" role="alert" style="display:none">
				<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<div class="text-center">This flow has been successfully add to your interests!</div>  
			</div>
			
			<!-- Avviso rimozione interesse con successo -->
			<div id="remove-interest-success" class="alert alert-success" role="alert" style="display:none">
				<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<div class="text-center">This flow has been successfully removed from your interests.</div>  
			</div>
			
			<!-- Avviso rimozione partecipazione con successo -->
			<div id="remove-participation-success" class="alert alert-success" role="alert" style="display:none">
				<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<div class="text-center">You have successfully removed your participation from this flow.</div>  
			</div>
			
			<div class="entity_details">
				<div class="entity_details_text">
					<h2><c:out value="${flow.id}"/></h2> 
					<p>
						Destination: <a href="<c:url value="/university"/>?name=${fn:replace(flow.destinazione, ' ', '+')}" target="_blank">${flow.destinazione}</a><br>
						Starting degree courses:
						<c:forEach var="orig" items='${origins}' varStatus="status">${orig.nome} (${orig.livello})<c:if test="${!status.last}">, </c:if><c:if test="${status.last}">.</c:if></c:forEach><br>
						Flow Manager: <c:out value="${manager.nome}"/> <c:out value="${manager.cognome}"/><br> 
						Available positions: <c:out value="${flow.postiDisponibili}"/> <br> 
						Length (months): <c:out value="${flow.durata}"/> <br> 
						Required language certifications:
						<c:forEach var="certificates" items='${certificates}' varStatus="status">${certificates.nomeLingua} - ${certificates.livello}<c:if test="${!status.last}">, </c:if><c:if test="${status.last}">.</c:if></c:forEach><br>
						<c:if test="${not empty flow.dettagli}">Details: <c:out value="${flow.dettagli}"/> <br></c:if>
						<c:choose><c:when test="${flow.attivo}">Is </c:when><c:otherwise>Not </c:otherwise></c:choose>Active 
						(Last Modification: <c:out value="${flow.dataUltimaModifica}"/>) <br>  
						<br>
					</p>
				</div>
				<div class="entity_details_text">
					<!-- evalutate visibile solo da studente
						show/remove interest solo da studente, se ha espresso interesse diventa remove interest
						edit e delete solo da reponsabili di flusso e coordinatori erasmus
						add partecipation solo se non si è partecipato già al flusso						
					 -->
					<ul class="nav nav-stacked pull-right">
						<li class="active"><span data-toggle="modal" data-target="#evaluateForm">Evaluate</span></li>
						<li class="active"><span id="flow_add_interest">Add interest</span></li>
						<li class="active"><span id="flow_remove_interest" style="display: none !important;">Remove interest</span></li>
						<li class="active"><span id="flow_add_partecipation" data-toggle="modal" data-target="#participationForm">Add participation</span></li>
						<li class="active">
							<span id="flow_remove_participation" style="display: none !important;">Remove participation</span>
						</li>
						<li class="active"><span data-toggle="modal" data-target="#editForm">Edit</span></li>
						<li class="active">
							<form method="post" action="<c:url value="/flow"/>">
                                <input type="hidden" name="operation" value="delete"/>
                                <input type="hidden" name="id" value="${flow.id}"/>
								<input type="submit" value="Delete" class="btn btn-primary entity_nav_button" 
									onclick="return confirm('Do you really want to remove this flow from the database?');">
							</form>
						</li>
					</ul>
				</div>
			</div>
			
			<!--Form di valutazione a comparsa-->
			<div class="modal fade" id="evaluateForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Insert an evaluation for <b><c:out value="${flow.id}"/></b></h4>
						</div>
						<div class="modal-body">
							<!-- action deve puntare alla servlet che gestisce l'inserimento della valutazione -->
							<form name='flowEvaluationForm' method="post" action='<c:url value="/flow/evaluations"/>' >
								<div class="col-md-6 text-center">Gratification:</div>
								<div class="col-md-6 text-center">
									<input id="gratification" name="soddEsperienza" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Academic Fulfillment:</div>
								<div class="col-md-6 text-center">
									<input id="academicFulfillment" name="soddAccademica"  class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Didactics:</div>
								<div class="col-md-6 text-center">
									<input id="didactics" name="didattica"  class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Manager Evaluation:</div>
								<div class="col-md-6 text-center">
									<input id="managerEvaluation" name="valutazioneResponsabile"  class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br><br>								
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Comment</span> <textarea rows="2" class="form-control" name="commento" id="comment" placeholder="Insert a general comment about the city."></textarea>
								</div>
								<br>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<input type="submit" value="Save!" class="btn btn-primary pull-right">
								</div>
								
								<!-- hidden params -->
								<input type="hidden" name="idFlusso" value="<c:out value="${flow.id}"/>" />
                                <input type="hidden" name="operation" value="insert"/>
								
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- fine Form di valutazione a comparsa-->
			
			<!--Form di edit a comparsa-->
			<div class="modal fade" id="editForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Edit <b><c:out value="${flow.id}"/></b></h4>
						</div>
						<div class="modal-body">
							
							<form name='flowEditForm'  method="post"  action="<c:url value="/flow"/>" >
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">ID*</span> <input type="text" class="form-control" name="id" id="name" value="<c:out value="${flow.id}"/>">
								</div>
								<br>
								<div class="row text-center">
									<span></span>
									<span class="input-group-addon insert_new_select_label_inline">Insert the flow's starting degree courses*</span>
									<select class="selectpicker text-left" multiple id="origin" name="origins[]" data-width="auto">
										<c:forEach var="possibleCourse" items='${possibleCourses}'>
											<option value="${possibleCourse.id}" 
												<c:forEach var="origin" items='${origins}' >
												<c:if test="${origin.id == possibleCourse.id }">selected</c:if>
												</c:forEach>
											>
											${possibleCourse.nome} (${possibleCourse.livello})</option>
										</c:forEach>
				    				</select> 
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Destination*</span> <input id="university" class="form-control" name="destinazione" value="<c:out value="${flow.destinazione}"/>">
								</div>
								<br>
								<div class="row text-center">
									<span></span>
									<span class="input-group-addon insert_new_select_label_inline">Insert the flow's Language Certifications*</span>
									<select class="selectpicker text-left" multiple id="certificate" name="certificates[]" data-width="auto">
										<c:forEach var="certificatesDomain" items='${certificatesDomain}'>
											<option value="${certificatesDomain.nomeLingua} - ${certificatesDomain.livello}" 
												<c:forEach var="certificates" items='${certificates}' >
												<c:if test="${(certificatesDomain.nomeLingua == certificates.nomeLingua) && (certificatesDomain.livello == certificates.livello)}">selected</c:if>
												</c:forEach>
											>
											${certificatesDomain.nomeLingua} - ${certificatesDomain.livello}</option>
										</c:forEach>
				    				</select> 						
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<div class="input-group insert_new_input_group_small">
										<span class="input-group-addon insert_new_input">Available positions*</span> <input type="text" class="form-control" name="postiDisponibili" id="seats" value="<c:out value="${flow.postiDisponibili}"/>">
									</div>
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<div class="input-group insert_new_input_group_small">
										<span class="input-group-addon insert_new_input">Length (months)*</span> <input type="text" class="form-control" name="durata" id="length" value="<c:out value="${flow.durata}"/>">
									</div>
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Details</span> <textarea rows="2" class="form-control" name="dettagli" id="details">${flow.dettagli}</textarea>
								</div>
								<br>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<input type="submit" value="Save your changes" class="btn btn-primary pull-right">
								</div>
								
								<!-- hidden params -->
								<input type="hidden" name="operation" value="edit" />
								<input type="hidden" name="old_id" value="<c:out value="${flow.id}" />" />
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- fine Form di valutazione a comparsa-->		
			
			<!--Form di inserimento partecipazione -->
			<div class="modal fade" id="participationForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Set the participation to <b><c:out value="${flow.id}"/></b></h4>
						</div>
						<div class="modal-body">
							<form name='participationForm'  method="post"  action="<c:url value="/flow/participation"/>" >
								<div class="row">
									<div class="col-lg-2"></div>
									<div class="col-lg-8">
										<div class="input-group sign_in_input_group">
											<span class="input-group-addon sign_in_input_small">From</span><input type="text" class="form-control" id="datepicker" name="date_from" placeholder="dd/mm/yyyy">
										</div>
										<br>
										<div class="input-group sign_in_input_group">
											<span class="input-group-addon sign_in_input_small">To</span><input type="text" class="form-control" id="datepicker2" name="date_to" placeholder="dd/mm/yyyy">
										</div>
									</div>
									<div class="col-lg-2"></div>
								</div>
								<br>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<input type="submit" value="Add participation" class="btn btn-primary pull-right">
								</div>
								
								<!-- hidden params -->
								<input type="hidden" name="operation" value="insert" />
								<input type="hidden" name="flowID" value="<c:out value="${flow.id}" />" />
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- fine Form di inserimento partecipazione-->	
			
			<br>	
			<h4 align="center" id="number_of_interested_stud">
			<c:choose>
				<c:when test="${interests == 0}">
					No students have expressed interest for this flow
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${interests == 1}">
							<b>One</b> student has expressed interest for this flow
						</c:when>
						<c:otherwise>
							There are <b>${interests}</b> students that have expressed interest for this flow
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			</h4>
			
			<!-- tabella in cui si visualizzano gli insegnamenti riconosciuti per questo flusso -->
			<br>
			<c:choose>
				<c:when test="${fn:length(recognisedClasses) == 0}">
					<div class="row text-center">
					<h4>There are no acknowledged classes for <b><c:out value="${flow.id}"/></b>.</h4>
					</div>
				</c:when>
				<c:otherwise>	
					<table class="table table-bordered table-hover table-striped tablesorter" id="resultTable">
						<caption><h4>Acknowledged classes for this flow</h4></caption>
						<thead>
							<tr>
								<th>Name</th>
								<th>Area</th>
								<th>CFU</th>
								<th>Year</th>
								<th>Semester</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="recCl" items='${recognisedClasses}' varStatus="status">
								<tr>
									<td><a href="<c:url value="/class"/>?id=${recCl.id}" target="_blank">${recCl.nome}</a></td>
									<td>${recCl.nomeArea}</td>
									<td>${recCl.crediti}</td>
									<td>${recCl.annoCorso}</td>
									<td>${recCl.periodoErogazione}</td>
								</tr>
							</c:forEach>					
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
			<br>
			<c:choose>
				<c:when test="${fn:length(evaluations) == 0}">
					<div class="row text-center">
					<h4>There are no evaluations for <b><c:out value="${flow.id}"/></b>.</h4>
					</div>
				</c:when>
				<c:otherwise>
				<!-- inizio valutazioni -->
				<div class="header entity_top">
					<div class="row">
						<div style="text-align: center">
							<c:choose>
								<c:when test="${fn:length(evaluations) == 1}">
									<h4>There is <b>1</b> evaluation</h4>
								</c:when>
								<c:otherwise>
									<h4>There are <b><c:out value="${fn:length(evaluations)}"></c:out></b> evaluations</h4>
								</c:otherwise>
							</c:choose>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Gratification<br>
								<c:if test="${evaluationsAvg.gratification == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.gratification == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.gratification == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.gratification == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.gratification == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Academic Fulfillment<br> 
								<c:if test="${evaluationsAvg.academicFulfillment == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.academicFulfillment == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.academicFulfillment == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.academicFulfillment == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.academicFulfillment == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Didactics<br> 
								<c:if test="${evaluationsAvg.didactics == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.didactics == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.didactics == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.didactics == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.didactics == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Manager Evaluation<br> 
								<c:if test="${evaluationsAvg.managerEvaluation == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.managerEvaluation == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.managerEvaluation == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.managerEvaluation == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.managerEvaluation == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
								</c:if>  
							</div>
						</div>
					</div>
					<br>
				</div>
				<c:forEach var="eval" items='${evaluations}'>
				<!-- inizio singola valutazione -->
				<section class="entity_box">
					Inserted by <b><c:out value="${eval.nomeUtenteStudente}"></c:out></b> on <c:out value="${eval.dataInserimento}"></c:out>.
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3">
							Gratification <br> Academic Fulfillment <br> Didactics <br> Manager Evaluation <br>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2">
							<c:if test="${eval.soddEsperienza == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.soddEsperienza == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.soddEsperienza == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.soddEsperienza == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.soddEsperienza == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
							</c:if>  
							
							<c:if test="${eval.soddAccademica == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.soddAccademica == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.soddAccademica == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.soddAccademica == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.soddAccademica == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
							</c:if> 
							
							<c:if test="${eval.didattica == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.didattica == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.didattica == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.didattica == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.didattica == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
							</c:if> 
							
							<c:if test="${eval.valutazioneResponsabile == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.valutazioneResponsabile == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.valutazioneResponsabile == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.valutazioneResponsabile == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.valutazioneResponsabile == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
							</c:if> 
					</div>
						<div class="col-xs-12 col-sm-12 col-md-7">
							<p>
							<c:if test="${not empty eval.commento}">
								<c:out value="${eval.commento}"></c:out>
							</c:if> 
							</p>
						</div>
					</div>
				</section>
				<!-- fine singola valutazione -->
				</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />

</body>
</html>