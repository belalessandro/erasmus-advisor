<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--
	Notifications	>	notifications_coordinator o notifications_flow_manager
						(notare come nella seconda bisogna includere anche 
						i dati della prima, da specifiche)

	Account			>	user_profile

	Logout			>	home (dopo il logout)
-->

<!-- inizio barra di navigazione -->
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<a href="#"> <img src="../img/logo.svg" class="nav_logo"></a>
	<ul class="nav nav-pills pull-right nav_style">
		<li><a href="#">Notifications</a></li>
		<li><a href="user_profile.jsp">Account</a></li>
		<li><a href="#"><span class="fa fa-sign-out fa-fw"></span>Logout</a></li>
	</ul>
</div>
<!-- fine barra di navigazione -->