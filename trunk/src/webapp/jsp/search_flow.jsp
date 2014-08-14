<!-- in quale arcano modo questa pagina caricherà i risultati della ricerca? Ajax?
	a te, baldo implementatore, la scelta! -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Find a flow</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
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
		var countryDropValue;
		var cityDropValue;
		var durationDropValue;
		var seatsDropValue;
		var certificateDropValue;
		
		// da questa funzione si fa partire la ricerca
		function doSearch()
		{
			if (countryDropValue === undefined || countryDropValue === null) 
			{
				// l'utente vuole tutti i flussi
				alert('get all the flows');
			}
			else if (document.getElementById("sidebar").style.display=="none")
			{ 
				// ricerca avanzata disabilitata
				alert('area ' + countryDropValue + ' city ' + cityDropValue);
			} 
			else
			{
				alert('area ' + countryDropValue + ' city ' + cityDropValue
					+ ' duration ' + durationDropValue + ' seats ' + seatsDropValue 
					+ ' certificate ' + certificateDropValue);
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
			if (elem === 'dropCountry')
			{
				$('#dropCountry').html(selText + ' <span class="caret"></span>');
				countryDropValue = selText;
			}
			else if (elem === 'dropCity')
			{
				$('#dropCity').html(selText + ' <span class="caret"></span>');
				cityDropValue = selText;
			}
			else if (elem === 'dropDuration')
			{
				$('#dropDuration').html(selText + ' <span class="caret"></span>');
				durationDropValue = selText;
			}
			else if (elem === 'dropSeats')
			{
				$('#dropSeats').html(selText + ' <span class="caret"></span>');
				seatsDropValue = selText;
			}
			else if (elem === 'dropCertificate')
			{
				$('#dropCertificate').html(selText + ' <span class="caret"></span>');
				certificateDropValue = selText;
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
			<h2 class="text-center">Search a Flow</h2>
			<br>
			The results are automatically filtered to show you only the Erasmus flows that start from your degree course.
			If you do not specify any parameter you will get their full list.
			<br><br>
			<!-- ho messo tanto amore nel fare questo form usando componenti di bootstrap standard
			non scartatelo a priori. I nomi vanno caricati in modo dinamico.-->
			<div class="col-md-4 text-center">
				<div class="btn-group">
					<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropCountry">
						Select a Country <span class="caret"></span>
					</button>
					<ul class="dropdown-menu search_scrollable_menu text-left">
						<li><a href="#">Country1</a></li>
						<li><a href="#">Country2</a></li>
						<li><a href="#">Country3</a></li>
						<li><a href="#">Country4</a></li>
						<li><a href="#">Country5</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 text-center" >
				<div class="btn-group">
					<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropCity">
						Select a City <span class="caret"></span>
					</button>
					<ul class="dropdown-menu search_scrollable_menu text-left">
						<li><a href="#">City1</a></li>
						<li><a href="#">City2</a></li>
						<li><a href="#">City3</a></li>
						<li><a href="#">City4</a></li>
						<li><a href="#">City5</a></li>
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
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropDuration">
							Select a number of months <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<li><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">6</a></li>
						</ul>
					</div>
				</div>
				<div class="col-md-4 text-center" >
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropSeats">
							Select the minimum avaible seats <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<li><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">6</a></li>
						</ul>
					</div>
				</div>
				<div class="col-md-4 text-center" >
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropCertificate">
							Select a Linguistic Certification <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<li><a href="#">Certificate1</a></li>
							<li><a href="#">Certificate2</a></li>
							<li><a href="#">Certificate3</a></li>
							<li><a href="#">Certificate4</a></li>
							<li><a href="#">Certificate5</a></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- fine Ricerca Avanzata -->
			<br><br><br>
			<!-- frase da da creare dinamicamente -->
			<h5>Results for <strong>United Kingdom</strong>.</h5>
			<br>
			<table class="table table-bordered table-hover table-striped tablesorter" id="resultTable">
				<thead>
					<tr>
						<th>ID</th>
						<th>University</th>
						<th>City</th>
						<th>Avaible Seats</th>
						<th>Duration</th>
						<th>Certificates</th>
					</tr>
				</thead>
				<tbody>
				<!-- risultati da creare dinamicamente -->
					<tr>
						<td><a href="#">UK854</a></td>
						<td><a href="#">Imperial College</a></td>
						<td><a href="#">London</a></td>
						<td>1</td>
						<td>6</td>
						<td>English C2</td>
					</tr>
					<tr>
						<td><a href="#">UK5</a></td>
						<td><a href="#">Brighton University</a></td>
						<td><a href="#">Brighton</a></td>
						<td>2</td>
						<td>9</td>
						<td>English B2</td>
					</tr>
					<tr>
						<td><a href="#">UK202</a></td>
						<td><a href="#">University of London</a></td>
						<td><a href="#">London</a></td>
						<td>4</td>
						<td>6</td>
						<td>English C1</td>
					</tr>
					<tr>
						<td><a href="#">UK412</a></td>
						<td><a href="#">Edimburgh College</a></td>
						<td><a href="#">Edimburgh</a></td>
						<td>1</td>
						<td>6</td>
						<td>English C1</td>
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
