<!-- in quale arcano modo questa pagina caricherà i risultati della ricerca? Ajax?
	a te, baldo implementatore, la scelta! -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Find a Thesis</title>
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
		var areaDropValue;
		var universityDropValue;
		var levelDropValue;
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
					+ ' level ' + levelDropValue + ' language ' + languageDropValue);
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
			else if (elem === 'dropLevel')
			{
				$('#dropLevel').html(selText + ' <span class="caret"></span>');
				levelDropValue = selText;
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
			<h2 class="text-center">Search a Thesis</h2>
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
					<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropUniversity">
						Select a University <span class="caret"></span>
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
			<!-- Ricerca Avanzata-->
			<div class="row text-center">
				<div><button class="btn btn-default" onClick="comparsa()">Advanced Search</button></div>
			</div>
			<br>
			<div id="sidebar" style="display:none">
				<div class="col-md-6 text-center" >
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropLevel">
							Select a Level <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<li><a href="#">Undergraduate</a></li>
							<li><a href="#">Graduate</a></li>
						</ul>
					</div>
				</div>
				<div class="col-md-6 text-center" >
					<div class="btn-group">
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="dropLanguage">
							Select a Language <span class="caret"></span>
						</button>
						<ul class="dropdown-menu search_scrollable_menu text-left">
							<li><a href="#">Italian</a></li>
							<li><a href="#">Spanish</a></li>
							<li><a href="#">English</a></li>
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
						<th></th><th></th><th></th>
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
						<th>Other Areas</th>
						<th>Level</th>
						<th>Languages</th>
						<th>Professors</th>
					</tr>
				</thead>
				<tbody>
				<!-- risultati da creare dinamicamente -->
					<tr>
						<td><a href="#">Counting up to ten.</a></td>
						<td></td>
						<td>Undergraduate</td>
						<td>Italian, English</td>
						<td>Matteo Novaga</td>
					</tr>
					<tr>
						<td><a href="#">Counting up to a hundred.</a></td>
						<td>Philosophy and ethics, Statistics</td>
						<td>Graduate</td>
						<td>Italian</td>
						<td>Matteo Novaga</td>
					</tr>
					<tr>
						<td><a href="#">Algebraic properties of n-dimensional spaces.</a></td>
						<td></td>
						<td>Undergraduate</td>
						<td>Italian, English</td>
						<td>Pietro de Petris, Alvaro Azzolini</td>
					</tr>
					<tr>
						<td><a href="#">The sum of all fears.</a></td>
						<td>Statistics</td>
						<td>Undergraduate</td>
						<td>English</td>
						<td>Alberico degli Uberti</td>
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
