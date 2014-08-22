<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- i risultati della ricerca sono visualizzati in Ajax -->

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Find a Class</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link href="../css/ea-main.css" rel="stylesheet">
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../fonts/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
	
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/jquery.tablesorter.min.js"></script>
	<link href="../css/tablesorter/style.css" rel="stylesheet"> 
	<script>
		// variabili che contengono i valori selezionati nei dropdown
		// usate nella chiamata per effettuare la ricerca vera e propria
		var areaDropValue;
		var universityDropValue;
		var yearDropValue;
		var semesterDropValue;
		var languageDropValue;
		
		// da questa funzione si fa partire la ricerca
		function doSearch()
		{
			if (document.getElementById("sidebar").style.display=="none")
			{ 
				// ricerca avanzata disabilitata
				alert('area ' + areaDropValue + ' university ' + universityDropValue);
			} 
			else
			{
				alert('area ' + areaDropValue + ' university ' + universityDropValue
					+ ' year ' + yearDropValue + ' semester ' + semesterDropValue 
					+ ' language ' + languageDropValue);
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
		$(document).on('click', '.dropdown-menu li a', function () {
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
			else if (elem === 'dropYear')
			{
				$('#dropYear').html(selText + ' <span class="caret"></span>');
				yearDropValue = selText;
			}
			else if (elem === 'dropSemester')
			{
				$('#dropSemester').html(selText + ' <span class="caret"></span>');
				semesterDropValue = selText;
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
			<jsp:param name="pageName" value="search_class"/>
		</jsp:include>
		
		<!-- inizio pagina -->
		<div class="col-md-9 general_main_border">
			<h2 class="text-center">Search a Class</h2>
			<br>
			<!-- ho messo tanto amore nel fare questo form usando componenti di bootstrap standard
			non scartatelo a priori. I nomi delle aree e delle università vanno caricate in modo dinamico. 
			Notare che potrebbe essere meglio inserire un altro dropdown che ad esempio permetta di selezionare 
			lo stato in cui si trova l'università e da lì, tramite Ajax, aggiornare l'altro.-->
			<div class="col-md-4 text-center">
				<div class="btn-group">
					<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropArea">
						Select an Area <span class="caret"></span>
					</button>
					<ul class="dropdown-menu search_scrollable_menu text-left">
						<li><a href="#" target="_blank">Area1</a></li>
						<li><a href="#" target="_blank">Area2</a></li>
						<li><a href="#" target="_blank">Area3</a></li>
						<li><a href="#" target="_blank">Area4</a></li>
						<li><a href="#" target="_blank">Area5</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 text-center" >
				<div class="btn-group">
					<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropUniversity">
						Select a University <span class="caret"></span>
					</button>
					<ul class="dropdown-menu search_scrollable_menu text-left">
						<li><a href="#" target="_blank">University1</a></li>
						<li><a href="#" target="_blank">University2</a></li>
						<li><a href="#" target="_blank">University3</a></li>
						<li><a href="#" target="_blank">University4</a></li>
						<li><a href="#" target="_blank">University5</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 text-center">
				<button class="btn btn-primary" onclick="doSearch()"><span class="fa fa-search fa-fw"></span> Search</button>
			</div>
			<br><br><br>
			<!-- Ricerca Avanzata-->
			<div class="row text-center">
				<div><button class="btn btn-default" onClick="comparsa()">Advanced Search</button></div>
			</div>
			<br>
			<div id="sidebar" style="display:none">
				<div class="col-md-4 text-center" >
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropYear">
							Select a Year <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<li><a href="#" target="_blank">1</a></li>
							<li><a href="#" target="_blank">2</a></li>
							<li><a href="#" target="_blank">3</a></li>
							<li><a href="#" target="_blank">4</a></li>
							<li><a href="#" target="_blank">5</a></li>
							<li><a href="#" target="_blank">6</a></li>
						</ul>
					</div>
				</div>
				<div class="col-md-4 text-center" >
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropSemester">
							Select a Semester <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<li><a href="#" target="_blank">1</a></li>
							<li><a href="#" target="_blank">2</a></li>
						</ul>
					</div>
				</div>
				<div class="col-md-4 text-center" >
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropLanguage">
							Select a Language <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<li><a href="#" target="_blank">Italian</a></li>
							<li><a href="#" target="_blank">Spanish</a></li>
							<li><a href="#" target="_blank">English</a></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- fine Ricerca Avanzata -->
			<br><br><br>
			<!-- frase da da creare dinamicamente -->
			<h5>Results for <strong>Mathematics</strong> in <strong>Università agli Studi di Padova</strong>.</h5>
			<br>
			<table class="table table-bordered table-hover table-striped tablesorter" id="resultTable">
<!-- 		rimossi i filtri, non si possono avere due thead sennò il tablesorter va in casino
			al limite si possono reinserire sotto o in un'altra posizione
			<thead>
					<tr>
						<th>
							<div class="input-group input-group-md">
								<input type="text" class="form-control" placeholder="Filter by Name"> 
								<span class="input-group-btn">
									<button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search"></span></button>
								</span>
							</div>
						</th>
						<th></th><th></th><th></th><th></th>
						<th>
							<div class="input-group input-group-md">
								<input type="text" class="form-control" placeholder="Filter by Professor"> 
								<span class="input-group-btn">
									<button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search"></span></button>
								</span>
							</div>
						</th>
					</tr>
				</thead> -->
				<thead>
					<tr>
						<th>Name</th>
						<th>CFU</th>
						<th>Year</th>
						<th>Semester</th>
						<th>Language</th>
						<th>Professors</th>
					</tr>
				</thead>
				<tbody>
				<!-- risultati da creare dinamicamente -->
					<tr>
						<td><a href="#" target="_blank">Analisi matematica 1</a></td>
						<td>12</td>
						<td>1</td>
						<td>1</td>
						<td>Italian</td>
						<td>Marco Bardi, Matteo Novaga</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">Analisi matematica 2</a></td>
						<td>12</td>
						<td>2</td>
						<td>1</td>
						<td>Italian</td>
						<td>Pietro Maroponda</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">Probabilità Discreta</a></td>
						<td>9</td>
						<td>3</td>
						<td>1</td>
						<td>Italian</td>
						<td>Vincenzo Rossi</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">Algebra Lineare e Geometria</a></td>
						<td>9</td>
						<td>1</td>
						<td>2</td>
						<td>Italian</td>
						<td>Stagnaro is Love</td>
					</tr>
				</tbody>
			</table>
			<!-- da usare solo se si sceglie di non visualizzare tutti i risultati nella pagina
			<ul class="pager">
				<li class="next"><a href="#" target="_blank">Show more &darr;</a></li>
			</ul>
			 -->
		</div>
	</div>	
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />	
</body>
</html>