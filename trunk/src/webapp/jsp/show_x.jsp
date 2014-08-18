<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- questa pagina costituisce un template per tutte le pagine di visualizzazione entità, ossia
	show_flow
	show_city
	show_university
	show_class
	show_theses
	le differenze tra queste pagine sono minime e non nella struttura -->

<!DOCTYPE html>
<html>
<head>
	<title>Name of the diplayed entity</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	
	<!-- CSS -->
	<link href="../css/ea-main.css" rel="stylesheet">
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../fonts/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet">
		
	<!-- Javascript -->
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>	
	<link href="../css/star-rating.css" rel="stylesheet">
	<script src="../js/star-rating.min.js"></script>	
	
	<script>
		// funziona che notifica che questa entità è da segnare REPORTED
		// ossia necessita di moderazione
		// possibile solo per insegnamento e argomento tesi
		// da fare con ajax
	    function report()
	    {
	        alert('report');
	    }
	    function edit()
	    {
	        alert('edit');
	    }
	</script>
</head>
<body>
	<div class="container">
		<!-- barra di navigazione -->
		<jsp:include page="/jsp/include/navbar.jsp"/>
		<!-- menu -->
		<jsp:include page="/jsp/include/menu.jsp">
			<jsp:param name="pageName" value="show"/>
		</jsp:include>

		<!-- corpo della pagina -->
		<!-- di fatto tutta questa pagina è generata con JSP -->
		<div class="col-md-9 general_main_border">
			<div class="entity_details">
				<div class="entity_details_text">
					<h2>Università degli Studi di Padova</h2> 
					<p>City: Padua (Italy)<br> 
					Link: <a href="http://www.unipd.it" target="_blank">www.unipd.it</a><br> 
					World Ranking: 250<br> 
					Has a residence for Erasmus Students<br></p>
				</div>
				<div class="entity_details_text">
					<ul class="nav nav-stacked pull-right">
						<li class="active"><span data-toggle="modal" data-target="#evalutateForm">Evaluate</span></li>
						<li class="active"><span onClick="report();">Report</span></li>
						<li class="active"><span onClick="edit();">Edit</span></li>
					</ul>
				</div>
			</div>
			
			<!--Form di modifica a comparsa-->
			<div class="modal fade" id="evalutateForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Insert an evalutation for <b>Università degli Studi di Padova</b></h4>
						</div>
						<div class="modal-body">
							<!-- action deve puntare alla servlet che gestisce l'inserimento della valutazione -->
							<form name='registration' onSubmit="return xEvaluationFormValidation();" method="post" action="#">
								<div class="col-md-6 text-center">Rating 1:</div>
								<div class="col-md-6 text-center">
									<input id="rating1" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Rating 2:</div>
								<div class="col-md-6 text-center">
									<input id="rating2" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Rating 3:</div>
								<div class="col-md-6 text-center">
									<input id="rating3" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br>
								<div class="col-md-6 text-center">Rating 4:</div>
								<div class="col-md-6 text-center">
									<input id="rating4" class="rating" data-size="sm" data-min="0" data-max="5" data-step="1" data-show-clear="false" data-show-caption="false">
								</div>
								<br>
								<br><br>								
								<div class="input-group insert_new_input_group">
									<span class="input-group-addon insert_new_input">Comment</span> <textarea rows="2" class="form-control" name="comment" id="comment" placeholder="Insert a general comment about the entity."></textarea>
								</div>
								<br>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<input type="submit" value="Save!" class="btn btn-primary pull-right">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- fine Form di valutazione a comparsa-->
			
			<!-- inizio valutazioni -->
			<div class="header entity_top">
				<div class="row">
					<div style="text-align: center">
						<h3>There are <b>3</b> evaluations</h3>
						<div class="col-xs-3 col-sm-3 col-md-3">
							Collocazione urbana<br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span>
						</div>
						<div class="col-xs-3 col-sm-3 col-md-3">
							Qualità aule e laboratori<br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span>
						</div>
						<div class="col-xs-3 col-sm-3 col-md-3">
							Qualità insegnamenti<br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span>
						</div>
						<div class="col-xs-3 col-sm-3 col-md-3">
							Iniziative Erasmus<br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span>
						</div>
					</div>
				</div>
				<br>
			</div>

			<!-- inizio singola valutazione -->
			<section class="entity_box">
				Inserito da <a href="#" target="_blank">CuliNudi</a>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-3">
						Collocazione urbana <br> Qualità aule e laboratori <br> Qualità insegnamenti <br> Iniziative Erasmus <br>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-2">
						<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span
							class="glyphicon glyphicon-star-empty"></span> <br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span
							class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-7">
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptatem, exercitationem, suscipit, distinctio, qui sapiente aspernatur molestiae non corporis magni sit sequi iusto debitis delectus doloremque.</p>

					</div>
				</div>
			</section>
			<!-- fine singola valutazione -->

			<!-- inizio singola valutazione -->
			<section class="entity_box">
				Inserito da <a href="#" target="_blank">Prezzemolina69</a>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-3">
						Collocazione urbana <br> Qualità aule e laboratori <br> Qualità insegnamenti <br> Iniziative Erasmus <br>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-2">
						<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span>
						<br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <span
							class="glyphicon glyphicon-star-empty"></span> <br>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-7">
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptatem, exercitationem, suscipit, distinctio, qui sapiente aspernatur molestiae non corporis magni sit sequi iusto debitis delectus doloremque.</p>

					</div>
				</div>
			</section>
			<!-- fine singola valutazione -->

			<!-- inizio singola valutazione -->
			<section class="entity_box">
				Inserito da <a href="#" target="_blank">Trave666</a>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-3">
						Collocazione urbana <br> Qualità aule e laboratori <br> Qualità insegnamenti <br> Iniziative Erasmus <br>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-2">
						<span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span>
						<br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <br> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star-empty"></span> <span
							class="glyphicon glyphicon-star-empty"></span> <br>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-7"></div>
				</div>
			</section>

		</div>
	</div>
	<!-- footer -->
	<jsp:include page="/jsp/include/footer.jsp" />

</body>
</html>