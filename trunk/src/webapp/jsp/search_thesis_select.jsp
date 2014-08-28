<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	
	<link href="<c:url value="/css"/>/bootstrap-select.min.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/tablesorter/style.css" rel="stylesheet"> 
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/bootstrap-select.js"></script>	
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>	
	<script src="<c:url value="/js"/>/jquery.tablesorter.min.js"></script>
	<script>
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

		// inizializza tablesorter
		$(document).ready(function() 
    	{ 
        	$("#resultTable").tablesorter();
   		}); 
		// inizializza i select avanzati
		$(document).ready(function() {
		    $('.selectpicker').selectpicker({
		        style: 'btn-default',
		        size: false
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
			<jsp:param name="pageName" value="search_class"/>
		</jsp:include>
		
		<!-- inizio pagina -->
		<div class="col-md-9 general_main_border">
			<h2 class="text-center">Search a Thesis</h2>
			<br>
			<!-- Notare che potrebbe essere meglio inserire un altro dropdown che ad esempio permetta di selezionare 
			lo stato in cui si trova l'università e da lì aggiornare l'altro.-->
			<form method="get" action="<c:url value="/thesis/list"/>" enctype="plain/text" class="text-center">
				<input name="operation" type="hidden" value="search" />
				<div class="row">
					<span></span>
					<span class="input-group-addon insert_new_select_label_inline">Select an Area</span>
					<select class="selectpicker text-left" id="area" name="area" data-width="auto">
	    				<option disabled selected>Nothing Selected</option>
						<c:forEach var="areaDomain" items='${areaDomain}'>
							<option value="${areaDomain.nome}" >${areaDomain.nome}</option>
						</c:forEach>
    				</select>
				</div>
				<div class="row">
					<span></span>
					<span class="input-group-addon insert_new_select_label_inline">Select a University</span>
					<select class="selectpicker text-left" id="university" name="university" data-width="auto">
	    				<option disabled selected>Nothing Selected</option>
						<c:forEach var="uni" items='${universities}'>
							<option value="${uni.nome}" >${uni.nome}</option>
						</c:forEach>
    				</select>
				</div>
				<div class="row">
					<button class="btn btn-primary" type="submit"><span class="fa fa-search fa-fw"></span> Search</button>
				</div>
				<br><br><br>
				<!-- Ricerca Avanzata-->
				<div class="row text-center">
					<div><button class="btn btn-default" type="button" onClick="comparsa()">Advanced Search</button></div>
				</div>
				<br>
				<div id="sidebar" style="display:none">
					<div class="row">
						<span></span>
						<span class="input-group-addon insert_new_select_label_inline">Select a Level</span>
						<select class="selectpicker text-left" id="level" name="level">
	    					<option disabled selected>Nothing Selected</option>
							<option value="Undergraduate" >Undergraduate</option>
							<option value="Graduate" >Graduate</option>
	    				</select>
					</div>
					<div class="row">
						<span></span>
						<span class="input-group-addon insert_new_select_label_inline">Select a Language</span>
						<select class="selectpicker text-left" id="language" name="language">
	    					<option disabled selected>Nothing Selected</option>
							<c:forEach var="languageDomain" items='${languageDomain}'>
								<option value="${languageDomain.nome}" >${languageDomain.nome}</option>
							</c:forEach>
	    				</select>
					</div>
				</div>
			</form>
			<!-- fine Ricerca Avanzata -->
			<br><br><br>
			<!-- frase da da creare dinamicamente -->
			<c:set var="found" scope="session" value="false"/>
			<c:if test='${not empty theses}'> 
			<h5>Results for <strong>${areaSearch}</strong> in <strong>${universitySearch}</strong>.</h5>
			<br>
			<table class="table table-bordered table-hover table-striped tablesorter" id="resultTable">
				<thead>
					<tr>
						<th>Name</th>
						<th>Other Areas</th>
						<th>Level</th>
						<th>Languages</th>
						<th>Professors</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="thesis" items="${theses}">
					<tr>
						<td><c:out value="${thesis.nomeTesi}"/></td>
						<td><c:out value="${thesis.aree}"/></td>
						<td><c:out value="${thesis.livello}"/></td>
						<td><c:out value="${thesis.lingue}"/></td>
						<td><c:out value="${thesis.professori}"/></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			</c:if>
			<!-- FRASE DA CREARE DIN. -->
			<c:if test="true">
				<h4><strong>NOT FOUND: </strong> there are no results for thesis in <strong>${areaSearch}</strong> area, at <strong>${universitySearch}</strong>.</h4>
			</c:if>
		</div>
	</div>	
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />	
</body>
</html>
