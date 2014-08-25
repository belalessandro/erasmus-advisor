<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- luca: non mi è ben chiaro come dovrebbe funzionare il form di questa pagina
	per quanto riguarda il passaggio delle informazioni al server.
	anche cercando in internet non sembra che la gente lo sappia -->

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>User Profile</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/fonts"/>/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	
	<script src="<c:url value="/js"/>/ea-form-validation.js"></script>
	
	<!-- Datepicker -->
	<link href="<c:url value="/css"/>/datepicker3.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/ui-lightness/jquery-ui-1.10.4.custom.css" rel="stylesheet">
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/jquery-1.10.2.js"></script>
	<script src="<c:url value="/js"/>/jquery-ui-1.10.4.custom.js"></script>
	<script src="<c:url value="/js"/>/ea-basic.js"></script>
	<script src="<c:url value="/js"/>/bootstrap-datepicker.js"></script>
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>
</head>

<body role="document">
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp">
			<jsp:param name="pageName" value="user_profile"/>
		</jsp:include>
		<!-- menu -->
		<jsp:include page="/jsp/include/menu.jsp" />
    	
		<!-- pagina -->
		<div class="col-md-9 general_main_border">
			<h2 align="center">User Account</h2>
			<br>

			<div class="panel panel-default">
				<!-- Default panel contents -->
				<div class="col-sm-1 column"></div>
				<div class="panel-heading">
					<div class="row">
						<div class="col-sm-6 column" style="padding-top:8px !important;">
							<strong>Account Informations</strong>
						</div>
						<!--Bottone modifica informazioni-->
						<div class="col-sm-6 column" align="right">
							<button class="btn btn-primary" data-toggle="modal" data-target="#myModal">
								<span class="glyphicon glyphicon glyphicon-pencil"></span> Edit your account
							</button>
						</div>

						<!--Form di modifica a comparsa-->
						<!-- notare che ogni input deve avere il campo value settato a quanto è presente nel DB -->
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h3 class="modal-title" id="myModalLabel">Edit your profile information</h3>
									</div>
									<div class="modal-body">
										<!-- action deve puntare alla servlet che gestisce il cambio delle informazioni -->
										<!-- notare che ogni input deve avere il campo value settato a quanto è presente nel DB -->
										<form name='registration' onSubmit="return userProfileFormValidation();" method="post" action="#">
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">Username*</span> <input type="text" class="form-control" name="user" id="user" value="<c:out value="${student.nomeUtente}"/>">
											</div>
											<br>
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">E-mail*</span> <input type="text" class="form-control" name="email" id="email" value="<c:out value="${student.email}"/>">
											</div>
											<br>
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">Password*</span> <input type="password" class="form-control" name="password" id="password" value="<c:out value="${student.password}"/>">
											</div>
											<br>
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">Confirm Password*</span> <input type="password" class="form-control" name="password2" id="password2" value="<c:out value="${student.password}" />">
											</div>
											<br>
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">University</span> <input id="autocomplete" class="form-control" name="university" value="<c:out value="${course.nomeuniversita}" />">
											</div>
											<br>
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">Degree Course</span> <input id="autocomplete2" class="form-control" name="degree" value="<c:out value="${course.nome}" />">
											</div>
											<br>
											<div class="row">
												<div class="col-lg-5"></div>
												<div class="col-lg-7">
													<div class="input-group sign_in_input_group">
														<span class="input-group-addon sign_in_input_small">From</span><input type="text" class="form-control" id="datepicker" name="date_from" value="<c:out value="${iscrizione.annoInizio}" />">
													</div>
													<div class="input-group sign_in_input_group">
														<span class="input-group-addon sign_in_input_small">To</span><input type="text" class="form-control" id="datepicker2" name="date_to" value="<c:out value="${iscrizione.annoFine}" />">
													</div>
												</div>
											</div>
											<br>
											<div class="modal-footer">
												<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
													<input type="submit" class="btn btn-primary pull-right" value="Save changes!">
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
						<!--Fine form di modifica a comparsa-->
					</div>
				</div>  			
    	
				<!-- Information Table -->
				<!-- Da generare con JSP -->
				<c:if test='${not empty student}'>
					<table class="table">
						<thead>
							<tr>
								<th></th>
								<th>Field</th>
								<th>Content</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td></td>
								<td>Username</td>
								<td><c:out value="${student.nomeUtente}"/></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>E-mail</td>
								<td><c:out value="${student.email}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>Password</td>
								<td><c:out value="${student.password}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>University</td>
								<td><c:out value="${course.nomeuniversita}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>Degree Course</td>
								<td><c:out value="${course.nome}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>From</td>
								<td><c:out value="${iscrizione.annoInizio}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>To</td>
								<td><c:out value="${iscrizione.annoFine}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>Registration Date</td>
								<td><c:out value="${student.dataRegistrazione}" /></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</c:if>
			</div>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />
</body>
</html>
