<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Insert a new flow</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/fonts"/>/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	
	<link href="<c:url value="/css"/>/bootstrap-select.min.css" rel="stylesheet">
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/bootstrap-select.js"></script>	
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>
		
	<script src="<c:url value="/js"/>/ea-form-validation.js"></script>
	
	<script src="<c:url value="/js"/>/jquery-ui-1.10.4.custom.min.js"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
	<script>
	// inizializza i select avanzati
	$(document).ready(function() {
	    $('.selectpicker').selectpicker({
	        style: 'btn-default',
	        size: false
	    });
	});
	</script>
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
</head>

<body>
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp"/>
		<!-- menu -->
		<jsp:include page="/jsp/include/menu.jsp">
			<jsp:param name="pageName" value="insert_flow"/>
		</jsp:include>

		<!-- Class Header -->
		<div class="col-md-9 general_main_border">
			<c:if test="${param.notify eq 'success'}">
				<!-- display the message -->
    			<c:import url="/jsp/include/showMessage.jsp"/>
    		</c:if>
    		
			<h2 align="center">Insert a New Flow</h2>
			<br>
			<p align="center">An * indicates a required field.</p>
			<br>
			<!-- action deve puntare alla servlet che gestisce la registrazione -->
			<!-- università e corso di laurea con autocomplemento e mostrano solo le opzioni consentite -->
			<!-- opzioni di area e lingua passate via JSP -->
			<form name='insert_flow' method="post" action="<c:url value="/flow"/>" onSubmit="return insertFlowFormValidation();"> 
				<input type="hidden" name="operation" value="insert">
				<div align="center">
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">ID*</span> <input type="text" class="form-control" name="name" id="name" placeholder="Insert the flow's name">
					</div>
					<br>
					<div class="row">
						<span></span>
						<span class="input-group-addon insert_new_select_label_inline">Insert the flow's starting degree courses*</span>
						<select class="selectpicker text-left" multiple id="origin" name="origin[]" data-width="auto">
							<c:forEach var="possibleCourses" items='${possibleCourses}'>
								<option value="${possibleCourses.id}" >
								${possibleCourses.nome} (${possibleCourses.livello})</option>
							</c:forEach>
	    				</select> 
					</div>
					<br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Destination*</span> <input id="universityNames" class="form-control" name="university" title="type &quot;a&quot;" placeholder="Insert the University"/>
					</div>
					<br>
					<div class="row">
						<span></span>
						<span class="input-group-addon insert_new_select_label_inline">Insert the flow's Language Certifications*</span>
						<select class="selectpicker text-left" multiple id="certificate" name="certificate[]" data-width="auto">
							<c:forEach var="certificatesDomain" items='${certificatesDomain}'>
								<option value="${certificatesDomain.nomeLingua} - ${certificatesDomain.livello}" >
								${certificatesDomain.nomeLingua} - ${certificatesDomain.livello}</option>
							</c:forEach>
	    				</select> 						
					</div>
					<br>
					<div class="col-md-6">
						<div class="input-group insert_new_input_group_small">
							<span class="input-group-addon insert_new_input_small">Available positions*</span> <input type="text" class="form-control" name="seats" id="seats" placeholder="">
						</div>
					</div>
					<div class="col-md-6">
						<div class="input-group insert_new_input_group_small">
							<span class="input-group-addon insert_new_input_small">Length (months)*</span> <input type="text" class="form-control" name="length" id="length" placeholder="">
						</div>
					</div>
					<br><br><br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Details</span> <textarea rows="2" class="form-control" name="details" id="details" placeholder="Insert the flow's details"></textarea>
					</div>
					<br><br>
					<input type="submit" class="btn btn-primary" value="Insert into the Database">
				</div>
			</form>
		</div>
	</div>
	<!--/container-->
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />
</body>
</html>
