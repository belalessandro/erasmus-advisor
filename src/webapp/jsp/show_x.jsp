<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
	<script src="../js/bootstrap.min.js"></script>
	<script>
	    function evaluate()
	    {
	        alert('evaluate');
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
					Link: <a href="www.unipd.it">www.unipd.it</a><br> 
					World Ranking: 250<br> 
					Has a residence for Erasmus Students<br></p>
				</div>
				<div class="entity_details_text">
					<ul class="nav nav-stacked pull-right">
						<li class="active"><a href="#" onclick="evaluate();">Evaluate</a></li>
						<li class="active"><a href="#" onclick="report();">Report</a></li>
						<li class="active"><a href="#" onclick="edit();">Edit</a></li>
					</ul>
				</div>
			</div>

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
				Inserito da <a href="#">CuliNudi</a>
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
				Inserito da <a href="#">Prezzemolina69</a>
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
				Inserito da <a href="#">Trave666</a>
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