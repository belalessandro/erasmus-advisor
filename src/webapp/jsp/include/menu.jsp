<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 	
	notare che alcune voci sono disattivate con alcuni utenti: 
	1) solo responsabili e coordinatori vedono insert_flow, insert_city, insert_uni, insert_course
	2) solo studenti vedono /student/evaluation
-->

<!-- inizio menu -->
<div class="col-md-3">
	<div class="menu_title"><i class="fa fa-bars fa-fw"></i>MENU</div>
	<div class="menu_normal"><a href="<c:url value="/index"/>"><i class="fa fa-home fa-fw"></i>Home</a></div>
	<c:if test="${sessionScope.loggedUser.student}">
		<div class="menu_normal"><a href="<c:url value="/student/evaluations"/>"><i class="fa fa-user fa-fw"></i>My Evaluations</a></div>
	</c:if>
	<br>
	<div class="menu_title"><i class="fa fa-search fa-fw"></i>SEARCH</div>
	<div class="menu_normal"><a href="<c:url value="/flow/list"/>"><i class="fa fa-arrow-right fa-fw"></i>Flows</a></div>
	<div class="menu_normal"><a href="<c:url value="/city/list"/>"><i class="fa fa-arrow-right fa-fw"></i>Cities</a></div>
	<div class="menu_normal"><a href="<c:url value="/university/list"/>"><i class="fa fa-arrow-right fa-fw"></i>Universities</a></div>
	<div class="menu_normal"><a href="<c:url value="/class/list"/>"><i class="fa fa-arrow-right fa-fw"></i>Classes</a></div>
	<div class="menu_normal"><a href="<c:url value="/thesis/list"/>"><i class="fa fa-arrow-right fa-fw"></i>Theses</a></div>
	<br>
	<div class="menu_title"><i class="fa fa-search fa-fw"></i>INSERT</div>
	<c:if test="${!sessionScope.loggedUser.student && !sessionScope.loggedUser.coord}">
		<div class="menu_normal"><a href="<c:url value="/flow/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new Flow</a></div>
	</c:if>
	<c:if test="${!sessionScope.loggedUser.student}">
		<div class="menu_normal"><a href="<c:url value="/city/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new City</a></div>
		<div class="menu_normal"><a href="<c:url value="/university/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new University</a></div>
		<div class="menu_normal"><a href="<c:url value="/course/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new Course</a></div>
	</c:if>
	<div class="menu_normal"><a href="<c:url value="/class/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new Class</a></div>
	<div class="menu_normal"><a href="<c:url value="/thesis/insert"/>"><i class="fa fa-arrow-right fa-fw"></i>Insert a new Thesis</a></div>
</div>
<!-- fine menu -->