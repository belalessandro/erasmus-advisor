<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>jQuery UI Autocomplete - Remote with caching</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<style>
.ui-autocomplete-loading {
background: white url("<c:url value="/img"/>/ui-anim_basic_16x16.gif") right center no-repeat;
}
</style>
<script>
$(function() {
var cache = {};
$( "#universityNames" ).autocomplete({
minLength: 2,
source: function( request, response ) {
var term = request.term;
if ( term in cache ) {
response( cache[ term ] );
return;
}
$.getJSON( "<c:url value="/university/list"/>", request, function( data, status, xhr ) {
cache[ term ] = data;
response( data );
});
}
});
});
</script>
</head>
<body>
<div class="ui-widget">
<label for="universityNames">University: </label>
<input id="universityNames">
</div>
</body>
</html>