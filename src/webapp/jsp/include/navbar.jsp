<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--
	Notifications	>	notifications

	Account			>	user_profile

	Logout			>	servlet che fa il logout, e da lì alla home
-->

<!-- inizio barra di navigazione -->
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<!-- nella pagina di sign in il logo rimanda alla home -->
	<c:choose>
		<c:when test="${param.pageName == 'sign_in'}"><a href="/erasmus-advisor/"></c:when>
		<c:otherwise><a href="index.jsp"></c:otherwise>
	</c:choose>
	<img src="<c:url value="/img"/>/logo.svg" class="nav_logo"></a>
	<ul class="nav nav-pills pull-right nav_style">
		<!-- nella pagina di sign in non si vedono le voci di menù -->
		<c:if test="${param.pageName != 'sign_in'}">
			<li><a href="<c:url value="/notifications"/>">Notifications</a></li>
			<li><a href="<c:url value="/student/profile"/>">Account</a></li>
			<li><a href="<c:url value="/logout"/>"><span class="fa fa-sign-out fa-fw"></span>Logout</a></li>
		</c:if>
	</ul>
</div>
<!-- fine barra di navigazione -->