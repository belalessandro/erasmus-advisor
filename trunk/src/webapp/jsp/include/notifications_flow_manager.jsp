<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
	// inizializza tablesorter
	$(document).ready(function() 
   	{ 
       	$("#classTable").tablesorter({ 
	        headers: { 
	            0: { sorter: false }
	        } 
	    });
       	$("#thesisTable").tablesorter({ 
	        headers: { 
	            0: { sorter: false }
	        } 
	    });
  	}); 
</script>

<!-- i risultati vanno creati dinamicamente
	alla pressione sui tasti corrisponde l'azione di conferma o rimozione -->

<h4 align="center">Reported Courses</h4>
<div class="alert alert-danger alert-dismissable">
	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
	There are new courses that need a check.
</div>

<table class="table table-condensed table-hover table-striped tablesorter" id="classTable">
	<thead>
		<tr>
			<th>Actions</th>
			<th>Name</th>
			<th>University</th>
			<th>Professor</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon-ok"></span>
				</button>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon glyphicon-remove"></span>
				</button>
			</td>
			<td><a href="#">Analisi del Pene</a></td>
			<td>Università agli Studi di Padova</td>
			<td>HUEHUEHUEHUE</td>
		</tr>
		<tr>
			<td>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon-ok"></span>
				</button>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon glyphicon-remove"></span>
				</button>
			</td>
			<td><a href="#">Culo</a></td>
			<td>Università agli Studi di Bologna</td>
			<td>Matteo Montesi</td>
		</tr>
	</tbody>
</table>
<br><br>
<h4 align="center">Reported Theses</h4>
<div class="alert alert-danger alert-dismissable">
	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
	There are new theses that need a check.
</div>

<table class="table table-condensed table-hover table-striped tablesorter" id="thesisTable">
	<thead>
		<tr>
			<th></th>
			<th>Name</th>
			<th>University</th>
			<th>Professor</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon-ok"></span>
				</button>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon glyphicon-remove"></span>
				</button>
			</td>
			<td><a href="#">;DROP TABLE Users CASCADE;</a></td>
			<td>London University</td>
			<td>LOLOL</td>
		</tr>
		<tr>
			<td>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon-ok"></span>
				</button>
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon glyphicon-remove"></span>
				</button>
			</td>
			<td><a href="#">Analisys of Trolls</a></td>
			<td>Università agli Studi di Napoli</td>
			<td>Paolo Chiavetor</td>
		</tr>
	</tbody>
</table>
