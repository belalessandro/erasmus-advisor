<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<!DOCTYPE html>
<html>
<head>
	<title><c:out value="${university.nome}"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	
	<!-- CSS -->
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/fonts"/>/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
		
	<!-- componenti aggiuntivi -->
	<link href="<c:url value="/css"/>/star-rating.css" rel="stylesheet">
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>	
	<script src="<c:url value="/js"/>/star-rating.min.js"></script>	
	<script src="<c:url value="/js"/>/ea-form-validation.js"></script>
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
			<!-- Showed when successfully edited -->
			<c:if test="${!sessionScope.loggedUser.student}">
				<c:if test="${!empty param.edited && param.edited == 'success'}">
					<div class="alert alert-success alert-dismissible" role="alert" >
					  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					  <h4 class="text-center">University Successfully Edited!</h4>
					</div>
				</c:if>
			</c:if>
			
			<div class="entity_details">
				<div class="entity_details_text">
					<h2><c:out value="${university.nome}"/></h2> 
					<p>
						Located in <a href="<c:url value="/city"/>?name=${fn:replace(university.nomeCitta, ' ', '+')}&country=${fn:replace(university.statoCitta, ' ', '+')}" target="_blank">
							<c:out value="${university.nomeCitta}"/> (<c:out value="${university.statoCitta}"/>)</a> <br> 
						Link to the official website: <a href="<c:out value="${university.link}"/>" target="_blank"><c:out value="${university.link}"/></a> <br> 
						<a href="http://www.topuniversities.com/university-rankings/world-university-rankings/2013#sorting=rank+region=140+country=+faculty=+stars=false+search=" target=_blank">World Ranking</a>: 
						<c:out value="${university.posizioneClassifica}"/> <br> 
						<c:choose><c:when test="${university.presenzaAlloggi}">Has </c:when><c:otherwise>Has not </c:otherwise></c:choose>
						a residence for Erasmus Students. <br> 
					</p>
				</div>
				<div class="entity_details_text">
					<ul class="nav nav-stacked pull-right">
						<c:if test="${sessionScope.loggedUser.student}">
							<c:if test="${!empty evalEnabled && evalEnabled == 'enabled'}">
								<li class="active"><span data-toggle="modal" data-target="#evaluateForm">Evaluate</span></li>
							</c:if>
						</c:if>
						<c:if test="${!sessionScope.loggedUser.student}">
							<li class="active"><span data-toggle="modal" data-target="#editForm">Edit</span></li>
							<li class="active">
								<form method="post" action="<c:url value="/university"/>">
	                                <input type="hidden" name="operation" value="delete"/>
	                                <input type="hidden" name="name" value="${university.nome}"/>
									<input type="submit" value="Delete" class="btn btn-primary entity_nav_button"
										onclick="return confirm('Do you really want to remove this university from the database?');">
								</form>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
			
			<!--Form di valutazione a comparsa-->
			<c:if test="${sessionScope.loggedUser.student}">
			<div class="modal fade" id="evaluateForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Insert an evaluation for <b><c:out value="${university.nome}"/></b></h4>
						</div>
						<div class="modal-body">
							<form name='universityEvaluationForm' method="post" action="<c:url value="/university/evaluations"/>">
								<div class="col-md-6 text-center">Urban Location:</div>
								<div class="col-md-6 text-center">
									<input id="urbanLocation" name="collocazioneUrbana" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Erasmus Events:</div>
								<div class="col-md-6 text-center">
									<input id="erasmusEvents" name="iniziativeErasmus" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Teachings Quality:</div>
								<div class="col-md-6 text-center">
									<input id="teachingsQuality" name="qtaInsegnamenti" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Classroom Quality:</div>
								<div class="col-md-6 text-center">
									<input id="classroomQuality" name="qtaAule" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
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
								<input type="hidden" name="nomeUniversita" value="<c:out value="${university.nome}"/>"/>
                                <input type="hidden" name="operation" value="insert"/>
								                               
							</form>
						</div>
					</div>
				</div>
			</div>
			</c:if>
			<!-- fine Form di valutazione a comparsa-->
			
			<!--Form di edit a comparsa-->
			<c:if test="${!sessionScope.loggedUser.student}">
			<div class="modal fade" id="editForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Edit <b><c:out value="${university.nome}"/></b></h4>
						</div>
						<div class="modal-body">
							<form name='universityEditForm'  method="post" action="<c:url value="/university"/>" onSubmit="return editUniversityFormValidation();">
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Name*</span> <input type="text" class="form-control" name="nome" id="name" value="<c:out value="${university.nome}"/>">
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Country*</span> <input id="country" class="form-control" name="statoCitta" value="<c:out value="${university.statoCitta}"/>">
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">City*</span> <input id="city" class="form-control" name="nomeCitta" value="<c:out value="${university.nomeCitta}"/>">
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Link*</span> <input id="link" class="form-control" name="link" value="<c:out value="${university.link}"/>">
								</div>
								<br>
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input_small">
										<a href="http://www.topuniversities.com/university-rankings/world-university-rankings/2013#sorting=rank+region=140+country=+faculty=+stars=false+search=" target=_blank">Position in university ranking*</a>
									</span> 
									<input type="text" class="form-control" name="posizioneClassifica" id="ranking" value="<c:out value="${university.posizioneClassifica}"/>">
								</div>
								<br>
								<div class="row text-center">
									<input type="checkbox" id="hasResidence" name="presenzaAlloggi" value="hasResidence" <c:if test="${university.presenzaAlloggi}">checked</c:if>> Select if the university has a residence for Erasmus students.
								</div>												
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<input type="submit" value="Save your changes" class="btn btn-primary pull-right">
								</div>
								
								<!-- hidden params -->
								<input type="hidden" name="operation" value="edit"/>
								<input type="hidden" name="old_name" value="<c:out value="${university.nome}"/>"/>
							</form>
						</div>
					</div>
				</div>
			</div>
			</c:if>
			<!-- fine Form di edit a comparsa-->	
			<br>	
			<c:choose>
				<c:when test="${fn:length(evaluations) == 0}">
					<div class="row text-center">
					<h4>There are no evaluations for <b><c:out value="${university.nome}"/></b>.</h4>
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
								Urban Location<br>
								<c:if test="${evaluationsAvg.urbanLocation == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.urbanLocation == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.urbanLocation == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.urbanLocation == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.urbanLocation == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Erasmus Events<br> 
								<c:if test="${evaluationsAvg.erasmusEvents == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.erasmusEvents == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.erasmusEvents == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.erasmusEvents == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.erasmusEvents == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Teachings Quality<br> 
								<c:if test="${evaluationsAvg.teachingsQuality == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.teachingsQuality == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.teachingsQuality == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.teachingsQuality == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.teachingsQuality == 5}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
								</c:if>  
							</div>
							<div class="col-xs-3 col-sm-3 col-md-3">
								Classroom Quality<br> 
								<c:if test="${evaluationsAvg.classroomQuality == 1}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.classroomQuality == 2}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.classroomQuality == 3}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
								</c:if>  
								<c:if test="${evaluationsAvg.classroomQuality == 4}">
									<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
								</c:if>  
								<c:if test="${evaluationsAvg.classroomQuality == 5}">
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
							Urban Location <br> Erasmus Events <br> Teachings Quality <br> Classroom Quality <br>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2">
							<c:if test="${eval.collocazioneUrbana == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.collocazioneUrbana == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.collocazioneUrbana == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.collocazioneUrbana == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.collocazioneUrbana == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
							</c:if>  
							
							<c:if test="${eval.iniziativeErasmus == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.iniziativeErasmus == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.iniziativeErasmus == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.iniziativeErasmus == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.iniziativeErasmus == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
							</c:if> 
							
							<c:if test="${eval.qtaInsegnamenti == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.qtaInsegnamenti == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.qtaInsegnamenti == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.qtaInsegnamenti == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.qtaInsegnamenti == 5}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <br>  
							</c:if> 
							
							<c:if test="${eval.qtaAule == 1}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.qtaAule == 2}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.qtaAule == 3}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> 
							</c:if>  
							<c:if test="${eval.qtaAule == 4}">
								<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>   
							</c:if>  
							<c:if test="${eval.qtaAule == 5}">
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