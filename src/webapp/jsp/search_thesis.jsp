<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Find a Thesis</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/fonts"/>/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/jquery.tablesorter.min.js"></script>
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>
	<link href="<c:url value="/css"/>/tablesorter/style.css" rel="stylesheet"> 
	<script>
		// variabili che contengono i valori selezionati nei dropdown
		// usate nella chiamata per effettuare la ricerca vera e propria
		var areaDropValue;
		var universityDropValue;
		var levelDropValue;
		var languageDropValue;
		
		// da questa funzione si fa partire la ricerca
		function doSearch()
		{	
 			if (areaDropValue === undefined)
			{
				document.getElementById('area').disabled = true;
			}
			else
			{
				document.getElementById("area").value = areaDropValue;
			}
			if (universityDropValue === undefined)
			{
				document.getElementById('university').disabled = true;
			}
			else
			{
				document.getElementById("university").value = universityDropValue;
			}
			
			if (levelDropValue === undefined)
			{
				document.getElementById('level').disabled = true;
			}
			else
			{
				document.getElementById("level").value = levelDropValue;
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
			if (elem === 'dropArea')
			{
				$('#dropArea').html(selText + ' <span class="caret"></span>');
				areaDropValue = selText;
			}
			else if (elem === 'dropUniversity')
			{
				$('#dropUniversity').html(selText + ' <span class="caret"></span>');
				universityDropValue = selText;
			}
			else if (elem === 'dropLevel')
			{
				$('#dropLevel').html(selText + ' <span class="caret"></span>');
				levelDropValue = selText;
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
        	$("th.idThesis").hide();
         	$("td.idThesis").hide();     	
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
			<h2 class="text-center">Search a Thesis</h2>
			<br>
			If you do not specify any parameter you will get all the theses in the Erasmus Advisor database.
			<br><br>
			<form  method="get" action="<c:url value="/thesis/list"/>" enctype="plain/text">
				<input name="operation" type="hidden" value="search" />
				<div class="col-md-4 text-center">
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropArea" name="dropArea">
							Select an Area <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<c:forEach var="areaDomain" items='${areaDomain}'>
								<li><span>${areaDomain.nome}</span></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<input name="area" type="hidden" id="area"/>
				<div class="col-md-4 text-center" >
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropUniversity" name="idUniversity">
							Select a University <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<c:forEach var="uni" items='${universities}'>
								<li><span>${uni.nome}</span></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<input name="university" type="hidden" id="university"/>
				<div class="col-md-4 text-center">
					<button class="btn btn-primary" type="submit" onClick="doSearch()"><span class="fa fa-search fa-fw"></span> Search</button>
				</div>
				<br><br><br>
				<!-- Ricerca Avanzata-->
				<div class="row text-center">
					<div><button class="btn btn-default" type="button" onClick="comparsa()">Advanced Search</button></div>
				</div>
				<br>
				<div id="sidebar" style="display:none">
					<div class="col-md-6 text-center" >
						<div class="btn-group">
							<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropLevel" name="dropLevel" value="ciao">
								Select a Level <span class="caret"></span>
							</button>
							<ul class="dropdown-menu search_scrollable_menu text-left">
								<li><span>Undergraduate</span></li>
								<li><span>Graduate</span></li>
							</ul>
						</div>
					</div>
					<input name="level" type="hidden" id="level"/>
					<div class="col-md-6 text-center" >
						<div class="btn-group">
							<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropLanguage" name="dropLanguage">
								Select a Language <span class="caret"></span>
							</button>
							<ul class="dropdown-menu search_scrollable_menu text-left">
								<c:forEach var="languageDomain" items='${languageDomain}'>
									<li><span>${languageDomain.nome}</span></li>
								</c:forEach>
							</ul>
						</div>
						<input name="language" type="hidden" id="language"/>
					</div>
					
				</div>
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
					<h4>
					<c:choose>
						<c:when test="${empty allThesis}">
							Results for theses 
							<c:if test="${(not empty searchedArea)}" >
								in the "<strong><c:out value="${searchedArea}"/></strong>" area 
							</c:if>
							<c:if test="${(not empty searchedUniversity)}" >
								at <strong><c:out value="${searchedUniversity}"/></strong> 
							</c:if>
							<c:if test="${(not empty searchedLevel)}" >
								for <strong><c:out value="${searchedLevel}"/></strong> students
							</c:if>
							<c:if test="${(not empty searchedLanguage)}" >
								in <strong><c:out value="${searchedLanguage}"/></strong> language
							</c:if>
							.
						</c:when>
						<c:otherwise>
							List of all the theses.
						</c:otherwise>
					</c:choose>
					</h4>
					<table class="table table-bordered table-hover table-striped tablesorter" id="resultTable">
						<thead>
							<tr>
						<th class="idThesis">ID</th>
								<th>Name</th>
								<th>University</th>
								<th>Other Areas</th>
								<th>Level</th>
								<th>Languages</th>
								<th>Professors</th>
								<th>Status</th>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach var="thesis" items="${results}">
								<tr>
									<td class="idThesis"><c:out value="${thesis.arg.id}"/></td>
									<td>
									<a href="<c:url value="/thesis"/>?id=${thesis.arg.id}" target="_blank">
										<c:out value="${thesis.arg.nome}"/>
									</a>
									</td>
									<td>
									<a href="<c:url value="/university"/>?name=${fn:replace(thesis.arg.nomeUniversita, ' ', '+')}" target="_blank">
										<c:out value="${thesis.arg.nomeUniversita}"/>
									</a>
									</td>
									<td><c:forEach var="area" items="${thesis.listaAree}" varStatus="status"><c:out value="${area.nome}"/><c:if test="${!status.last}"><br></c:if></c:forEach></td>
									<td><c:if test='${thesis.arg.triennale}'>UNDERGRADUATE<br></c:if><c:if test='${thesis.arg.magistrale}'>GRADUATE<br></c:if></td>
									<td><c:forEach var="language" items="${thesis.listaLingue}" varStatus="status"><c:out value="${language.nome}"/><c:if test="${!status.last}"><br></c:if></c:forEach></td>
									<td><c:forEach var="teacher" items="${thesis.listaProfessori}" varStatus="status"><c:out value="${teacher.nome}"/> <c:out value="${teacher.cognome}"/> <c:if test="${!status.last}"><br></c:if></c:forEach></td>
									<td><c:out value="${thesis.arg.stato}"/></td>
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
