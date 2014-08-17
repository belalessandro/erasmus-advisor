<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Insert a new flow</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="../css/ea-main.css" rel="stylesheet">
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../fonts/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	
	<link href="../css/bootstrap-select.min.css" rel="stylesheet">
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap-select.js"></script>	
	<script src="../js/bootstrap.min.js"></script>	
	<script>
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
			<jsp:param name="pageName" value="insert_flow"/>
		</jsp:include>

		<!-- Class Header -->
		<div class="col-md-9 general_main_border">
			<h2 align="center">Insert a New Flow</h2>
			<br>
			<p align="center">An * indicates a required field.</p>
			<br>
			<!-- action deve puntare alla servlet che gestisce la registrazione -->
			<!-- implementare insertFlowFormValidation -->
			<!-- università e corso di laurea con autocomplemento e mostrano solo le opzioni consentite -->
			<!-- opzioni di area e lingua passate via JSP -->
			<form name='insert_class' method="post" action="#" onSubmit="return insertFlowFormValidation();"> 
				<div align="center">
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">ID*</span> <input type="text" class="form-control" name="name" id="name" placeholder="Insert the course's name">
					</div>
					<br>
					<div class="row">
						<span></span>
						<span class="input-group-addon insert_new_select_label_inline">Insert the flow's starting degree courses*</span>
						<select class="selectpicker text-left" multiple id="origin" name="origin[]">
	    					<option>Course1</option>
	    					<option>Course2</option>
	   						<option>Course3</option>
	    					<option>Course4</option>
	   						<option>Course5</option>
	    				</select> 
					</div>
					<br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Destination*</span> <input id="university" class="form-control" name="university" placeholder="Insert the University">
					</div>
					<br>
					<div class="row">
						<span></span>
						<span class="input-group-addon insert_new_select_label_inline">Insert the flow's Language Certifications*</span>
						<select class="selectpicker text-left" multiple id="certificate" name="certificate[]">
	    					<option>Certificate1</option>
	    					<option>Certificate2</option>
	   						<option>Certificate3</option>
	    					<option>Certificate4</option>
	   						<option>Certificate5</option>
	    				</select> 						
					</div>
					<br>
					<div class="col-md-6">
						<div class="input-group insert_new_input_group_small">
							<span class="input-group-addon insert_new_input_small">Available positions*</span> <input type="text" class="form-control" name="seats" id="seats" placeholder="">
						</div>
					</div>
					<div class="col-md-6">
						<div class="input-group insert_new_input_group_small">
							<span class="input-group-addon insert_new_input_small">Lenght (months)*</span> <input type="text" class="form-control" name="length" id="length" placeholder="">
						</div>
					</div>
					<br><br><br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Details</span> <textarea rows="2" class="form-control" name="details" id="details" placeholder="Insert the flow's details"></textarea>
					</div>
					<br><br>
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
