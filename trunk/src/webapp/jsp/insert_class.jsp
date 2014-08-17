<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Insert a new class</title>
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
			<jsp:param name="pageName" value="insert_class"/>
		</jsp:include>

		<!-- Class Header -->
		<div class="col-md-9 general_main_border">
			<h2 align="center">Insert a New Class</h2>
			<br>
			<p align="center">An * indicates a required field.</p>
			<br>
			<!-- action deve puntare alla servlet che gestisce la registrazione -->
			<!-- implementare insertClassFormValidation -->
			<!-- area e università con autocomplemento, università mostra solo le opzioni consentite -->
			<form name='insert_class' method="post" action="#" onSubmit="return insertClassFormValidation();"> 
				<div align="center">
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Name*</span> <input type="text" class="form-control" name="name" id="name" placeholder="Insert the class's name">
					</div>
					<br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Professors*</span> <textarea rows="2" class="form-control" name="professor" id="professor" placeholder="Insert the class' professors, one per line"></textarea>
					</div>
					<br>
					<div class="row">
						<span></span>
						<span class="input-group-addon insert_new_select_label_inline">Select the class' area*</span>
						<select class="selectpicker text-left" id="area" name="area">
	    					<option>Area1</option>
	    					<option>Area2</option>
	   						<option>Area3</option>
	    					<option>Area4</option>
	   						<option>Area5</option>
	    				</select>
					</div>
					<br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">University*</span> <input id="university" class="form-control" name="university" placeholder="Insert the University">
					</div>
					<br>
					<div class="input-group insert_new_input_group">
						<span class="input-group-addon insert_new_input">Language*</span> <input type="text" class="form-control" name="language" id="language" placeholder="Insert the class' language">
					</div>
					<br>
					<div class="col-md-4">
						<div class="input-group insert_new_input_group">
							<span class="input-group-addon insert_new_input_small">Credits*</span> <input type="text" class="form-control" name="credits" id="credits" placeholder="">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group insert_new_input_group">
							<span class="input-group-addon insert_new_input_small">Year*</span> <input type="text" class="form-control" name="year" id="year" placeholder="">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group insert_new_input_group">
							<span class="input-group-addon insert_new_input_small">Semester*</span> <input type="text" class="form-control" name="semester" id="semester" placeholder="">
						</div>
					</div>
					<br>
					<br>
					<br>
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
