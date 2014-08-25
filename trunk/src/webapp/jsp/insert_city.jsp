<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Insert a new city</title>
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
	<script>
	// inizializza i select avanzati
	$(document).ready(function() {
	    $('.selectpicker').selectpicker({
	        style: 'btn-default',
	        size: false
	    });
	});
	</script>
<!-- Autocomplete Countries -->
	<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
	<style>
	.ui-autocomplete-loading {
	background: white url("<c:url value="/img"/>/ui-anim_basic_16x16.gif") right center no-repeat;
	}
	</style>
	
	<script>
	$(function() {
		var cache = {};
		$("#countryNames" ).autocomplete({
					minLength : 0,
					source : function(request, response) {
						var term = request.term;
						if (term in cache) {
							response(cache[term]);
							return;
						}
						$.getJSON("<c:url value="/country/list"/>", request,
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
			<jsp:param name="pageName" value="insert_city"/>
		</jsp:include>

		<!-- Class Header -->
		<div class="col-md-9 general_main_border">
			<h2 align="center">Insert a New City</h2>
			<br>
			<p align="center">An * indicates a required field.</p>
			<br>
			<!-- action deve puntare alla servlet che gestisce la registrazione -->
			<!-- lingua mostra solo le opzioni consentite -->
			<form name='insert_city' method="post" action="#" onSubmit="return insertCityFormValidation();"> 
				<div align="center">
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Name*</span> <input type="text" class="form-control" name="name" id="name" placeholder="Insert the city's name">
					</div>
					<br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Country*</span> 
						<input id="countryNames" class="form-control" name="country" title="type &quot;a&quot;" placeholder="Insert the city's country"/>
					</div>
					<br>
					<div class="row">
						<span></span>
						<span class="input-group-addon insert_new_select_label_inline">Select the languages spoken in the city*</span>
						<select class="selectpicker text-left" multiple id="language" name="language[]">
							<c:forEach var="languageDomain" items='${languageDomain}'>
								<option value="${languageDomain.sigla}" >${languageDomain.nome}</option>
							</c:forEach>
	    				</select>
					</div>
					<br>
					<br>
					<br>
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
