<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- luca:
	notare che la pagina iniziale si trova in un limbo che non sono riuscito a capire
	per lei i percorsi dei file sono diversi che per tutte le altre pagine -->

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

		<title>Erasmus Advisor</title>

		<link href="<c:url value="/css"/>/ea-main.css" rel="stylesheet">
		<link href="<c:url value="/css"/>/bootstrap.min.css" rel="stylesheet">
		<link href="<c:url value="/css"/>/bootstrap-theme.min.css" rel="stylesheet">
		
		<script src="<c:url value="/js"/>/ea-form-validation.js"></script>
		
		<script>
			// il codice dello script è tutto per lo sfondo rotante
			var bgimages=new Array()
			
			bgimages[0]="<c:url value="/img"/>/home_back_1.jpg"
			bgimages[1]="<c:url value="/img"/>/home_back_2.jpg"
			bgimages[2]="<c:url value="/img"/>/home_back_3.jpg"
			
			//preload images
			var pathToImg=new Array()
			for (i=0;i<bgimages.length;i++)
			{
			  pathToImg[i]=new Image()
			  pathToImg[i].src=bgimages[i]
			}
			
			var inc=-1
			
			function homeBackgroundImageSlide()
			{
			  if (inc<bgimages.length-1)
			    inc++
			  else
			    inc=0
			  document.body.background=pathToImg[inc].src
			}
			
			if (document.all||document.getElementById)
			  	window.onload=new Function('setInterval("homeBackgroundImageSlide()", 4000)')
		</script>

	</head>

	<!-- qui si setta l'immagine di sfondo di default (visualizzata all'inizio o se non va lo script) -->
	<body class="home_body" background="<c:url value="/img"/>/home_back_3.jpg">
		
		<!-- menù superiore -->
		<div class="navbar navbar-inverse home_nav_style" role="navigation">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle"data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
			</div>
			<div class="navbar-collapse collapse">
				<form name="login_form" class="navbar-form navbar-right" method="post" action="<c:url value="/login"/>" onSubmit="return loginValidation();">
					<c:if test="${not empty message}">
						<div class="form-group">
							<span class="badge home_message">
								<c:out value="${message.message}"/>
							</span>
						</div>
					</c:if>
					<div class="form-group"><input type="text" placeholder="Email" name="email" id="email" class="form-control"></div>
					<div class="form-group"><input type="password" placeholder="Password" name="pass" id="pass" class="form-control"></div>
					<button type="submit" class="btn btn-primary">Login</button>
				</form>
			</div>
		</div>
		
		<!-- contenuto principale -->
		
		<div class="home_text_box">
			<h1>Erasmus Advisor</h1>
			<h4>Plan your Erasmus experience and share your evaluations.</h4>
			<br>
			<a href="<c:url value="/signin"/>" class="btn btn-primary">Sign up</a>
			<br>
			<br>
		</div>
	</body>
</html>
