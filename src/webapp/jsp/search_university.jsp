<!-- in quale arcano modo questa pagina caricherà i risultati della ricerca? Ajax?
	a te, baldo implementatore, la scelta! -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Find a University</title>
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
		
		// da questa funzione si fa partire la ricerca
		function doSearch()
		{
			if (cityDropValue === undefined || cityDropValue === null) 
			{
				// città non selezionata
				alert('country ' + countryDropValue);
			}
			else
			{
				alert('country ' + countryDropValue + ' city ' + cityDropValue);
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
		});
		// inizializza tablesorter
		// disabilita l'ordinamento sulla prima colonna
		$(document).ready(function() { 
		    $("#resultTable").tablesorter({ 
		        headers: { 
		            1: { sorter: false }
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
			<jsp:param name="pageName" value="search_university"/>
		</jsp:include>
		
		<!-- inizio pagina -->
		<div class="col-md-9 general_main_border">
			<h2 class="text-center">Search a University</h2>
			<br>
			<!-- ho messo tanto amore nel fare questo form usando componenti di bootstrap standard
			non scartatelo a priori. I nomi delle aree e delle università vanno caricate in modo dinamico. 
			Notare che potrebbe essere meglio inserire un altro dropdown che ad esempio permetta di selezionare 
			lo stato in cui si trova l'università e da lì, tramite Ajax, aggiornare l'altro.-->
			<div class="col-md-4 text-center">
				<div class="btn-group">
					<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropCountry">
						Select a Country <span class="caret"></span>
					</button>
					<ul class="dropdown-menu search_scrollable_menu text-left">
						<li><a href="#" target="_blank">Country1</a></li>
						<li><a href="#" target="_blank">Country2</a></li>
						<li><a href="#" target="_blank">Country2</a></li>
						<li><a href="#" target="_blank">Country4</a></li>
						<li><a href="#" target="_blank">Country5</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 text-center" >
				<div class="btn-group">
					<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropCity">
						Select a City <span class="caret"></span>
					</button>
					<ul class="dropdown-menu search_scrollable_menu text-left">
						<li><a href="#" target="_blank">City1</a></li>
						<li><a href="#" target="_blank">City2</a></li>
						<li><a href="#" target="_blank">City3</a></li>
						<li><a href="#" target="_blank">City4</a></li>
						<li><a href="#" target="_blank">City5</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 text-center">
				<button class="btn btn-primary" onclick="doSearch()"><span class="fa fa-search fa-fw"></span> Search</button>
			</div>
			<!-- Niente ricerca avanzata qui, non ha molto senso IMHO-->
			<br><br><br>
			<!-- frase da da creare dinamicamente -->
			<h5>Results for <strong>Italy</strong>.</h5>
			<br>
			<table class="table table-bordered table-hover table-striped tablesorter" id="resultTable">
				<thead>
					<tr>
						<th>Name</th>
						<th>Link</th>
						<th>Rank</th>
						<th>Campus</th>
						<th>City</th>
					</tr>
				</thead>
				<tbody>
				<!-- risultati da creare dinamicamente -->
					<tr>
						<td><a href="#" target="_blank">Università degli Studi di Padova</a></td>
						<td><a href="http://www.unipd.it">http://www.unipd.it</a></td>
						<td>180</td>
						<td>No</td>
						<td>Padua</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">Università degli Studi di Bologna</a></td>
						<td><a href="http://www.unibo.it">http://www.unibo.it</a></td>
						<td>160</td>
						<td>Yes</td>
						<td>Bologna</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">Università degli Studi di Napoli Federico II</a></td>
						<td><a href="http://www.unina.it">http://www.unina.it</a></td>
						<td>NV</td>
						<td>No</td>
						<td>Neaples</td>
					</tr>
					<tr>
						<td><a href="#" target="_blank">La Sapienza - Università di Roma</a></td>
						<td><a href="http://www.uniroma1.it">http://www.uniroma1.it</a></td>
						<td>380</td>
						<td>Yes</td>
						<td>Rome</td>
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
