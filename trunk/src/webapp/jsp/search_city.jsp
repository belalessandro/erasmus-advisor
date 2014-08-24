<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- i risultati della ricerca sono visualizzati in Ajax -->

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
			if (languageDropValue === undefined || languageDropValue === null) 
			{
				// lingua non selezionata
				alert('country ' + countryDropValue);
			}
			else
			{
				alert('country ' + countryDropValue + ' language ' + languageDropValue);
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
				languageDropValue = selText;
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
							<li><span>${languageDomain.nome}</span></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="col-md-4 text-center">
				<button class="btn btn-primary" onclick="doSearch()"><span class="fa fa-search fa-fw"></span> Search</button>
			</div>
			<!-- Niente ricerca avanzata qui, non ha molto senso IMHO-->
			<br><br><br>
			<!-- frase da da creare dinamicamente -->
			<h5>Results for <strong>Italy</strong>.</h5>
			<br>
			<!-- si potrebbe al limite visualizzare le università per città in una colonna apposita -->
			<table class="table table-bordered table-hover table-striped tablesorter" id="resultTable">
				<thead>
					<tr>
						<th>Name</th>
						<th>Languages</th>
					</tr>
				</thead>
				<tbody>
				<!-- risultati da creare dinamicamente -->
					<tr>
						<td><a href="#" target="_blank">Padua</a></td>
						<td>Italian, Venetian, Zibbo</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">Neaples</a></td>
						<td>Terron</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">Milan</a></td>
						<td>Italian</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">Florence</a></td>
						<td>Italian</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>	
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />	
</body>
</html>
