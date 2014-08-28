<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- questa è la pagina che appare quando si è completato il login
	la home è solo l'interfaccia verso l'esterno -->

<!DOCTYPE html>
<html>
<head>
	<title>Erasmus Advisor</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<!-- CSS -->
	<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
	<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/fonts"/>/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
		
	<!-- Javascript -->
	<script src="<c:url value="/js"/>/jquery.min.js"></script>
	<script src="<c:url value="/js"/>/bootstrap.min.js"></script>
	
	<script>
	// funzione per l'azione di eliminazione di un interesse
        $(document).ready(function() {                    
            $('.index_button_remove').click(function() {  
    	    	var r = confirm("Do you want to remove this flow from your interests?");
    			if (r == true) 
    			{ 
	                var flow = $(this).attr("id");
	            	$.post('<c:url value="/student/interests"/>', 
	                		{ operation: "delete", flowID : flow},
	                		function(responseText) { 
	                			$('#row-' + flow).remove();
	                		});
    			} 
    			else {
    			    // splash! and nothing happens
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
			<jsp:param name="pageName" value="index"/>
		</jsp:include>
		<!-- corpo della pagina -->
		<div class="col-md-9 general_main_border" >
			<h2 align="center">Erasmus Advisor</h2>
			<br>
			[Qui teoricamente si potrebbe mettere una qualche breve spiegazione del sito.
			Sotto, da specifiche, ci vanno gli interessi espressi dall'utente]
			<br>
			<br>
			<div class="panel panel-default">
				<!-- Default panel contents -->
				<div class="col-sm-1 column"></div>
				<c:choose>
					<c:when test="${fn:length(interests) == 0}">
						<div class="row text-center">
							<h4>You have not express interest for any flow yet.</h4>
						</div>
					</c:when>
					<c:otherwise>	
					<div class="panel-heading text-center">
						<strong>You have express interest for the flows</strong>
					</div>
					<table class="table">
						<thead>
							<tr>
								<th>Flow ID</th>
								<th>Target University</th>
								<th>Target City</th>
								<th class="index_table_col_remove" align="center">Remove</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="interest" items='${interests}' varStatus="status">
								<tr id="row-${interest.flowID}">
									<td><a href="<c:url value="/flow"/>?id=${interest.flowID}" target="_blank">${interest.flowID}</a></td>
									<td><a href="<c:url value="/university"/>?name=${interest.universityName}" target="_blank">${interest.universityName}</a></td>
									<td><a href="<c:url value="/city"/>?name=${interest.cityName}&country=${interest.countryName}" target="_blank">${interest.cityName} (${interest.countryName})</a></td>
									<td align="center">
										<button type="button" class="btn btn-default btn-xs index_button_remove" id="${interest.flowID}">
											<span class="glyphicon glyphicon glyphicon-remove"></span>
										</button>
									</td>
								</tr>
							</c:forEach>		
						</tbody>
					</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>	
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />

</body>
</html>