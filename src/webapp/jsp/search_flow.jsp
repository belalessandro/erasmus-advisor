<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- i risultati della ricerca sono visualizzati in Ajax -->

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
			if (countryDropValue === undefined || countryDropValue === null) 
			{
				// l'utente vuole tutti i flussi
				alert('get all the flows');
			}
			else if (document.getElementById("sidebar").style.display=="none")
			{ 
				// ricerca avanzata disabilitata
				alert('country ' + countryDropValue + ' city ' + cityDropValue);
			} 
			else
			{
				alert('country ' + countryDropValue + ' city ' + cityDropValue
					+ ' duration ' + durationDropValue + ' seats ' + seatsDropValue 
					+ ' certificate ' + certificateDropValue);
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
			The results are automatically filtered to show you only the Erasmus flows that start from your degree course.
			If you do not specify any parameter you will get their full list.
			<br><br>
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
				<button class="btn btn-primary" onclick="doSearch()"><span class="fa fa-search fa-fw"></span> Search</button>
			</div>
			<br><br><br>
			<!-- Ricerca Avanzata-->
			<div class="row text-center">
				<div><button class="btn btn-default" onClick="comparsa()">Advanced Search</button></div>
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
							Select the min available positions <span class="caret"></span>
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
			<!-- fine Ricerca Avanzata -->
			<br><br><br>
			<!-- frase da da creare dinamicamente -->
			<h5>Results for <strong>United Kingdom</strong>.</h5>
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
				<!-- risultati da creare dinamicamente -->
					<tr>
						<td><a href="#" target="_blank">UK854</a></td>
						<td><a href="#" target="_blank">Imperial College</a></td>
						<td><a href="#" target="_blank">London</a></td>
						<td>1</td>
						<td>6</td>
						<td>English C2</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">UK5</a></td>
						<td><a href="#" target="_blank">Brighton University</a></td>
						<td><a href="#" target="_blank">Brighton</a></td>
						<td>2</td>
						<td>9</td>
						<td>English B2</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">UK202</a></td>
						<td><a href="#" target="_blank">University of London</a></td>
						<td><a href="#" target="_blank">London</a></td>
						<td>4</td>
						<td>6</td>
						<td>English C1</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">UK412</a></td>
						<td><a href="#" target="_blank">Edimburgh College</a></td>
						<td><a href="#" target="_blank">Edimburgh</a></td>
						<td>1</td>
						<td>6</td>
						<td>English C1</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>	
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />	
</body>
</html>
