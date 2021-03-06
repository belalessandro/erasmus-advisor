<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert a new university</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="../css/ea-main.css" rel="stylesheet">
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../fonts/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">

	<link href="../css/bootstrap-select.min.css" rel="stylesheet">
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap-select.js"></script>	
	<script src="../js/bootstrap.min.js"></script>	
	
	<script src="../js/ea-form-validation.js"></script>
	<script>
		// inizializza i select avanzati
		$(document).ready(function() {
		    $('.selectpicker').selectpicker({
		        style: 'btn-default',
		        size: false
		    });
		});
	
		// gestione dei select stato - città
		// alla selezione dello stato compaiono le sue città
		var cityMap = new Object();
		var initialized = false;
		<c:forEach var="state" items='${cities}'>
		cityMap["${state.country}"] = [
			<c:forEach var="city" items='${state.cities}' varStatus="status">
				"${city}"
				<c:if test="${!status.last}">, </c:if>
			</c:forEach>
		];
		</c:forEach>
	
		function updateCity() {
			var country = document.getElementById("country").value;
			var city = document.getElementById("city");
			
			// esclude dal delete la prima voce
			if (initialized == false)
			{
				initialized = true;
			}
			else
			{	// elimina le voci inserite in precedenza
				for(var i = 0; i <= city.length; i++) 
		    	{  
		    	    city.remove(1);
		    	}  
			}

			// aggiunge le nuove opzioni
			for (var i = 0; i < cityMap[country].length; i++) 
			{
				var opt = document.createElement("option");
				opt.appendChild(document.createTextNode(cityMap[country][i]));
				opt.value = cityMap[country][i];
				city.appendChild(opt);
			}
			// aggiorna il controllo
			$('.selectpicker').selectpicker('refresh');
		}
	</script>
</head>

<body>
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp"/>
		<!-- menu -->
		<jsp:include page="/jsp/include/menu.jsp">
			<jsp:param name="pageName" value="insert_university"/>
		</jsp:include>

		<!-- Class Header -->
		<div class="col-md-9 general_main_border">
			<h2 align="center">Insert a New University</h2>
			<br>
			<p align="center">An * indicates a required field.</p>
			<br>
			<!-- action deve puntare alla servlet che gestisce la registrazione -->
			<!-- lingua mostra solo le opzioni consentite -->
			<form name='insert_university' method="post" action="<c:url value="/university"/>" accept-charset="UTF-8" onSubmit="return insertUniversityFormValidation();">
				<input type="hidden" name="operation" value="insert">
				<div align="center">
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Name*</span> <input type="text" class="form-control" name="nome" id="name" placeholder="Insert the university's name">
					</div>
					<br>					
					<div class="row">
						<span></span>
						<span class="input-group-addon insert_new_select_label_inline">Select the university's country*</span>
						<select class="selectpicker text-left" id="country" name="statoCitta" onchange="updateCity();" data-width="auto">
	    					<option disabled selected>Nothing Selected</option> <!-- serve per la corretta validazione -->
	    						<c:forEach var="state" items='${cities}'>
									<option value="${state.country}" >${state.country}</option>
								</c:forEach>
	    				</select>
					</div>
					<br>
					<div class="row">
						<span></span>
						<span class="input-group-addon insert_new_select_label_inline">Select the university's city*</span>
						<select class="selectpicker text-left" id="city" name="nomeCitta" data-width="auto">
	    					<option disabled selected>Nothing Selected</option> <!-- serve per la corretta validazione -->
	    				</select>
					</div>
					<br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Link*</span> <input id="link" class="form-control" name="link" placeholder="Insert a link to the university's web site">
					</div>
					<br>
					<div class="col-md-6">
						<div class="input-group insert_new_input_group_small">
							<span class="input-group-addon insert_new_input_small">
								<a href="http://www.topuniversities.com/university-rankings/world-university-rankings/2013#sorting=rank+region=140+country=+faculty=+stars=false+search=" target=_blank">Position in university ranking*</a>
							</span> 
							<input type="text" class="form-control" name="posizioneClassifica" id="ranking" placeholder="">
						</div>
					</div>
					<div class="col-md-6">
							<input type="checkbox" id="hasResidence" name="presenzaAlloggi" value="hasResidence"> Select if the university has a residence for Erasmus students.
					</div>
					<br><br><br><br>
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
