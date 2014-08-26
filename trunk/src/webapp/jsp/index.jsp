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
	<link href="../css/ea-main.css" rel="stylesheet">
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../fonts/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
		
	<!-- Javascript -->
	<script src="../js/bootstrap.min.js"></script>
	
	<script>
		function removeInterest(id)
		{
			alert(id);
		}
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
			<!-- Notare che cliccando sul tasto remove si deve togliere l'interesse dal DB
				ossia bisogna usare Ajax -->
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
					<div class="panel-heading">
						<strong>You have express interest for the following flows.</strong>
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
							<tr>
								<td><a href="<c:url value="/flow"/>?id=${interests.flowID}" target="_blank">${interests.flowID}</a></td>
								<td><a href="<c:url value="/university"/>?name=${interests.universityName}" target="_blank">${interests.universityName}</a></td>
								<td><a href="<c:url value="/city"/>?name=${interests.cityName}&country=${interests.countryName}" target="_blank">${interests.cityName} (${interests.countryName})</a></td>
								<td align="center">
									<button type="button" class="btn btn-default btn-xs" onclick="removeInterest(this.id);"
									id="${interests.flowID}&${interests.userName}">
										<span class="glyphicon glyphicon glyphicon-remove"></span>
									</button>
								</td>
							</tr>
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