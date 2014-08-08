<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- funzionamento del menu
luca: 
	la pagina che include il menu passa nel parametro pageName il suo nome
	con l'istruzione jsp <c:if test="${param.pageName == 'index'}">active</c:if>
	si possono inserire tag specifici per evidenziare la pagina in cui ci si trova
	o simili
	
	non l'ho implementata semplicemente perchè non so cosa si vorrebbe fare
-->

<!-- inizio menu -->
<div class="col-md-3">
	<div class="menu_title menu_normal">
		<i class="fa fa-bars fa-fw"></i>MENU
	</div>
	<div class="menu_normal">
		<a href="#"><i class="fa fa-home fa-fw"></i>Home</a>
	</div>
	<div class="menu_normal">
		<a href="#"><i class="fa fa-user fa-fw"></i>My Interests</a>
	</div>
	<div class="menu_normal">
		<a href="#"><i class="fa fa-user fa-fw"></i>My Comments</a>
	</div>
	<div class="menu_normal">
		<a href="#"><i class="fa fa-arrow-right fa-fw"></i>Insert a New Content</a>
	</div>
	<br>
	<div class="menu_title menu_normal">
		<i class="fa fa-search fa-fw"></i>SEARCH
	</div>
	<div class="menu_normal">
		<a href="#"><i class="fa fa-arrow-right fa-fw"></i>Cities</a>
	</div>
	<div class="menu_normal">
		<a href="#"><i class="fa fa-arrow-right fa-fw"></i>Universities</a>
	</div>
	<div class="menu_normal">
		<a href="#"><i class="fa fa-arrow-right fa-fw"></i>Classes</a>
	</div>
	<div class="menu_normal">
		<a href="#"><i class="fa fa-arrow-right fa-fw"></i>Theses</a>
	</div>
	<div class="menu_normal">
		<a href="#"><i class="fa fa-arrow-right fa-fw"></i>Advanced Search</a>
	</div>
</div>
<!-- fine menu -->