<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- inizio barra di navigazione -->
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<c:choose>
		<c:when test="${(param.pageName != 'sign_in') && (param.pageName != 'error_not_logged')}">
			<a href="<c:url value="/"/>">
		</c:when>
		<c:otherwise><a href="<c:url value="/index"/>"></c:otherwise>
	</c:choose>
	<img src="<c:url value="/img"/>/logo.svg" class="nav_logo"></a>
	<ul class="nav nav-pills pull-right nav_style">
		<c:if test="${(param.pageName != 'sign_in') && (param.pageName != 'error_not_logged')}">
			<li><a href="<c:url value="/notifications"/>">Notifications</a></li>
			<li><a href="<c:url value="/user/profile"/>">Account</a></li>
			<li>
				<form method="post" action ="<c:url value="/logout"/>">
					<button type="submit" class="navbar_submit"><span class="fa fa-sign-out fa-fw"></span>Logout</button>
				</form>
			</li>
		</c:if>
	</ul>
</div>
<!-- fine barra di navigazione -->