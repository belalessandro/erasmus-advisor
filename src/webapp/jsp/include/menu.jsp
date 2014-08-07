<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- funzionamento del menu
	la pagina che include il menu passa nel parametro thisPage il suo nome
	trabattando un po' con <c:out value="${param.thisPage}"/> 
	dovrebbe essere possibile evidenziare la pagina nella quale ci si trova o fare altre cose 
-->

<!-- inizio menu -->
<div class="col-md-3">
    <div class="menu_title menu_normal"><i class="fa fa-bars fa-fw"></i>MENU</div>
    <div class="menu_normal"><a href="#"><i class="fa fa-home fa-fw"></i>Home</a></div>
 	<div class="menu_normal"><a href="#"><i class="fa fa-user fa-fw"></i>My Interests</a></div>
 	<div class="menu_normal"><a href="#"><i class="fa fa-user fa-fw"></i>My Comments</a></div>
    <div class="menu_normal"><a href="#"><i class="fa fa-arrow-right fa-fw"></i>Insert a New Content</a></div>
	<br>
	<div class="menu_title menu_normal"><i class="fa fa-search fa-fw"></i>SEARCH</div>
	<div class="menu_normal"><a href="#"><i class="fa fa-arrow-right fa-fw"></i>Cities</a></div>
 	<div class="menu_normal"><a href="#"><i class="fa fa-arrow-right fa-fw"></i>Universities</a></div>
  	<div class="menu_normal"><a href="#"><i class="fa fa-arrow-right fa-fw"></i>Classes</a></div>
	<div class="menu_normal"><a href="#"><i class="fa fa-arrow-right fa-fw"></i>Theses</a></div>
  	<div class="menu_normal"><a href="#"><i class="fa fa-arrow-right fa-fw"></i>Advanced Search</a></div>
</div>
<!-- fine menu -->