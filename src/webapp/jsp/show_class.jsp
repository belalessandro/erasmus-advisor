<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 
	manca la servlet per il delete, il report, l'update e la valutazione
 -->

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
		
	<script src="<c:url value="/js"/>/ea-form-validation.js"></script>
	
	<!-- componenti aggiuntivi -->
	<link href="<c:url value="/css"/>/bootstrap-select.min.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/star-rating.css" rel="stylesheet">
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/bootstrap-select.js"></script>
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>	
	<script src="<c:url value="/js"/>/star-rating.min.js"></script>	
	
	<script>
		// funziona che notifica che questa entità è da segnare REPORTED
		// da fare con ajax
	    function report()
	    {
	    	var r = confirm("Do you really want to report this class to the moderators?");
			if (r == true) 
			{
			    // procedere con la cancellazione
			} 
			else {
			    // splash! and nothing happens
			} 
	    }
		// inizializza i select avanzati
		$(document).ready(function() {
		    $('.selectpicker').selectpicker({
		        style: 'btn-default',
		        size: false
		    });
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
		<!-- di fatto tutta questa pagina è generata con JSP -->
		<div class="col-md-9 general_main_border">
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
						<c:forEach var="prof" items='${professors}' varStatus="status">
							${prof.nome} ${prof.cognome}
							<c:if test="${!status.last}">, </c:if>
							<c:if test="${status.last}">.</c:if>
						</c:forEach>
						<br>
					</p>
				</div>
				<div class="entity_details_text">
					<!-- evalutate visibile solo da studente
						edit e delete solo da reponsabili di flusso e coordinatori erasmus -->
					<ul class="nav nav-stacked pull-right">
						<li class="active"><span data-toggle="modal" data-target="#evaluateForm">Evaluate</span></li>
						<li class="active"><span onClick="report();">Report</span></li>
						<li class="active"><span data-toggle="modal" data-target="#editForm">Edit</span></li>
						<li class="active">
							<form method="post" action="#">
								<input type="submit" value="Delete" class="btn btn-primary entity_nav_button">
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
							<h4 class="modal-title" id="myModalLabel">Insert an evaluation for <b><c:out value="${classBean.nome}"/></b></h4>
						</div>
						<div class="modal-body">
							<!-- action deve puntare alla servlet che gestisce l'inserimento della valutazione -->
							<form name='classEvaluationForm' onSubmit="return xEvaluationFormValidation();" method="post" action="#">
								<div class="col-md-6 text-center">Teaching Quality:</div>
								<div class="col-md-6 text-center">
									<input id="teachingQuality" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Schedule Compliance:</div>
								<div class="col-md-6 text-center">
									<input id="scheduleCompliance" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Difficulty:</div>
								<div class="col-md-6 text-center">
									<input id="difficulty" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Interest:</div>
								<div class="col-md-6 text-center">
									<input id="interest" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br><br>								
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Comment</span> <textarea rows="2" class="form-control" name="comment" id="comment" placeholder="Insert a general comment about the city."></textarea>
								</div>
								<br>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<input type="submit" value="Save!" class="btn btn-primary pull-right">
								</div>
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
							<form name='classEditForm' onSubmit="return xEditFormValidation();" method="post" action="#">	
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Name*</span> <input type="text" class="form-control" name="name" id="name" value="<c:out value="${classBean.nome}"/>">
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
									<select class="selectpicker text-left" id="area" name="area">
											<c:forEach var="areaDomain" items='${areaDomain}'>
											<option 
												<c:if test="${areaDomain.nome == classBean.nomeArea}">selected</c:if>
											>
											${areaDomain.nome}</option>
										</c:forEach>					
				    				</select>
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">University*</span> <input id="university" class="form-control" name="university" value="<c:out value="${classBean.nomeUniversita}"/>">
								</div>
								<br>
								<div class="row text-center">
									<span></span>
									<span class="input-group-addon insert_new_select_label_inline">Select the class' language*</span>
									<select class="selectpicker text-left" id="language" name="language">
										<c:forEach var="languageDomain" items='${languageDomain}'>
											<option 
												<c:if test="${languageDomain.sigla == classBean.nomeLingua}">selected</c:if>
											>
											${languageDomain.nome}</option>
										</c:forEach>
				    				</select>
								</div>
								<br>
								<div class="input-group insert_new_input_group text-center">
									<div class="input-group insert_new_input_group">
										<span class="input-group-addon insert_new_input">Credits*</span> <input type="text" class="form-control" name="credits" id="credits" value="<c:out value="${classBean.crediti}"/>">
									</div>
								</div>
								<br>
								<div class="input-group insert_new_input_group text-center">
									<div class="input-group insert_new_input_group">
										<span class="input-group-addon insert_new_input">Year*</span> <input type="text" class="form-control" name="year" id="year" value="<c:out value="${classBean.annoCorso}"/>">
									</div>
								</div>
								<br>
								<div class="input-group insert_new_input_group text-center">
									<div class="input-group insert_new_input_group">
										<span class="input-group-addon insert_new_input">Semester*</span> <input type="text" class="form-control" name="semester" id="semester" value="<c:out value="${classBean.periodoErogazione}"/>">
									</div>
								</div>
								<br>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<input type="submit" value="Save your changes" class="btn btn-primary pull-right">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- fine Form di valutazione a comparsa-->
			<c:choose>
				<c:when test="${fn:length(evaluations) == 0}">
					<div class="row text-center">
					<h3>There are no evaluations for <b><c:out value="${classBean.nome}"/></b>.</h3>
					</div>
				</c:when>
				<c:otherwise>
				<!-- inizio valutazioni -->
				<div class="header entity_top">
					<div class="row">
						<div style="text-align: center">
							<c:choose>
								<c:when test="${fn:length(evaluations) == 1}">
									<h3>There is <b>1</b> evaluation</h3>
								</c:when>
								<c:otherwise>
									<h3>There are <b><c:out value="${fn:length(evaluations)}"></c:out></b> evaluations</h3>
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