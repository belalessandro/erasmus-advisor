<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Registration Page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/fonts"/>/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>
	
	<script src="<c:url value="/js"/>/js/ea-form-validation.js"></script>
	
	<!-- questi servono per il datepicker 
		mi sembra impossibile ci voglia tutta sta roba per forza, ma se si toglie qualcosa non va -.-
		notare come l'autocompletamento in ea-basic sia stato eliminato -->
	<link href=".<c:url value="/css"/>/datepicker3.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/ui-lightness/jquery-ui-1.10.4.custom.css" rel="stylesheet">
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/jquery-1.10.2.js"></script>
	<script src="<c:url value="/js"/>/jquery-ui-1.10.4.custom.js"></script>
	<script src="<c:url value="/js"/>/bootstrap-datepicker.js"></script>
	<script src="<c:url value="/js"/>/ea-basic.js"></script>	
</head>

<body>
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp">
			<jsp:param name="pageName" value="sign_in"/>
		</jsp:include>

		<!-- Class Header -->
		<div class ="col-md-2"></div>
		
		<div class="col-md-8">
			
			<h2 align="center">Registration Page</h2>
			<br>
			<p align="center">An * indicates a required field.</p>
			<br>
			<!-- action deve puntare alla servlet che gestisce la registrazione -->
			<form name='registration' onSubmit="return signInFormValidation();" action="<c:url value="/create-student"/>" method="post" action="#"> 
				<div align="center">
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
						<span class="input-group-addon sign_in_input">University</span> <input id="university" class="form-control" name="university" title="Type a letter for suggestions." placeholder="Insert your University">
					</div>
					<br>
					<div class="input-group sign_in_input_group">
						<span class="input-group-addon sign_in_input">Degree Course</span> <input id="degree" class="form-control" name="degree" title="Type a letter for suggestions." placeholder="Insert your Degree Course">
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
					<div class="row">
						<hr>
						<div class="alert alert-warning">
							Agree to the registration's terms* :
							<input id="regTermRadioYes" name="regTermRadio" type="radio" class="radio-btn" value="Yes" /> Yes 
							<input id="regTermRadioNo" name="regTermRadio" type="radio" class="radio-btn" value="No" /> No
						</div>
						
							<!-- display the message -->
    						<c:import url="/jsp/include/showMessage.jsp"/>
    	
						<hr>
					</div>
					<input type="submit" class="btn btn-primary" value="Register Now!">
				</div>
			</form>
		</div>
		<div class ="col-md-2"></div>
	</div>
	<!--/container-->
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />
</body>
</html>
