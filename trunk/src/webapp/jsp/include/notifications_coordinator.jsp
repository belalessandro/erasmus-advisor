<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script>
	// inizializza tablesorter
	$(document).ready(function() 
   	{ 
       	$("#coordinatorTable").tablesorter({ 
	        headers: { 
	            0: { sorter: false }
	        } 
	    });
  	}); 
</script>

<!-- i risultati vanno creati dinamicamente
	alla pressione sui tasti corrisponde l'azione di conferma o rimozione -->
	
<h4 align="center">New Flow Managers</h4>
<div class="alert alert-dismissable alert-danger">
	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
	There are * 2 * Flow Manager whose identity needs to be confirmed! Deactivate the account in case of doubt.
</div>
<table class="table table-condensed table-hover table-striped tablesorter" id="coordinatorTable">
	<thead>
		<tr>
			<th>Actions</th>
			<th>User Name</th>
			<th>Email</th>
			<th>Registration Date</th>
			<th>State</th>
		</tr>
	</thead>
	<tbody>
		<tr class="danger">
			<td>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon-ok"></span>
				</button>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon glyphicon-remove"></span>
				</button>
			</td>
			<td>capri</td>
			<td>dmcourse@unipd.it</td>
			<td>03/04/2012</td>
			<td>To be approved</td>
		</tr>
		<tr class="danger">
			<td>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon-ok"></span>
				</button>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon glyphicon-remove"></span>
				</button>
			</td>
			<td>valcher</td>
			<td>mariavalcher@unipd.it</td>
			<td>03/04/2012</td>
			<td>To be approved</td>
		</tr>
		<tr>
			<td></td>
			<td>geppo</td>
			<td>geppino@unipd.it</td>
			<td>02/04/2012</td>
			<td>Active</td>
		</tr>
		<tr>
			<td></td>
			<td>finesso</td>
			<td>prova@prova.com</td>
			<td>01/04/2012</td>
			<td>Active</td>
		</tr>
		<tr>
			<td></td>
			<td>amenos</td>
			<td>tecnico@unipd.it</td>
			<td>01/04/2012</td>
			<td>Active</td>
		</tr>
	</tbody>
</table>
<br><br>