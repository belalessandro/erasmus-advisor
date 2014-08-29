<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Find a City</title>
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
		var languageDropValue;
		
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
			if (languageDropValue === undefined)
			{
				document.getElementById('language').disabled = true;
			}
			else
			{
				document.getElementById("language").value = languageDropValue;
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
			else if (elem === 'dropLanguage')
			{
				$('#dropLanguage').html(selText + ' <span class="caret"></span>');
				languageDropValue = $(this).attr('id');
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
			<jsp:param name="pageName" value="search_city"/>
		</jsp:include>
		
		<!-- inizio pagina -->
		<div class="col-md-9 general_main_border">
			<h2 class="text-center">Search a City</h2>
			<br>
			
			<form  method="get" action="<c:url value="/city/list"/>" enctype="plain/text">
				<div class="col-md-4 text-center">
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropCountry">
							Select a Country <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<c:forEach var="country" items='${countries}'>
								<li><span>${country}</span></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="col-md-4 text-center" >
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropLanguage">
							Select a Language <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<c:forEach var="languageDomain" items='${languageDomain}'>
								<li><span id="${languageDomain.sigla}">${languageDomain.nome}</span></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="col-md-4 text-center">
					<button class="btn btn-primary" onclick="doSearch()"><span class="fa fa-search fa-fw"></span> Search</button>
				</div>
				<input name="operation" type="hidden" value="search" />
				<input name="language" type="hidden" id="language" />
				<input name="country" type="hidden" id="country" />
			</form>
			<br><br><br>
			<c:choose>
				<c:when test="${fn:length(results) == 0}">
					<div class="row text-center">
						<h4>There are no results for your search.</h4>
					</div>
				</c:when>
				<c:otherwise>
					<h4>
						<c:if test="${(not empty searchedCountry && not empty searchedLanguage)}" >
							Results for cities in <strong><c:out value="${searchedCountry}"/></strong> in which 
								<c:forEach var="result" items="${results}" end="0">
									<c:forEach var="linguaBean" items="${result.listaLingue}">
		    							<c:if test="${linguaBean.sigla == searchedLanguage}"><strong>${linguaBean.nome}</strong></c:if>
									</c:forEach>
								</c:forEach>
							is spoken.
						</c:if>
						<c:if test="${(empty searchedCountry && not empty searchedLanguage)}" >
							Results for cities in which 
								<c:forEach var="result" items="${results}" end="0">
									<c:forEach var="linguaBean" items="${result.listaLingue}">
		    							<c:if test="${linguaBean.sigla == searchedLanguage}"><strong>${linguaBean.nome}</strong></c:if>
									</c:forEach>
								</c:forEach>
							is spoken.
						</c:if>
						
						<c:if test="${(not empty searchedCountry && empty searchedLanguage)}" >
							Results for cities in <strong><c:out value="${searchedCountry}"/></strong>.
						</c:if>
						
					</h4>
					<br>
					<table class="table table-bordered table-hover table-striped tablesorter" id="resultTable">
						<thead>
							<tr>
								<th>Name</th>
								<th>Languages</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="result" items="${results}">
							<tr>
								<td>
									<a href="<c:url value="/city"/>?name=${fn:replace(result.citta.nome, ' ', '+')}&country=${fn:replace(result.citta.stato, ' ', '+')}" target="_blank">
										<c:out value="${result.citta.nome}"/>
									</a>
								</td>
								<td>
									<c:forEach var="linguaBean" items="${result.listaLingue}" varStatus="loop">
		    							${linguaBean.nome}<c:if test="${!loop.last}">, </c:if>
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