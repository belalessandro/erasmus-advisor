<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
	<title><c:out value="${city.nome}"/></title>
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
		function doDelete()
		{
			var r = confirm("Are you sure you want to delete this entity from the database? The action is not reversible.");
			if (r == true) 
			{
			    // procedere con la cancellazione
			    $.ajax({
			          type: "POST",
			          url: "<c:url value="/city"/>",
			          dataType: "json",
			          data: { 'operation' : 'delete', 'city' : '<c:out value="${city.nome}"/>', 'country' : '<c:out value="${city.stato}"/>'},
		              success: function(data) {
		                  window.location.href = "<c:url value="/js"/>" + data.url;
		                } 
			        });
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
					<h2><c:out value="${city.nome}"/></h2> 
					<p>
						Country: <c:out value="${city.stato}"/> <br> 
						Spoken Languages:
						<c:forEach var="lingua" items='${languages}' varStatus="status">
							${lingua.nome}
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
							<h4 class="modal-title" id="myModalLabel">Insert an evaluation for <b><c:out value="${city.nome}"/></b></h4>
						</div>
						<div class="modal-body">
							<!-- action deve puntare alla servlet che gestisce l'inserimento della valutazione -->
							<form name='cityEvaluationForm' onSubmit="return xEvaluationFormValidation();" method="post" action="<c:url value="/student/evaluate"/>">
								<div class="col-md-6 text-center">Cost of life:</div>
								<div class="col-md-6 text-center">
									<input id="costOfLife" name="costoDellaVita" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">House availability:</div>
								<div class="col-md-6 text-center">
									<input id="houseAvailability" name="disponibilitaAlloggi" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Liveability:</div>
								<div class="col-md-6 text-center">
									<input id="liveability" name="vivibilitaUrbana" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Social life:</div>
								<div class="col-md-6 text-center">
									<input id="socialLife" name="vitaSociale" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
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
                
                                <!-- Tag necessario per il riconoscimento del tipo di valutazione lato servlet -->
                                <input type="hidden" name="TypeOfEvaluation" value="city" />
                                <input type="hidden" name="nomeCitta" value="${city.nome}">
                                <input type="hidden" name="statoCitta" value="${city.stato}">
                                
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
							<h4 class="modal-title" id="myModalLabel">Edit <b><c:out value="${city.nome}"/></b></h4>
						</div>
						<div class="modal-body">
							<!-- action deve puntare alla servlet che gestisce la modifica dell'entità -->
							<!-- notare che ogni input deve avere il campo value settato a quanto è presente nel DB -->
							<form name='cityEditForm' onSubmit="return xEditFormValidation();" method="post" action="">
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Name*</span> <input type="text" class="form-control" name="name" id="name" value="<c:out value="${city.nome}"/>">
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Country*</span> <input type="text" id="country" class="form-control" name="country" value="<c:out value="${city.stato}"/>">
								</div>
								<br>
								<div class="row text-center">
									<span></span><span class="input-group-addon insert_new_select_label_inline">Select the languages spoken in the city*</span> 
									<select class="selectpicker text-left" multiple id="language" name="language[]">
									<c:forEach var="languageDomain" items='${languageDomain}'>
										<option 
											<c:forEach var="lingua" items='${languages}' >
											<c:if test="${languageDomain.nome == lingua.nome}">selected</c:if>
											</c:forEach>
										>
										${languageDomain.nome}</option>
									</c:forEach>
									</select>
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
					<h3>There are no evaluations for <b><c:out value="${city.nome}"/></b>.</h3>
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
								Cost of life<br>
								<c:if test="${evaluationsAvg.costOfLife == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.costOfLife == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.costOfLife == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.costOfLife == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.costOfLife == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								House availability<br> 
								<c:if test="${evaluationsAvg.houseAvailability == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.houseAvailability == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.houseAvailability == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.houseAvailability == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.houseAvailability == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Liveability<br> 
								<c:if test="${evaluationsAvg.liveability == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.liveability == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.liveability == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.liveability == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.liveability == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Social Life<br> 
								<c:if test="${evaluationsAvg.socialLife == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.socialLife == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.socialLife == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.socialLife == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.socialLife == 5}">
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
							Cost of life <br> House availability <br> Liveability <br> Social Life <br>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2">
							<c:if test="${eval.costoDellaVita == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.costoDellaVita == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.costoDellaVita == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.costoDellaVita == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
							</c:if>  
							<c:if test="${eval.costoDellaVita == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							
							<c:if test="${eval.disponibilitaAlloggi == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.disponibilitaAlloggi == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.disponibilitaAlloggi == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.disponibilitaAlloggi == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
							</c:if>  
							<c:if test="${eval.disponibilitaAlloggi == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if> 
							
							<c:if test="${eval.vivibilitaUrbana == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.vivibilitaUrbana == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.vivibilitaUrbana == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.vivibilitaUrbana == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
							</c:if>  
							<c:if test="${eval.vivibilitaUrbana == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if> 
							
							<c:if test="${eval.vitaSociale == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.vitaSociale == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.vitaSociale == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.vitaSociale == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br> 
							</c:if>  
							<c:if test="${eval.vitaSociale == 5}">
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