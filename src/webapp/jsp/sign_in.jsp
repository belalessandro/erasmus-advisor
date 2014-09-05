<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Registration Page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/fonts"/>/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	
	<script src="<c:url value="/js"/>/ea-form-validation.js"></script>
	
	<!-- questi servono per il datepicker -->
	<link href="<c:url value="/css"/>/datepicker3.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/ui-lightness/jquery-ui-1.10.4.custom.css" rel="stylesheet">
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/jquery-1.10.2.js"></script>
	<script src="<c:url value="/js"/>/jquery-ui-1.10.4.custom.js"></script>
	<script src="<c:url value="/js"/>/ea-datepicker.js"></script>	
	<script src="<c:url value="/js"/>/bootstrap-datepicker.js"></script>
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>
	
	<!-- Autocomplete Universities -->
	<!-- causano conflitto con lo stile, non vanno inclusi, il tutto funziona lo stesso
	<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
	 -->
	<style>
	.ui-autocomplete-loading {
		background: white url("<c:url value="/img"/>/ui-anim_basic_16x16.gif") right center no-repeat;
	}
	</style>
	<script>
	$(function() {
		var cache = {};
		$("#universityNames" ).autocomplete({
					minLength : 2,
					source : function(request, response) {
						var term = request.term;
						if (term in cache) {
							response(cache[term]);
							return;
						}
						$.getJSON("<c:url value="/university/list"/>", request,
								function(data, status, xhr) {
									xhr.setRequestHeader("X-Requested-With",
											"XMLHttpRequest");
									cache[term] = data;
									response(data);
								});
					}
				});
	});
	</script>
	<script>
	/*
	* [NEW VERSION]
	*/
	$(function() {
		var cache = {};
		
		$("#universityNames").change(function() {
			cache = {};
		});
		
		
		$("#corsoNames" ).autocomplete({
					minLength : 2,
					source : function(request, response) {
						var term = request.term;

						request.university = $("#universityNames").val();
						if (term in cache) {
							response(cache[term]);
							return;
						}
						$.getJSON("<c:url value="/course/list"/>", request,
								function(data, status, xhr) {
									xhr.setRequestHeader("X-Requested-With",
											"XMLHttpRequest");
									
									if(data["error"] == "university")
									{
										$("#universityNames").css("border","1px solid red");
										$("#alert-university").show();
									}
									else
									{	$("#universityNames").css("border","1px solid #ccc");
										$("#alert-university").hide();
										cache[term] = data;
										response(data);
									}
								});
					}
				}); // end autocomplete function
	}); // and ready function
	</script>
	
	<script>
	// gestisce i controlli visualizzati in base al tipo di account desiderato
		function userTypeSelection()
		{
			
			document.registration.setAttribute("style", "display: inline;");
			
			if (document.getElementById("typeStudent").checked)
			{
				document.getElementById("form_degree_course").setAttribute("style", "display: table;");
				document.getElementById("form_dates").setAttribute("style", "display: inline;");
				
				document.getElementById("form_name").setAttribute("style", "display: none;");
				document.getElementById("form_surname").setAttribute("style", "display: none;");
				
				// modify hidden params
				document.getElementById("userTypeSelected").setAttribute("name", "userType");
				document.getElementById("userTypeSelected").setAttribute("value", "Student");
			}
			else if (document.getElementById("typeManager").checked)
			{
				document.getElementById("form_name").setAttribute("style", "display: table;");
				document.getElementById("form_surname").setAttribute("style", "display: table;");
	
				document.getElementById("form_degree_course").setAttribute("style", "display: none;");
				document.getElementById("form_dates").setAttribute("style", "display: none;");
				
				// modify hidden params
				document.getElementById("userTypeSelected").setAttribute("name", "userType");
				document.getElementById("userTypeSelected").setAttribute("value", "Manager");
			}
		}
	</script>
	
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
			<!-- display the message -->
    		<c:import url="/jsp/include/showMessage.jsp"/>
			<div class="row text-center">
				<hr>
				<div class="col-md-4">
					Select an account type:
				</div>
				<div class="col-md-4">
					<input id="typeStudent" name="userType" type="radio" class="radio-btn" value="Student" onchange="userTypeSelection();"/> Student
				</div>
				<div class="col-md-4"> 
					<input id="typeManager" name="userType" type="radio" class="radio-btn" value="Manager" onchange="userTypeSelection();"/> Flow Manager
				</div>
				<hr>
			</div>
			
			<div class="row">
				<div class="col-lg-12">&nbsp;</div>
			</div>
			<!-- Alert when the user has inserted a wrong course or university -->
			<div class="row">
				<c:if test="${param.err != null && (param.err == 'university' || param.err == 'course')}">
					<div class="alert alert-danger alert-dismissible" role="alert">
					  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					  <strong>Warning!</strong> The <c:out value="${param.err}"/> you have inserted doesn't exists.
					</div>
				</c:if>
			</div>
			
			<!-- action deve puntare alla servlet che gestisce la registrazione -->
			<form name='registration' onSubmit="return signInFormValidation();" action="<c:url value="/signin"/>" method="post" style="display: none;"> 
				<div align="center">
					<br>
					<p>An * indicates a required field.</p>
					<br>
					<div class="input-group sign_in_input_group">
						<span class="input-group-addon sign_in_input">Username*</span> <input type="text" class="form-control" name="user" id="user" placeholder="Insert your username">
					</div>
					<br>
					<div class="input-group sign_in_input_group">
						<span class="input-group-addon sign_in_input">E-mail*</span> <input type="text" class="form-control" name="email" id="email" placeholder="Example: mario.rossi@example.com">
					</div>
					<br>
					<div id="form_name" class="sign_in_input_group">
						<div class="input-group sign_in_input_group">
							<span class="input-group-addon sign_in_input">Name*</span> <input type="text" class="form-control" name="nome" id="name" placeholder="Insert your name">	
						</div>
						<br>
					</div>
					<div id="form_surname" class="sign_in_input_group">
						<div class="input-group sign_in_input_group">
							<span class="input-group-addon sign_in_input">Surname*</span> <input type="text" class="form-control" name="cognome" id="surname" placeholder="Insert your surname">			
						</div>
						<br>
					</div>
					<div class="input-group sign_in_input_group">
						<span class="input-group-addon sign_in_input">Password*</span> <input type="password" class="form-control" name="password" id="password" placeholder="Insert a password">
					</div>
					<br>
					<div class="input-group sign_in_input_group">
						<span class="input-group-addon sign_in_input">Confirm Password*</span> <input type="password" class="form-control" name="password2" id="password2" placeholder="Confirm your password">
					</div>
					<br>
					<div class="input-group sign_in_input_group">
						<span class="input-group-addon sign_in_input">University</span><input id="universityNames" class="form-control" name="nomeUniversita" title="type &quot;a&quot;" placeholder="Insert the University"/>
					</div>
					<br>
					<div id="form_degree_course" class="sign_in_input_group">
						<div class="input-group sign_in_input_group">
							<span class="input-group-addon sign_in_input">Degree Course</span> <input id="corsoNames" class="form-control" name="courseName" title="type &quot;a&quot;" placeholder="Insert your Degree Course"/>
						</div>
						<br>
					</div>
					<div class="row" id="form_dates">
						<div class="col-lg-5"></div>
						<div class="col-lg-7">
							<div class="input-group sign_in_input_group">
								<span class="input-group-addon sign_in_input_small">From</span><input type="text" class="form-control" id="datepicker" name="date_from" data-date-format="yyyy-mm-dd">
							</div>
							<div class="input-group sign_in_input_group">
								<span class="input-group-addon sign_in_input_small">To</span><input type="text" class="form-control" id="datepicker2" name="date_to" data-date-format="yyyy-mm-dd">
							</div>
						</div>
					</div>
					<!-- Alerts -->
					<div class="row">
						<div class="col-lg-12">&nbsp;</div>	
					</div>
					<div class="row">
						<div class="col-lg-offset-4 col-lg-7">
							<span id="alert-university" style="display:none" class="label label-danger">Select the university first, please...</span>
						</div>
					</div>
					<div class="row">
						<hr>
						<div class="alert alert-warning">
							Agree to the registration's terms* :
							<input id="regTermRadioYes" name="regTermRadio" type="radio" class="radio-btn" value="Yes" /> Yes 
							<input id="regTermRadioNo" name="regTermRadio" type="radio" class="radio-btn" value="No" /> No
						</div>
						<hr>
					</div>
					<input type="submit" class="btn btn-primary" value="Register Now!">
				</div>
				<!-- Hidden params -->
				<input type="hidden" id="userTypeSelected" />
			</form>
		</div>
		<div class ="col-md-2"></div>
	</div>
	<!--/container-->
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />
</body>
</html>
