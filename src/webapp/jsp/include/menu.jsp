<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- funzionamento del menu
	la pagina che include il menu passa nel parametro pageName il suo nome
	con l'istruzione jsp <c:if test="${param.pageName == 'index'}">active</c:if>
	si possono inserire tag specifici per evidenziare la pagina in cui ci si trova
	
	non l'ho implementata semplicemente perchè non so cosa si vorrebbe fare
-->

<!-- Mappa dei link del menù
	Home			->		index 
							sono visualizzati gli interessi, volendo pure una guida/introduzione
	
	My Interests			pagina rimossa, è ridondante con la Home
	
	My Comments		-/->	manca la pagina
							elenco di tutti i commenti inseriti dall'utente?
	
	Insert a new...	->		insert_x
							manca un menù per decidere a quale pagina reindirizzare
	
	Flows			->		search_flow
	
	Universities	->		search_university
	
	Cities			->		search_city
	
	Classes			->		search_class
	
	Theses			->		search_theses
	
	Advanced Search			pagina rimossa
-->

<!-- inizio menu -->
<div class="col-md-3">
	<div class="menu_title menu_normal"><i class="fa fa-bars fa-fw"></i>MENU</div>
	<div class="menu_normal"><a href="index.jsp"><i class="fa fa-home fa-fw"></i>Home</a></div>
	<div class="menu_normal"><a href="#"><i class="fa fa-user fa-fw"></i>My Comments</a></div>
	<div class="menu_normal"><a href="insert_flow.jsp"><i class="fa fa-arrow-right fa-fw"></i>Insert a New Content</a></div>
	<br>
	<div class="menu_title menu_normal"><i class="fa fa-search fa-fw"></i>SEARCH</div>
	<div class="menu_normal"><a href="search_flow.jsp"><i class="fa fa-arrow-right fa-fw"></i>Flows</a></div>
	<div class="menu_normal"><a href="search_city.jsp"><i class="fa fa-arrow-right fa-fw"></i>Cities</a></div>
	<div class="menu_normal"><a href="search_university.jsp"><i class="fa fa-arrow-right fa-fw"></i>Universities</a></div>
	<div class="menu_normal"><a href="search_class.jsp"><i class="fa fa-arrow-right fa-fw"></i>Classes</a></div>
	<div class="menu_normal"><a href="search_thesis.jsp"><i class="fa fa-arrow-right fa-fw"></i>Theses</a></div>
</div>
<!-- fine menu -->