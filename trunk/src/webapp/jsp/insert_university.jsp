<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Insert a new university</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<script src="../js/ea-form-validation.js"></script>
	
	<link href="../css/ea-main.css" rel="stylesheet">
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../fonts/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<script src="../js/bootstrap.min.js"></script>	
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
			<form name='insert_university' method="post" action="<c:url value="/university"/>" onSubmit="return insertUniversityFormValidation();">
				<input type="hidden" name="operation" value="insert">
				<div align="center">
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Name*</span> <input type="text" class="form-control" name="nome" id="name" placeholder="Insert the university's name">
					</div>
					<br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Country*</span> <input id="country" class="form-control" name="statoCitta" placeholder="Insert the university's country">
					</div>
					<br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">City*</span> <input id="city" class="form-control" name="nomeCitta" placeholder="Insert the university's city">
					</div>
					<br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Link*</span> <input id="link" class="form-control" name="link" placeholder="Insert a link to the university's web site">
					</div>
					<br>
					<div class="col-md-6">
						<div class="input-group insert_new_input_group_small">
							<span class="input-group-addon insert_new_input_small">Position in university ranking*</span> <input type="text" class="form-control" name="posizioneClassifica" id="ranking" placeholder="">
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