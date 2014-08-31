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
	
	<!-- questi servono per il datepicker -->
	<link href="<c:url value="/css"/>/datepicker3.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/ui-lightness/jquery-ui-1.10.4.custom.css" rel="stylesheet">
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/jquery-1.10.2.js"></script>
	<script src="<c:url value="/js"/>/jquery-ui-1.10.4.custom.js"></script>
	<script src="<c:url value="/js"/>/ea-basic.js"></script>	
	<script src="<c:url value="/js"/>/bootstrap-datepicker.js"></script>
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>
	
	<style>
	.ui-autocomplete-loading {
		background: white url("<c:url value="/img"/>/ui-anim_basic_16x16.gif") right center no-repeat;
	}
	.datepicker {
		z-index:1151;
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
										console.log("university is null");
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
		
		// mauro: questi sono necessari altrimenti quei figli di puttana di datepicker non funzionano
		$( "#datepicker" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
		$( "#datepicker" ).datepicker({ gotoCurrent: true });
		$( "#datepicker" ).datepicker( "setDate", new Date().getDate() + "/" + eval(new Date().getMonth() + 1) + "/" + new Date().getFullYear() );
		$( "#datepicker2" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
		$( "#datepicker2" ).datepicker({ gotoCurrent: true });
		$( "#datepicker2" ).datepicker( "setDate", new Date().getDate() + "/" + eval(new Date().getMonth() + 1) + "/" + new Date().getFullYear() );
		
	}); // and ready function
	</script>
	<!-- Questo è per evitare che quei f.d.p. di datepicker non vadano sotto il modal -->
	<style type="text/css">
		.datepicker {
			z-index:1000000 !important;
		}
	</style>
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

			<!-- Avviso di avvenuta modifica dell'entita -->
			<c:if test="${!empty param.edited && param.edited == 'success'}">
				<div class="alert alert-success alert-dismissible" role="alert" >
				  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				  <h4 class="text-center">Account Successfully Edited!</h4>
				</div>
			</c:if>


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

						<!--Edit form-->
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
										<form name='registration' onSubmit="return userProfileFormValidation();" method="post" action="<c:url value="/user/profile"/>">
											<!-- student's input -->
											<c:if test="${student != null}">
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
													<span class="input-group-addon sign_in_input">University</span> <input id="universityNames" class="form-control" name="courseUniversity" value="<c:out value="${course.nomeUniversita}" />">
												</div>
												<br>
												<div class="input-group sign_in_input_group">
													<span class="input-group-addon sign_in_input">Degree Course</span> <input id="corsoNames" class="form-control" name="courseName" value="<c:out value="${course.nome} (${course.livello})" />">
												</div>
												<br>
												<div class="row">
													<div class="col-lg-5"></div>
													<div class="col-lg-7">
															<div class="input-group sign_in_input_group">
																<span class="input-group-addon sign_in_input_small">From</span><input type="text" class="form-control"  id="datepicker" name="date_from"  value="<c:out value="${subscription.annoInizio}"/>">
															</div>
															<div class="input-group sign_in_input_group">
																<span class="input-group-addon sign_in_input_small">To</span><input type="text" class="form-control" id="datepicker2" name="date_to" value="<c:out value="${subscription.annoFine}"/>">
															</div>
													</div>
												</div>
												<br>
												
												<!-- Alerts -->
												<div class="row">
													<div class="col-lg-12">&nbsp;</div>	
												</div>
												<div class="row">
													<div class="col-lg-offset-4 col-lg-7">
														<span id="alert-university" style="display:none" class="label label-danger">Select the university first, please...</span>
													</div>
												</div>
												
												<!-- student hidden params -->
												<input type="hidden" name="nomeUtente" value="<c:out value="${student.nomeUtente}"/>" >
												<input type="hidden" name="dataRegistrazione" value="<c:out value="${student.dataRegistrazione}"/>" >
												<input type="hidden" name="old_courseId" value="<c:out value="${course.id}"/>" >
												<input type="hidden" name="courseLevel" value="<c:out value="${course.livello}"/>" >
											</c:if>
											
											<!-- flow manager input -->
											<c:if test="${flowmanager != null }">
												<div class="input-group sign_in_input_group">
													<span class="input-group-addon sign_in_input">Name*</span> <input type="text" class="form-control" name="email" id="email" value="<c:out value="${flowmanager.nome}"/>">
												</div>
												<br>
												<div class="input-group sign_in_input_group">
													<span class="input-group-addon sign_in_input">Surname*</span> <input type="text" class="form-control" name="email" id="email" value="<c:out value="${flowmanager.cognome}"/>">
												</div>
												<br>
												<div class="input-group sign_in_input_group">
													<span class="input-group-addon sign_in_input">E-mail*</span> <input type="text" class="form-control" name="email" id="email" value="<c:out value="${flowmanager.email}"/>">
												</div>
												<br>
												<div class="input-group sign_in_input_group">
													<span class="input-group-addon sign_in_input">Password*</span> <input type="password" class="form-control" name="password" id="password" value="<c:out value="${flowmanager.password}"/>">
												</div>
												<br>
												<div class="input-group sign_in_input_group">
													<span class="input-group-addon sign_in_input">Confirm Password*</span> <input type="password" class="form-control" name="password2" id="password2" value="<c:out value="${flowmanager.password}" />">
												</div>
												<br>
												<div class="input-group sign_in_input_group">
													<span class="input-group-addon sign_in_input">University</span> <input id="autocomplete" class="form-control" name="nomeUniversita" value="<c:out value="${flowmanager.nomeUniversita}" />">
												</div>
												<br>
												
												<!-- flowmanager hidden params -->
												<input type="hidden" name="attivo" value="<c:out value="${flowmanager.attivo}"/>" >
												<input type="hidden" name="abilitato" value="<c:out value="${flowmanager.abilitato}"/>" >
												<input type="hidden" name="dataRegistrazione" value="<c:out value="${flowmanager.dataRegistrazione}"/>" >
												<input type="hidden" name="idCourse" value="<c:out value="${flowmanager.nomeUtente}"/>" >
											</c:if>
											
											
											<!-- coordinator input -->
											<c:if test="${coordinator != null }">
												<div class="input-group sign_in_input_group">
													<span class="input-group-addon sign_in_input">E-mail*</span> <input type="text" class="form-control" name="email" id="email" value="<c:out value="${coordinator.email}"/>">
												</div>
												<br>
												<div class="input-group sign_in_input_group">
													<span class="input-group-addon sign_in_input">Password*</span> <input type="password" class="form-control" name="password" id="password" value="<c:out value="${coordinator.password}"/>">
												</div>
												<br>
												<div class="input-group sign_in_input_group">
													<span class="input-group-addon sign_in_input">Confirm Password*</span> <input type="password" class="form-control" name="password2" id="password2" value="<c:out value="${coordinator.password}" />">
												</div>
											<br>
												<div class="input-group sign_in_input_group">
													<span class="input-group-addon sign_in_input">University</span> <input id="autocomplete" class="form-control" name="nomeUniversita" value="<c:out value="${coordinator.nomeUniversita}" />">
												</div>
												<br>
												
												<!-- coordinator hidden params -->
												<input type="hidden" name="idCourse" value="<c:out value="${coordinator.nomeUtente}"/>" >
											</c:if>
											
											<br>
											<div class="modal-footer">
												<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
													<input type="submit" class="btn btn-primary pull-right" value="Save changes!">
											</div>
											
											<!-- hidden params -->
											<input type="hidden" name="operation" value="edit"/>
										</form>
									</div>
								</div>
							</div>
						</div>
						<!--Fine form di modifica a comparsa-->
					</div>
				</div>  			
    	
				<!-- Information Table -->
				<!-- Generated when student is logged -->
				<c:if test='${student != null}'>
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
								<td><c:out value="${course.nomeUniversita}" /></td>
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
								<td><c:out value="${subscription.annoInizio}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>To</td>
								<td><c:out value="${subscription.annoFine}" /></td>
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
				<!-- Generated when coordinator is logged -->
				<c:if test='${coordinator != null}'>
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
								<td><c:out value="${coordinator.nomeUtente}"/></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>E-mail</td>
								<td><c:out value="${coordinator.email}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>Password</td>
								<td><c:out value="${coordinator.password}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>University</td>
								<td><c:out value="${coordinator.nomeUniversita}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>Registration Date</td>
								<td><c:out value="${coordinator.dataRegistrazione}" /></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</c:if>
				
				<!-- Generated when flowmanager is logged -->
				<c:if test='${flowmanager != null}'>
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
								<td><c:out value="${flowmanager.nomeUtente}"/></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>E-mail</td>
								<td><c:out value="${flowmanager.email}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>Password</td>
								<td><c:out value="${flowmanager.password}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>University</td>
								<td><c:out value="${flowmanager.nomeUniversita}" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>Registration Date</td>
								<td><c:out value="${flowmanager.dataRegistrazione}" /></td>
								<td></td>
							</tr>
						</tbody>
					</table>
					<br><br>
				</c:if>
			</div>
			
			<c:if test="${flowmanager != null }">
				<c:choose>
					<c:when test="${flowmanager.abilitato && flowmanager.attivo}">
						<div class="alert alert-success" role="alert">You are enabled to modify your flow and course features.</div>	
					</c:when>
					<c:otherwise>
						<div class="alert alert-warning" role="alert">You have been reported to university's coordinator. </div>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />
</body>
</html>
