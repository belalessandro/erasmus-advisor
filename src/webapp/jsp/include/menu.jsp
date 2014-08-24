<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- funzionamento del menu
	la pagina che include il menu passa nel parametro pageName il suo nome
	con l'istruzione jsp <c:if test="${param.pageName == 'index'}">active</c:if>
	si possono inserire tag specifici per evidenziare la pagina in cui ci si trova
	
	non l'ho implementata semplicemente perchè non so cosa si vorrebbe fare
	
	notare che alcune voci sono disattivate con alcuni utenti: 
	1) solo responsabili e coordinatori vedono insert_flow, insert_city, insert_uni, insert_course
	2) solo studenti vedono user_comments
-->

<!-- Mappa dei link del menù
	Home			->		index (sono visualizzati gli interessi, volendo pure una guida/introduzione)
	My Interests			pagina rimossa, è ridondante con la Home
	My Comments		-/->	manca la pagina (elenco di tutti i commenti inseriti dall'utente?)
	Insert a new...			pagina rimossa (servirebbe un menù per decidere a quale pagina reindirizzare)
	
	Flows			->		search_flow
	Universities	->		search_university
	Cities			->		search_city
	Classes			->		search_class
	Theses			->		search_theses
	Advanced Search			pagina rimossa
	
	Flows			->		insert_flow
	Cities			->		insert_city
	Universities	->		insert_university
	Cities			->		insert_course
	Classes			->		insert_class
	Theses			->		insert_theses	
-->

<!-- inizio menu -->
<div class="col-md-3">
	<div class="menu_title"><i class="fa fa-bars fa-fw"></i>MENU</div>
	<div class="menu_normal"><a href="index.jsp"><i class="fa fa-home fa-fw"></i>Home</a></div>
	<div class="menu_normal"><a href="#"><i class="fa fa-user fa-fw"></i>My Comments</a></div>
	<br>
	<div class="menu_title"><i class="fa fa-search fa-fw"></i>SEARCH</div>
	<div class="menu_normal"><a href="<c:url value="/flow/list"/>"><i class="fa fa-arrow-right fa-fw"></i>Flows</a></div>
	<div class="menu_normal"><a href="<c:url value="/city/list"/>"><i class="fa fa-arrow-right fa-fw"></i>Cities</a></div>
	<div class="menu_normal"><a href="<c:url value="/university/list"/>"><i class="fa fa-arrow-right fa-fw"></i>Universities</a></div>
	<div class="menu_normal"><a href="<c:url value="/class/list"/>"><i class="fa fa-arrow-right fa-fw"></i>Classes</a></div>
	<div class="menu_normal"><a href="<c:url value="/thesis/list"/>"><i class="fa fa-arrow-right fa-fw"></i>Theses</a></div>
	<br>
	<div class="menu_title"><i class="fa fa-search fa-fw"></i>INSERT</div>
	<div class="menu_normal"><a href="<c:url value="/flow/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new Flow</a></div>
	<div class="menu_normal"><a href="<c:url value="/city/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new City</a></div>
	<div class="menu_normal"><a href="<c:url value="/university/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new University</a></div>
	<div class="menu_normal"><a href="<c:url value="/course/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new Course</a></div>
	<div class="menu_normal"><a href="<c:url value="/class/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new Class</a></div>
	<div class="menu_normal"><a href="<c:url value="/thesis/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new Thesis</a></div>
</div>
<!-- fine menu -->