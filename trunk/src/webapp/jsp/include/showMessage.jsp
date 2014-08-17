<!--
 Copyright 2014 University of Padua, Italy
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
     http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 
 Author: Nicola Ferro (ferro@dei.unipd.it)
 Version: 1.0
 Since: 1.0
-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


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