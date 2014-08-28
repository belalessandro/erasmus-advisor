<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<title><c:out value="${thesis.nome}"/></title>
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
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/bootstrap-select.js"></script>
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>	
	<script src="<c:url value="/js"/>/star-rating.min.js"></script>	
	<script src="<c:url value="/js"/>/ea-form-validation.js"></script>
	<script src="<c:url value="/js"/>/ea-insert.js"></script> 
	
	<!-- Autocompletamento Universities - non va -->
	<script src="<c:url value="/js"/>/jquery-ui-1.10.4.custom.min.js"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
	<style>
		.ui-autocomplete-loading {
			background: white url("<c:url value="/img"/>/ui-anim_basic_16x16.gif") right center no-repeat;
		}
	</style>
	<script>
		$(function() {
			/*
			*	Inizializza i select avanzati
			*/
			$('.selectpicker').selectpicker({
		        style: 'btn-default',
		        size: false
		    });
			
			
			/*
			* 	Script di autocompletamento
			*/
			var cache = {};
			$("#universityNames").autocomplete(
				{
					minLength : 2,
					source : function(request, response)
					{
						var term = request.term;
						if (term in cache) 
						{
							response(cache[term]);
							return;
						}
						$.getJSON("<c:url value="/university/list"/>", request, 
							function(data, status, xhr) 
							{
								xhr.setRequestHeader(
										"X-Requested-With",
										"XMLHttpRequest");
								cache[term] = data;
								response(data);
							});
					}
				});
			
			/*
			*	Script per la gestione del tasto report
			*/
			$("#report-yes").click(function() {
				
				// data to send
				var jsonData = new Object();
				jsonData.operation = "report";
				jsonData.id = <c:out value="${thesis.id}"/>;
				
				// json object to send
			    var jsonobj = JSON.stringify(jsonData);
				
			    $.ajax({
			    	data : jsonobj,  
					contentType : "application/json",
					method : "POST",
					url : "<c:url value="/thesis"/>",
					success: function(data) { 
						
						if(data["report"] == "success")
						{
							// hide the button
							$("#report-button").hide();
							
							// hide the window
							$('#reportConfirmDialog').modal('hide');
							
							// show the alert-success
							$('#report-success').show();
							console.log("report: " + data["report"]);
						}
					},
					error: function(data) {console.log("EA ERROR: failed to report the entity."); }
			    }); // end ajax
			}); // end click
		}); // and document.ready
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
		
			<!-- Avviso di avvenuta modifica dell'entita -->
			<c:if test="${!empty param.edited && param.edited == 'success'}">
				<div class="alert alert-success alert-dismissible" role="alert" >
				  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				  <h4 class="text-center">Thesis Successfully Edited!</h4>
				</div>
			</c:if>
			
			<!-- Avviso che l'entità è in stato reported -->
			<c:if test="${!empty thesis.stato && thesis.stato == 'REPORTED'}">
				<div class="alert alert-danger" role="alert">
					<div class="text-center"><b> <span class="glyphicon glyphicon-star"></span> Warning:</b> 
					This thesis was recently reported to moderators for some reasons. </div>
				</div>
			</c:if>
			
			<!-- Avviso che l'entità è in stato not verified -->
			<c:if test="${!empty thesis.stato && thesis.stato == 'NOT VERIFIED'}">
				<div class="alert alert-warning" role="alert">
					<div class="text-center"><b> <span class="glyphicon glyphicon-star"></span> Warning:</b> 
					This thesis has not been verified yet. </div>
				</div>
			</c:if>
			
			<!-- Avviso del report avvenuto con successo -->
			<div id="report-success" class="alert alert-success" role="alert" style="display:none">
					<div class="text-center">Thesis successfully reported! </div> 
			</div>
			
			
			<div class="entity_details">
				<div class="entity_details_text">
					<h2><c:out value="${thesis.nome}"/></h2> 
					<p>
						University:	<a href="<c:url value="/university"/>?name=${fn:replace(thesis.nomeUniversita, ' ', '+')}" target="_blank">${thesis.nomeUniversita}</a><br> 
						Areas: 
						<c:forEach var="area" items='${areas}' varStatus="status">${area.nome}<c:if test="${!status.last}">, </c:if><c:if test="${status.last}">.</c:if></c:forEach>
						<br>
						Languages: 
						<c:forEach var="lang" items='${languages}' varStatus="status">${lang.nome}<c:if test="${!status.last}">, </c:if><c:if test="${status.last}">.</c:if></c:forEach>
						<br>											
						Supervised by:
						<c:forEach var="prof" items='${professors}' varStatus="status">${prof.nome} ${prof.cognome}<c:if test="${!status.last}">, </c:if><c:if test="${status.last}">.</c:if></c:forEach>
						<br>
						Avaible for 
						<c:choose>
							<c:when test="${(thesis.triennale) && (!thesis.magistrale)}">undergraduate </c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${(thesis.magistrale) && (!thesis.triennale)}">graduate </c:when>
									<c:otherwise>
										 both undergraduate and graduate 
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						students.
					</p>
				</div>
				<div class="entity_details_text">
					<!-- evalutate visibile solo da studente
						edit e delete solo da reponsabili di flusso e coordinatori erasmus -->
					<ul class="nav nav-stacked pull-right">
						<li class="active"><span data-toggle="modal" data-target="#evaluateForm">Evaluate</span></li>
						<c:if test="${!empty thesis.stato && thesis.stato == 'NOT VERIFIED'}">
							<li id="report-button" class="active"><span data-toggle="modal" data-target="#reportConfirmDialog">Report</span></li>
						</c:if>
						<li class="active"><span data-toggle="modal" data-target="#editForm">Edit</span></li>
						<li class="active">
							<form method="post" action="<c:url value="/thesis"/>">
                                <input type="hidden" name="operation" value="delete"/>
                                <input type="hidden" name="id" value="${thesis.id}"/>
                                <input type="hidden" name="name" value="${thesis.nome}"/>
								<input type="submit" value="Delete" class="btn btn-primary entity_nav_button">
							</form>
						</li>
					</ul>
				</div>
			</div>
			
			<!-- Finestra conferma Report a comparsa -->
			<div class="modal fade" id="reportConfirmDialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">Report to Administrator</h4>
						</div>
						<div class="modal-body">
							<p>
							<span class="glyphicon glyphicon-exclamation-sign"></span>
							Do you really want to report this thesis to moderators?
							</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
							<button id="report-yes" type="button" class="btn btn-primary">Yes</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
			
			<!--Form di valutazione a comparsa-->
			<div class="modal fade" id="evaluateForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Insert an evaluation for <b><c:out value="${thesis.nome}"/></b></h4>
						</div>
						<div class="modal-body">
							<!-- action deve puntare alla servlet che gestisce l'inserimento della valutazione -->
							<form name='thesisEvaluationForm' method="post" action="<c:url value="/thesis/evaluations"/>">
								<div class="col-md-6 text-center">Effort Needed:</div>
								<div class="col-md-6 text-center">
									<input id="effortNeeded" name="impegnoNecessario" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Subject Appeal:</div>
								<div class="col-md-6 text-center">
									<input id="subjectAppeal" name="interesseArgomento" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Supervisor Availability:</div>
								<div class="col-md-6 text-center">
									<input id="supervisorAvailability" name="disponibilitaRelatore" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Satisfaction:</div>
								<div class="col-md-6 text-center">
									<input id="satisfaction" name="soddisfazione" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br><br>								
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Comment</span> <textarea rows="2"  class="form-control" name="commento" id="comment" placeholder="Insert a general comment about the city."></textarea>
								</div>
								<br>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<input type="submit" value="Save!" class="btn btn-primary pull-right">
								</div>
								
								<!--  hidden params -->
								<input type="hidden" name="idArgomentoTesi" value="<c:out value="${thesis.id}"/>" />
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
							<h4 class="modal-title" id="myModalLabel">Edit <b><c:out value="${thesis.nome}"/></b></h4>
						</div>
						<div class="modal-body">
							<!-- action deve puntare alla servlet che gestisce la modifica dell'entità -->
							<!-- notare che ogni input deve avere il campo value settato a quanto è presente nel DB -->
							<form name='thesisEditForm' method="post" action="<c:url value="/thesis"/>">	
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Name*</span> <input type="text" class="form-control" name="nome" id="name" value="<c:out value="${thesis.nome}"/>">
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Professors*</span>
									<div id="profRow">
										<c:forEach var="prof" items='${professors}' varStatus="status">
											<input type="text" class="form-control insert_new_multiple_input" name="professorName" id="professorName" value="<c:out value="${prof.nome}"/>">
											<input type="text" class="form-control insert_new_multiple_input" name="professorSurname" id="professorSurname" value="<c:out value="${prof.cognome}"/>">
											<c:if test="${status.first}">
												<input class="insert_new_multiple_button btn btn-primary" type="button" value="Add Row" onclick="addRow('profRow', 'professorName', 'professorSurname');" />
											</c:if>
										</c:forEach>
									</div> 
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">University*</span> <input id="university" class="form-control" name="nomeUniversita" value="<c:out value="${thesis.nomeUniversita}"/>">
								</div>
								<br>
								<div class="row text-center">
									<span></span>
									<span class="input-group-addon insert_new_select_label_inline">Select the thesis' language*</span>
									<select class="selectpicker text-left" id="language" name="language" multiple data-width="auto">
										<c:forEach var="languageDomain" items='${languageDomain}'>
											<option value="${languageDomain.sigla}" 
												<c:forEach var="lingua" items='${languages}' >
												<c:if test="${languageDomain.nome == lingua.nome}">selected</c:if>
												</c:forEach>
											>
											${languageDomain.nome}</option>
										</c:forEach>
				    				</select>
								</div>
								<br>
								<div class="row text-center">
									<span></span>
									<span class="input-group-addon insert_new_select_label_inline">Select the thesis' area*</span>
									<select class="selectpicker text-left" id="language" name="area" multiple data-width="auto">
										<c:forEach var="areaDomain" items='${areaDomain}'>
											<option value="${areaDomain.nome}" 
												<c:forEach var="area" items='${areas}' >
												<c:if test="${areaDomain.nome == area.nome}">selected</c:if>
												</c:forEach>
											>
											${areaDomain.nome}</option>
										</c:forEach>
				    				</select>
								</div>
								<br>

								<div class="row text-center">
									Choose the thesis' availability (one or both):
								</div>
								<div class="col-md-4">
									<input type="checkbox" id="undergraduate" name="triennale" value="undergraduate" <c:if test="${thesis.triennale}">checked</c:if>> Undergraduate
								</div>
								<div class="col-md-4">
									<input type="checkbox" id="graduate" name="magistrale" value="graduate" <c:if test="${thesis.magistrale}">checked</c:if>> Graduate
								</div>
								<br>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<input type="submit" value="Save your changes" class="btn btn-primary pull-right">
								</div>
								
								<!-- hidden params -->
								<input type="hidden" name="operation" value="update" />
								<input type="hidden" name="id" value="<c:out value="${thesis.id}"/>" />
								<input type="hidden" name="stato" value="<c:out value="${thesis.stato}"/>" />
								
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- fine Form di valutazione a comparsa-->	
			<br>	
			<c:choose>
				<c:when test="${fn:length(evaluations) == 0}">
					<div class="row text-center">
					<h4>There are no evaluations for <b><c:out value="${thesis.nome}"/></b>.</h4>
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
								Effort Needed<br>
								<c:if test="${evaluationsAvg.effortNeeded == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.effortNeeded == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.effortNeeded == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.effortNeeded == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.effortNeeded == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Subject Appeal<br> 
								<c:if test="${evaluationsAvg.subjectAppeal == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.subjectAppeal == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.subjectAppeal == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.subjectAppeal == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.subjectAppeal == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Supervisor Availability<br> 
								<c:if test="${evaluationsAvg.supervisorAvailability == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.supervisorAvailability == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.supervisorAvailability == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.supervisorAvailability == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.supervisorAvailability == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Satisfaction<br> 
								<c:if test="${evaluationsAvg.satisfaction == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.satisfaction == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.satisfaction == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.satisfaction == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.satisfaction == 5}">
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
							Effort Needed <br> Subject Appeal <br> Supervisor Availability <br> Satisfaction <br>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2">
							<c:if test="${eval.impegnoNecessario == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.impegnoNecessario == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.impegnoNecessario == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.impegnoNecessario == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.impegnoNecessario == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
							</c:if>  
							
							<c:if test="${eval.interesseArgomento == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.interesseArgomento == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.interesseArgomento == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.interesseArgomento == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.interesseArgomento == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
							</c:if> 
							
							<c:if test="${eval.disponibilitaRelatore == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.disponibilitaRelatore == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.disponibilitaRelatore == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.disponibilitaRelatore == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.disponibilitaRelatore == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
							</c:if> 
							
							<c:if test="${eval.soddisfazione == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.soddisfazione == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.soddisfazione == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.soddisfazione == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.soddisfazione == 5}">
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