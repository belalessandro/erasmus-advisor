<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty message}" >
	<c:choose>
		<c:when test="${message.error}">
			<div class="alert alert-danger">
			
				<div class="row">
					<div class="col-md-2 text-left">
						<strong>Error code</strong>
		    		</div>
		    		<div class="col-md-10 text-left">
		    			<c:out value="${message.errorCode}"/>
		    		</div>
				</div>
				
				<div class="row">
					<div class="col-md-2 text-left">
						<strong>Error message</strong>
		    		</div>
		    		<div class="col-md-10 text-left">
		    			<c:out value="${message.message}"/>
		    		</div>
				</div>
				
				<div class="row">
					<div class="col-md-2 text-left">
						<strong>Error details</strong>
		    		</div>
		    		<div class="col-md-10 text-left">
		    			<c:out value="${message.errorDetails}"/>
		    		</div>
				</div>
			</div>
		</c:when>
		
		<c:otherwise>
			<div class="alert alert-success text-center h2">
				<div class="row">
		    		<div class="col-md-12 text-center">
		    			<c:out value="${message.message}"/>
		    		</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>   
	<br>
</c:if> 	