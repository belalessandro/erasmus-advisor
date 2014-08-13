<!-- come mai funzionerà questa pagina? JSP? Ajax?
	a te, baldo implementatore, la scelta! -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Find a Class</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="../css/ea-main.css" rel="stylesheet">
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../fonts/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script>
		var areaDropValue;
		var universityDropValue;

		// da questa funzione si fa partire la ricerca
		function doSearch()
		{
			alert('area ' + areaDropValue + ' university ' + universityDropValue);
		}
		// aggiorna l'etichetta mostrata dai dropdown
		$(document).on('click', '.dropdown-menu li a', function () {
			var selText = $(this).text();
			var elem = $(this).parents('.btn-group').children('.dropdown-toggle').attr('id');
			if (elem === 'dropMenu')
			{
				$('#dropMenu').html(selText + ' <span class="caret"></span>');
				areaDropValue = selText;
			}
			else if (elem === 'dropMenu2')
			{
				$('#dropMenu2').html(selText + ' <span class="caret"></span>');
				universityDropValue = selText;
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
			<jsp:param name="pageName" value="search_class"/>
		</jsp:include>
		
		<!-- inizio pagina -->
		<div class="col-md-9 general_main_border">
			<h2 class="text-center">Search a Class</h2>
			<br>
			<!-- luca: ho messo tanto amore nel fare questo form usando componenti di bootstrap standard
			non scartatelo -->
			<div class="col-md-4 text-center">
				<div class="btn-group">
					<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropMenu">
						Select an Area <span class="caret"></span>
					</button>
					<ul class="dropdown-menu search_scrollable_menu text-left">
						<li><a href="#">Area1</a></li>
						<li><a href="#">Area2</a></li>
						<li><a href="#">Area3</a></li>
						<li><a href="#">Area4</a></li>
						<li><a href="#">Area5</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 text-center" >
				<div class="btn-group">
					<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropMenu2">
						Select an University <span class="caret"></span>
					</button>
					<ul class="dropdown-menu search_scrollable_menu text-left">
						<li><a href="#">University1</a></li>
						<li><a href="#">University2</a></li>
						<li><a href="#">University3</a></li>
						<li><a href="#">University4</a></li>
						<li><a href="#">University5</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 text-center">
				<button class="btn btn-primary" onclick="doSearch()"><span class="fa fa-search fa-fw"></span> Search</button>
			</div>
			<br><br><br>
			<!-- frase da da creare dinamicamente -->
			<h5>Results for <strong>Mathematics</strong> in <strong>Università agli Studi di Padova</strong>.</h5>
			<br>
			<table class="table table-bordered table-hover table-striped">
				<thead>
					<tr>
						<th>
							<div class="input-group input-group-md">
								<input type="text" class="form-control" placeholder="Filter by Name"> <span class="input-group-btn">
									<button class="btn btn-default" type="button">
										<span class="glyphicon glyphicon-search"></span>
									</button>
								</span>
							</div>
						</th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th>
							<div class="input-group input-group-md">
								<input type="text" class="form-control" placeholder="Filter by Professor"> <span class="input-group-btn">
									<button class="btn btn-default" type="button">
										<span class="glyphicon glyphicon-search"></span>
									</button>
								</span>
							</div>
						</th>
					</tr>
				</thead>
				<thead>
					<tr class="warning">
						<th>Name</th>
						<th>CFU</th>
						<th>Year</th>
						<th>Semester</th>
						<th>Language</th>
						<th>Professors</th>
					</tr>
				</thead>
				<tbody>
				<!-- risultati da creare dinamicamente con JSP -->
					<tr>
						<td><a href="#" class="linkExam">Analisi matematica 1</a></td>
						<td>12</td>
						<td>1</td>
						<td>1</td>
						<td>IT</td>
						<td>Marco Bardi, Matteo Novaga</td>
					</tr>
					<tr>
						<td><a href="#" class="linkExam">Analisi matematica 2</a></td>
						<td>12</td>
						<td>2</td>
						<td>1</td>
						<td>IT</td>
						<td>Pietro Maroponda, Andrea Angeletti</td>
					</tr>
					<tr>
						<td><a href="#" class="linkExam">Algebra Lineare e Geometria</a></td>
						<td>9</td>
						<td>1</td>
						<td>2</td>
						<td>IT</td>
						<td>Stagnaro</td>
					</tr>
				</tbody>
			</table>
			<!-- da usare solo se si sceglie di non visualizzare tutti i risultati nella pagina
			<ul class="pager">
				<li class="next"><a href="#">Show more &darr;</a></li>
			</ul>
			 -->
		</div>
	</div>	
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />	
</body>
</html>
