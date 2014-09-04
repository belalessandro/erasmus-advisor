<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Find a flow</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/fonts"/>/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>
	<script src="<c:url value="/js"/>/jquery.tablesorter.min.js"></script>
	<link href="<c:url value="/css"/>/tablesorter/style.css" rel="stylesheet"> 
	<script>
		// variabili che contengono i valori selezionati nei dropdown
		// usate nella chiamata per effettuare la ricerca vera e propria
		var countryDropValue;
		var cityDropValue;
		var durationDropValue;
		var seatsDropValue;
		var certificateDropValue;
		
		// da questa funzione si fa partire la ricerca
		function doSearch()
		{
			if (countryDropValue === undefined)
			{
				document.getElementById('country').disabled = true;
			}
			else
			{
				document.getElementById("country").value = countryDropValue;
			}
			if (cityDropValue === undefined)
			{
				document.getElementById('city').disabled = true;
			}
			else
			{
				document.getElementById("city").value = cityDropValue;
			}
			if (durationDropValue === undefined)
			{
				document.getElementById('length').disabled = true;
			}
			else
			{
				document.getElementById("length").value = durationDropValue;
			}
			if (seatsDropValue === undefined)
			{
				document.getElementById('minSeats').disabled = true;
			}
			else
			{
				document.getElementById("minSeats").value = seatsDropValue;
			}
			if (certificateDropValue === undefined)
			{
				document.getElementById('certificate').disabled = true;
			}
			else
			{
				document.getElementById("certificate").value = certificateDropValue;
			}
		}
		// serve per la comparsa delle impostazioni di ricerca avanzata
		function comparsa() 
		{
			if (document.getElementById("sidebar").style.display=="none")
			{ 
				document.getElementById("sidebar").style.display="block";
			} 
			else 
			{
				document.getElementById("sidebar").style.display="none";
			} 
		}
		// aggiorna l'etichetta mostrata dai dropdown e salva il valore selezionato
		$(document).on('click', '.dropdown-menu li span', function () {
			var selText = $(this).text();
			var elem = $(this).parents('.btn-group').children('.dropdown-toggle').attr('id');
			if (elem === 'dropCountry')
			{
				$('#dropCountry').html(selText + ' <span class="caret"></span>');
				countryDropValue = selText;
			}
			else if (elem === 'dropCity')
			{
				$('#dropCity').html(selText + ' <span class="caret"></span>');
				cityDropValue = selText;
			}
			else if (elem === 'dropDuration')
			{
				$('#dropDuration').html(selText + ' <span class="caret"></span>');
				durationDropValue = selText;
			}
			else if (elem === 'dropSeats')
			{
				$('#dropSeats').html(selText + ' <span class="caret"></span>');
				seatsDropValue = selText;
			}
			else if (elem === 'dropCertificate')
			{
				$('#dropCertificate').html(selText + ' <span class="caret"></span>');
				certificateDropValue = selText;
			}
		});
	
		// gestione delle opzioni stato - città
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
	
		$(document).on('click', '#countries li span', function () 
		{
			var country = $(this).text();
			var city = document.getElementById("cities");
			
			// esclude dal delete la prima voce
			if (initialized == false)
			{
				initialized = true;
			}
			else
			{	// elimina le voci inserite in precedenza
				$(city).empty();
				// elimina la selezione per evitare ricerche inconsistenti
				cityDropValue = null;
				$('#dropCity').html('Select a City <span class="caret"></span>');
			}

			// aggiunge le nuove opzioni
			for (var i = 0; i < cityMap[country].length; i++) 
			{
				$("<li><span>" + cityMap[country][i] + "</span></li>").appendTo(city);
			}
		});
		// inizializza tablesorter
		$(document).ready(function() 
    	{ 
        	$("#resultTable").tablesorter();
   		}); 
	</script>
</head>

<body>
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp"/>
		<!-- menu -->
		<jsp:include page="/jsp/include/menu.jsp">
			<jsp:param name="pageName" value="search_class"/>
		</jsp:include>
		
		<!-- inizio pagina -->
		<div class="col-md-9 general_main_border">
			<h2 class="text-center">Search a Flow</h2>
			<br>
			The results are automatically filtered to show you only the Erasmus flows			
			<c:if test="${sessionScope.loggedUser.student}">
				 that start from your degree course.
			</c:if>
			<c:if test="${sessionScope.loggedUser.flowResp}">
				 that you manage.
			</c:if>		
			If you do not specify any parameter you will get their full list.
			<br><br>
			<form  method="get" action="<c:url value="/flow/list"/>" enctype="plain/text">
				<div class="col-md-4 text-center">
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropCountry">
							Select a Country <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left" id="countries">
							<c:forEach var="city" items='${cities}'>
								<li><span>${city.country}</span></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="col-md-4 text-center" >
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropCity">
							Select a City <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left" id="cities">
						</ul>
					</div>
				</div>
				<div class="col-md-4 text-center">
					<button type="submit" class="btn btn-primary" onclick="doSearch()"><span class="fa fa-search fa-fw"></span> Search</button>
				</div>
				<br><br><br>
				<!-- Ricerca Avanzata-->
				<div class="row text-center">
					<div><button class="btn btn-default" type="button" onClick="comparsa()">Advanced Search</button></div>
				</div>
				<br>
				<div id="sidebar" style="display:none">
					<div class="col-md-4 text-center" >
						<div class="btn-group">
							<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropDuration">
								Select a number of months <span class="caret"></span>
							</button>
							<ul class="dropdown-menu search_scrollable_menu text-left">
								<li><span>1</span></li>
								<li><span>2</span></li>
								<li><span>3</span></li>
								<li><span>4</span></li>
								<li><span>5</span></li>
								<li><span>6</span></li>
								<li><span>7</span></li>
								<li><span>8</span></li>
								<li><span>9</span></li>
								<li><span>10</span></li>
								<li><span>11</span></li>
								<li><span>12</span></li>
							</ul>
						</div>
					</div>
					<div class="col-md-4 text-center" >
						<div class="btn-group">
							<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropSeats">
								Select the min available seats <span class="caret"></span>
							</button>
							<ul class="dropdown-menu search_scrollable_menu text-left">
								<li><span>1</span></li>
								<li><span>2</span></li>
								<li><span>3</span></li>
								<li><span>4</span></li>
								<li><span>5</span></li>
								<li><span>6</span></li>
								<li><span>7</span></li>
								<li><span>8</span></li>
								<li><span>9</span></li>
								<li><span>10</span></li>
							</ul>
						</div>
					</div>
					<div class="col-md-4 text-center" >
						<div class="btn-group">
							<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropCertificate">
								Select a Linguistic Certification <span class="caret"></span>
							</button>
							<ul class="dropdown-menu search_scrollable_menu text-left">
							<c:forEach var="cert" items='${certificatesDomain}'>
								<li><span>${cert.nomeLingua} - ${cert.livello}</span></li>
							</c:forEach>
							</ul>
						</div>
					</div>
				</div>
				<input name="operation" type="hidden" value="search" />
				<input name="country" type="hidden" id="country" />
				<input name="city" type="hidden" id="city" />
				<input name="length" type="hidden" id="length" />
				<input name="minSeats" type="hidden" id="minSeats" />
				<input name="certificate" type="hidden" id="certificate" />
			</form>
			<!-- fine Ricerca Avanzata -->
			<br><br><br>

			<c:choose>
				<c:when test="${fn:length(results) == 0}">
					<div class="row text-center">
						<h4>There are no results for your search.</h4>
					</div>
				</c:when>
				<c:otherwise>
					<h4><c:choose>
					<c:when test="${empty allFlows}">
						Results for flows						
						<c:if test="${(not empty searchedCountry && not empty searchedCity)}" >
							 to <strong><c:out value="${searchedCity}"/> 
							(<c:out value="${searchedCountry}"/></strong>)
						</c:if>			
						<c:if test="${(not empty searchedCountry && empty searchedCity)}" >
							 to <strong><c:out value="${searchedCountry}"/></strong>
						</c:if>			
								
						<c:if test="${(not empty searchedLenght)}" >
							 with a length of <strong><c:out value="${searchedLenght}"/></strong> months
						</c:if>		
						
						<c:if test="${(not empty searchedSeats)}" >
							 with <strong><c:out value="${searchedSeats}"/></strong> minumum available seats
						</c:if>		
						
						<c:if test="${(not empty searchedCert)}" >
							 with <strong><c:out value="${searchedCert}"/></strong> as required language certification
						</c:if>	
						.
					</c:when>
					<c:otherwise>
						List of all the flows.
					</c:otherwise>
					
					</c:choose></h4>
					
					
					<br>
					<table class="table table-bordered table-hover table-striped tablesorter" id="resultTable">
						<thead>
							<tr>
								<th>ID</th>
								<th>University</th>
								<th>City</th>
								<th>Avaible Seats</th>
								<th>Duration</th>
								<th>Certificates</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="result" items="${results}">
							<tr>
								<td>
									<a href="<c:url value="/flow"/>?id=${result.flusso.id}" target="_blank">
										<c:out value="${result.flusso.id}"/>
									</a>
								</td>
								<td>
									<a href="<c:url value="/university"/>?name=${fn:replace(result.universita.nome, ' ', '+')}" target="_blank">
										<c:out value="${result.universita.nome}"/>
									</a>
								</td>
								<td>
									<a href="<c:url value="/city"/>?name=${fn:replace(result.universita.nomeCitta, ' ', '+')}&country=${fn:replace(result.universita.statoCitta, ' ', '+')}" target="_blank">
										<c:out value="${result.universita.nomeCitta}"/>
									</a>
								</td>
								<td>${result.flusso.postiDisponibili}</td>
								<td>${result.flusso.durata}</td>
								<td>
									<c:forEach var="certificate" items="${result.listaCertificatiLinguistici}" varStatus="loop">
		    							${certificate.nomeLingua} - ${certificate.livello}<c:if test="${!loop.last}">, </c:if>
									</c:forEach>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
	</div>	
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />	
</body>
</html>