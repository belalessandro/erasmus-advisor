<!-- luca: non mi è ben chiaro come dovrebbe funzionare il form di questa pagina
	per quanto riguarda il passaggio delle informazioni al server.
	anche cercando in internet non sembra che la gente lo sappia -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>User Profile</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="../css/ea-main.css" rel="stylesheet">
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../fonts/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	
	<script src="../js/ea-form-validation.js"></script>
	
	<!-- Datepicker -->
	<link href="../css/datepicker3.css" rel="stylesheet">
	<link href="../css/ui-lightness/jquery-ui-1.10.4.custom.css" rel="stylesheet">
	<script src="../js/jquery.min.js"></script>
	<script src="../js/jquery-1.10.2.js"></script>
	<script src="../js/jquery-ui-1.10.4.custom.js"></script>
	<script src="../js/ea-basic.js"></script>
	<script src="../js/bootstrap-datepicker.js"></script>
	<script src="../js/bootstrap.min.js"></script>
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
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h3 class="modal-title" id="myModalLabel">Edit your profile information</h3>
										Note that you have to fill all the fields.
									</div>
									<div class="modal-body">
										<!-- action deve puntare alla servlet che gestisce il cambio delle informazioni -->
										<form name='registration' onSubmit="return userProfileFormValidation();" method="post" action="#">
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">Username*</span> <input type="text" class="form-control" name="user" id="user" placeholder="Insert your username">
											</div>
											<br>
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">E-mail*</span> <input type="text" class="form-control" name="email" id="email" placeholder="Example: mario.rossi@example.com">
											</div>
											<br>
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">Password*</span> <input type="password" class="form-control" name="password" id="password" placeholder="Insert a password">
											</div>
											<br>
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">Confirm Password*</span> <input type="password" class="form-control" name="password2" id="password2" placeholder="Confirm your password">
											</div>
											<br>
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">University</span> <input id="autocomplete" class="form-control" name="university" title="Type a letter for suggestions." placeholder="Insert your University">
											</div>
											<br>
											<div class="input-group sign_in_input_group">
												<span class="input-group-addon sign_in_input">Degree Course</span> <input id="autocomplete2" class="form-control" name="degree" title="Type a letter for suggestions." placeholder="Insert your Degree Course">
											</div>
											<br>
											<div class="row">
												<div class="col-lg-5"></div>
												<div class="col-lg-7">
													<div class="input-group sign_in_input_group">
														<span class="input-group-addon sign_in_input_small">From</span><input type="text" class="form-control" id="datepicker" name="date_from" placeholder="dd/mm/yyyy">
													</div>
													<div class="input-group sign_in_input_group">
														<span class="input-group-addon sign_in_input_small">To</span><input type="text" class="form-control" id="datepicker2" name="date_to" placeholder="dd/mm/yyyy">
													</div>
												</div>
											</div>
											<br>
											<input type="submit" class="btn btn-primary pull-right">
										</form>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
										<button type="button" class="btn btn-primary">Save changes</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- Information Table -->
				<!-- Da generare con JSP -->
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
							<td>Pippo</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td>E-mail</td>
							<td>pippo@studenti.unipd.it</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td>Password</td>
							<td>34ab2425234c</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td>University</td>
							<td>Università degli studi di Padova</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td>Degree Course</td>
							<td>Computer Science Engineering</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td>From</td>
							<td>2010</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td>To</td>
							<td>2013</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td>Registration Date</td>
							<td>5/08/2013</td>
							<td></td>
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
