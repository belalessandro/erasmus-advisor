<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- i risultati della ricerca sono visualizzati in Ajax -->
	
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Find a University</title>
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
		//Assegno i valori ai campi OUT
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
		});
		// inizializza tablesorter
		// disabilita l'ordinamento sulla prima colonna
		$(document).ready(function() { 
		    $("#resultTable").tablesorter({ 
		        headers: { 
		            1: { sorter: false }
		        } 
		    }); 
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
	</script>
</head>

<body>
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp"/>
		<!-- menu -->
		<jsp:include page="/jsp/include/menu.jsp">
			<jsp:param name="pageName" value="search_university"/>
		</jsp:include>
		
		<!-- inizio pagina -->
		<div class="col-md-9 general_main_border">
			<h2 class="text-center">Search a University</h2>
			<br>
			If you do not specify any parameter you will get all the universities in the Erasmus Advisor database.
			<br><br>
			<form  method="get" action="<c:url value="/university/list"/>" enctype="plain/text">
				<input name="operation" type="hidden" value="search" />
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
					<input name="country" type="hidden" id="country"/>
				</div>
				<div class="col-md-4 text-center" >
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropCity">
							Select a City <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left" id="cities">
						</ul>
					</div>
					<input name="city" type="hidden" id="city"/>
				</div>
				<div class="col-md-4 text-center">
					<button class="btn btn-primary" type="submit" onClick="doSearch()"><span class="fa fa-search fa-fw"></span> Search</button>
				</div>
			</form>
			<!-- Niente ricerca avanzata qui, non ha molto senso IMHO-->
			<br><br><br>
			<c:choose>
				<c:when test="${fn:length(results) == 0}">
					<div class="row text-center">
						<h4>There are no results for your search.</h4>
					</div>
				</c:when>
					<c:otherwise>
						<h4>
						<c:choose>
							<c:when test="${empty allUniversities}">
								Results for universities
								<c:if test="${(not empty searchedCountry)}" >
									in  "<strong><c:out value="${searchedCountry}"/></strong>" 
								</c:if>
								<c:if test="${(not empty searchedCity)}" >
									at <strong><c:out value="${searchedCity}"/></strong> 
								</c:if>
								.
							</c:when>
							<c:otherwise>
								List of all the universities.
							</c:otherwise>
						</c:choose>
						</h4>
						<table class="table table-bordered table-hover table-striped tablesorter" id="resultTable">
							<thead>
								<tr>
									<th>Name</th>
									<th>Link</th>
									<th>Rank</th>
									<th>Campus</th>
									<th>City</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="university" items="${results}">
									<tr>
										<td>
											<a href="<c:url value="/university"/>?name=${fn:replace(university.nome, ' ', '+')}" target="_blank">
													<c:out value="${university.nome}"/>
											</a>
										</td>
										<td>
											<a href="<c:out value="${university.link}"/>" target="_blank">
													<c:out value="${university.link}"/>
											</a>
										</td>
										<td><c:out value="${university.posizioneClassifica}"/></td>
										<td><c:if test='${university.presenzaAlloggi}'>Yes</c:if><c:if test='${not university.presenzaAlloggi}'>No</c:if></td>
										<td>
											<a href="<c:url value="/city"/>?name=${fn:replace(university.nomeCitta, ' ', '+')}&country=${fn:replace(university.statoCitta, ' ', '+')}" target="_blank">
													<c:out value="${university.nomeCitta}"/>
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			<!-- da usare solo se si sceglie di non visualizzare tutti i risultati nella pagina
			<ul class="pager">
				<li class="next"><a href="#" target="_blank">Show more &darr;</a></li>
			</ul>
			 -->
		</div>
	</div>	
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />	
</body>
</html>
