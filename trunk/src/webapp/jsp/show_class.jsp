<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<title><c:out value="${classBean.nome}"/></title>
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
	
	<!-- Autocompletamento Universities -->
	<script src="<c:url value="/js"/>/jquery-ui-1.10.4.custom.min.js"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
	<style>
		.ui-autocomplete-loading {
			background: white url("<c:url value="/img"/>/ui-anim_basic_16x16.gif") right center no-repeat;
		}
	</style>
	<script>
		/*
		*	Scripts
		*/
		$(function() {
			
			/*
			*	Inizializza i select avanzati
			*/
			$(document).ready(function() {
			    $('.selectpicker').selectpicker({
			        style: 'btn-default',
			        size: false
			    });
			});
			
			/*
			*	Script per l'autocompletamento
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
				jsonData.id = <c:out value="${classBean.id}"/>;
				
				// json object to send
			    var jsonobj = JSON.stringify(jsonData);
				
			    $.ajax({
			    	data : jsonobj,  
					contentType : "application/json",
					method : "POST",
					url : "<c:url value='/class'/>",
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
			}); // end click function report-yes
		}); // end ready function jQuery
		
		
	</script>
	
	<script>
		// funziona che aggiunge che questo corso è riconosciuto per il flusso
		// da fare con ajax
	    function acknowledge()
	    {
	    	alert(document.getElementById("ackFlow").value);
	    }
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
		<!-- di fatto tutta questa pagina è generata con JSP -->
		<div class="col-md-9 general_main_border">
			
			<!-- Avviso di avvenuta modifica -->
			<c:if test="${!empty param.edited && param.edited == 'success'}">
				<div class="alert alert-success alert-dismissible" role="alert" >
				  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				 <h4 class="text-center">Class Successfully Edited!</h4>
				</div>
			</c:if>
			
			<!-- Avviso che l'entità è in stato reported -->
			<c:if test="${!empty classBean.stato && classBean.stato == 'REPORTED'}">
				<div class="alert alert-warning" role="alert">
					<b> <span class="glyphicon glyphicon-star"></span> Warning:</b> 
					The following thesis was recently reported to moderators for some reasons. 
				</div>
			</c:if>
			
			<!-- Avviso del report avvenuto con successo -->
			<div id="report-success" class="alert alert-success" role="alert" style="display:none">
					Thesis Successfully Reported! 
			</div>
			
			
			<div class="entity_details">
				<div class="entity_details_text">
					<h2><c:out value="${classBean.nome}"/></h2> 
					<p>
						University:	<a href="<c:url value="/university"/>?name=${fn:replace(classBean.nomeUniversita, ' ', '+')}" target="_blank">${classBean.nomeUniversita}</a><br> 
						Area: <c:out value="${classBean.nomeArea}"/> <br> 
						Language: <c:out value="${language.nome}"/> <br> 
						Credits: <c:out value="${classBean.crediti}"/> <br> 
						<c:out value="${classBean.periodoErogazione}"/> Period of <c:out value="${classBean.annoCorso}"/> Year <br> 
						Taught by:
						<c:forEach var="prof" items='${professors}' varStatus="status">${prof.nome} ${prof.cognome}<c:if test="${!status.last}">, </c:if><c:if test="${status.last}">.</c:if></c:forEach>
						<br>
					</p>
				</div>
				<div class="entity_details_text">
					<!-- evalutate visibile solo da studente
						edit e delete solo da reponsabili di flusso e coordinatori erasmus -->
					<ul class="nav nav-stacked pull-right">
						<li class="active"><span data-toggle="modal" data-target="#evaluateForm">Evaluate</span></li>
						<li class="active"><span data-toggle="modal" data-target="#acknowledgeForm">Acknowledge</span></li>
						<!-- Visualizza il tasto report solo se non in stato non verificato e non reported -->
						<!-- TODO: inserire il controllo utente  -->
						<c:if test="${!empty classBean.stato && classBean.stato == 'NOT VERIFIED'}">
							<li class="active" id="report-button"><span data-toggle="modal" data-target="#reportConfirmDialog">Report</span></li>
						</c:if>
						<li class="active"><span data-toggle="modal" data-target="#editForm">Edit</span></li>
						<li class="active">
							<form method="post" action="<c:url value="/class"/>">
                                <input type="hidden" name="operation" value="delete"/>
                                <input type="hidden" name="id" value="${classBean.id}"/>
                                <input type="hidden" name="name" value="${classBean.nome}"/>
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
							Do you really want to report this class to moderators?
							</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
							<button id="report-yes" type="button" class="btn btn-primary">Yes</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
			<br>
			<!--Form di riconoscimento a comparsa-->
			<div class="modal fade" id="acknowledgeForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Select a flow that acknowledge <b><c:out value="${classBean.nome}"/></b></h4>
						</div>
						<div class="modal-body">
							<div class="row text-center">
								<span></span>
								<span class="input-group-addon insert_new_select_label_inline">Select the flow</span>
								<select class="selectpicker text-left" id="ackFlow" name="ackFlow">
									<c:forEach var="flow" items='${flows}'>
										<option value="${flow.idFlusso}">
										${flow.idFlusso}</option>
									</c:forEach>					
			    				</select>
							</div>
							<br>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								<input type="button" value="Save!" class="btn btn-primary pull-right" data-dismiss="modal" onclick="acknowledge();">
							</div>
						</div>
					</div>
				</div>
			</div>			
			<!--fine form di riconoscimento a comparsa-->
			
			<!--Form di valutazione a comparsa-->
			<div class="modal fade" id="evaluateForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Insert an evaluation for <b><c:out value="${classBean.nome}"/></b></h4>
						</div>
						<div class="modal-body">
							<!-- action deve puntare alla servlet che gestisce l'inserimento della valutazione -->
							<form name='classEvaluationForm' method="post" action="class/evaluations">
								<div class="col-md-6 text-center">Teaching Quality:</div>
								<div class="col-md-6 text-center">
									<input id="teachingQuality" name="qtaInsegnamanto" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Schedule Compliance:</div>
								<div class="col-md-6 text-center">
									<input id="scheduleCompliance" name="rispettoDelleOre" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Difficulty:</div>
								<div class="col-md-6 text-center">
									<input id="difficulty" name="difficolta" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Interest:</div>
								<div class="col-md-6 text-center">
									<input id="interest" name="interesse" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br><br>								
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Comment</span> <textarea name="commento" rows="2" class="form-control" name="comment" id="comment" placeholder="Insert a general comment about the city."></textarea>
								</div>
								<br>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<input type="submit" value="Save!" class="btn btn-primary pull-right">
								</div>
								
								<!-- Hidden params -->
								<input type="hidden" name="idInsegnamento" value='<c:out value="${classBean.id}"/>' /> 
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
							<h4 class="modal-title" id="myModalLabel">Edit <b><c:out value="${classBean.nome}"/></b></h4>
						</div>
						<div class="modal-body">
							<!-- action deve puntare alla servlet che gestisce la modifica dell'entità -->
							<!-- notare che ogni input deve avere il campo value settato a quanto è presente nel DB -->
							<form name='classEditForm' method="post" action='<c:url value="/class"/>'>	
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Name*</span> <input type="text" class="form-control" name="nome" id="name" value="<c:out value="${classBean.nome}"/>">
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
								<div class="row text-center">
									<span></span>
									<span class="input-group-addon insert_new_select_label_inline">Select the class' area*</span>
									<select class="selectpicker text-left" id="area" name="nomeArea">
											<c:forEach var="areaDomain" items='${areaDomain}'>
											<option value="${areaDomain.nome}" 
												<c:if test="${areaDomain.nome == classBean.nomeArea}">selected</c:if>
											>
											${areaDomain.nome}</option>
										</c:forEach>					
				    				</select>
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">University*</span> <input id="universityNames" class="form-control" name="nomeUniversita" value="<c:out value="${classBean.nomeUniversita}"/>">
								</div>
								<br>
								<div class="row text-center">
									<span></span>
									<span class="input-group-addon insert_new_select_label_inline">Select the class' language*</span>
									<select class="selectpicker text-left" id="language" name="nomeLingua">
										<c:forEach var="languageDomain" items='${languageDomain}'>
											<option value="${languageDomain.sigla}" 
												<c:if test="${languageDomain.sigla == classBean.nomeLingua}">selected</c:if>
											>
											${languageDomain.nome}</option>
										</c:forEach>
				    				</select>
								</div>
								<br>
								<div class="input-group insert_new_input_group text-center">
									<div class="input-group insert_new_input_group">
										<span class="input-group-addon insert_new_input">Credits*</span> <input type="text" class="form-control" name="crediti" id="credits" value="<c:out value="${classBean.crediti}"/>">
									</div>
								</div>
								<br>
								<div class="input-group insert_new_input_group text-center">
									<div class="input-group insert_new_input_group">
										<span class="input-group-addon insert_new_input">Year*</span> <input type="text" class="form-control" name="annoCorso" id="year" value="<c:out value="${classBean.annoCorso}"/>">
									</div>
								</div>
								<br>
								<div class="input-group insert_new_input_group text-center">
									<div class="input-group insert_new_input_group">
										<span class="input-group-addon insert_new_input">Semester*</span> <input type="text" class="form-control" name="periodoErogazione" id="semester" value="<c:out value="${classBean.periodoErogazione}"/>">
									</div>
								</div>
								<br>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<input type="submit" value="Save your changes" class="btn btn-primary pull-right">
								</div>
								
								<!-- Hidden params -->
								<input type="hidden" name="operation" value="edit"/>
								<input type="hidden" name="id" value='<c:out value="${classBean.id}"/>' />
								
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- fine Form di edit a comparsa-->	
			<br>	
			<c:choose>
				<c:when test="${fn:length(evaluations) == 0}">
					<div class="row text-center">
					<h4>There are no evaluations for <b><c:out value="${classBean.nome}"/></b>.</h4>
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
								Teaching quality<br>
								<c:if test="${evaluationsAvg.teachingQuality == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.teachingQuality == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.teachingQuality == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.teachingQuality == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.teachingQuality == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Schedule compliance<br> 
								<c:if test="${evaluationsAvg.scheduleCompliance == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.scheduleCompliance == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.scheduleCompliance == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.scheduleCompliance == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.scheduleCompliance == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Difficulty<br> 
								<c:if test="${evaluationsAvg.difficulty == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.difficulty == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.difficulty == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.difficulty == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.difficulty == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Interest<br> 
								<c:if test="${evaluationsAvg.interest == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.interest == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.interest == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.interest == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.interest == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
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
							Teaching Quality <br> Schedule Compliance <br> Difficulty <br> Interest <br>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2">
							<c:if test="${eval.qtaInsegnamanto == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.qtaInsegnamanto == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.qtaInsegnamanto == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.qtaInsegnamanto == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
							</c:if>  
							<c:if test="${eval.qtaInsegnamanto == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							
							<c:if test="${eval.rispettoDelleOre == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.rispettoDelleOre == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.rispettoDelleOre == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.rispettoDelleOre == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
							</c:if>  
							<c:if test="${eval.rispettoDelleOre == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if> 
							
							<c:if test="${eval.difficolta == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.difficolta == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.difficolta == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.difficolta == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
							</c:if>  
							<c:if test="${eval.difficolta == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if> 
							
							<c:if test="${eval.interesse == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.interesse == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.interesse == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.interesse == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
							</c:if>  
							<c:if test="${eval.interesse == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
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