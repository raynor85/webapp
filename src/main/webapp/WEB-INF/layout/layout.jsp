<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" scope="session" />

<!DOCTYPE html>
<html lang="${lang}">

  <!-- Head -->
  <head>
  
  
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<meta name="description" content="<spring:message code="application.description" />">
	<meta name="author" content="<spring:message code="application.author" />">
	<link rel="canonical" href="<spring:message code="application.url" />" />
	
	<link rel="shortcut icon" href="<spring:url value="/resources/img/favicon/favicon.ico" />">
	<link rel="apple-touch-icon" sizes="57x57" href="<spring:url value="/resources/img/favicon/apple-touch-icon-57x57.png" />">
	<link rel="apple-touch-icon" sizes="114x114" href="<spring:url value="/resources/img/favicon/apple-touch-icon-114x114.png" />">
	<link rel="apple-touch-icon" sizes="72x72" href="<spring:url value="/resources/img/favicon/apple-touch-icon-72x72.png" />">
	<link rel="apple-touch-icon" sizes="144x144" href="<spring:url value="/resources/img/favicon/apple-touch-icon-144x144.png" />">
	<link rel="apple-touch-icon" sizes="60x60" href="<spring:url value="/resources/img/favicon/apple-touch-icon-60x60.png" />">
	<link rel="apple-touch-icon" sizes="120x120" href="<spring:url value="/resources/img/favicon/apple-touch-icon-120x120.png" />">
	<link rel="apple-touch-icon" sizes="76x76" href="<spring:url value="/resources/img/favicon/apple-touch-icon-76x76.png" />">
	<link rel="apple-touch-icon" sizes="152x152" href="<spring:url value="/resources/img/favicon/apple-touch-icon-152x152.png" />">
	<link rel="icon" type="image/png" href="<spring:url value="/resources/img/favicon/favicon-196x196.png" />" sizes="196x196">
	<link rel="icon" type="image/png" href="<spring:url value="/resources/img/favicon/favicon-160x160.png" />" sizes="160x160">
	<link rel="icon" type="image/png" href="<spring:url value="/resources/img/favicon/favicon-96x96.png" />" sizes="96x96">
	<link rel="icon" type="image/png" href="<spring:url value="/resources/img/favicon/favicon-16x16.png" />" sizes="16x16">
	<link rel="icon" type="image/png" href="<spring:url value="/resources/img/favicon/favicon-32x32.png" />" sizes="32x32">
	<meta name="msapplication-TileColor" content="#0081b5">
	<meta name="msapplication-TileImage" content="<spring:url value="/resources/img/favicon/mstile-144x144.png" />">
	<meta name="msapplication-square70x70logo" content="<spring:url value="/resources/img/favicon/mstile-70x70.png" />">
	<meta name="msapplication-square144x144logo" content="<spring:url value="/resources/img/favicon/mstile-144x144.png" />">
	<meta name="msapplication-square150x150logo" content="<spring:url value="/resources/img/favicon/mstile-150x150.png" />">
	<meta name="msapplication-square310x310logo" content="<spring:url value="/resources/img/favicon/mstile-310x310.png" />">
	<meta name="msapplication-wide310x150logo" content="<spring:url value="/resources/img/favicon/mstile-310x150.png" />">
	
	<c:set var="title"><tiles:insertAttribute name="title" /></c:set>
	<title><spring:message code="application.name" /> | <spring:message code="${title}" /></title>

	<!-- Bootstrap core CSS -->
	<link href="<spring:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="<spring:url value="/resources/css/color-styles.css" />" rel="stylesheet">
	<link href="<spring:url value="/resources/css/ui-elements.css" />" rel="stylesheet">
	<link href="<spring:url value="/resources/css/custom.css" />" rel="stylesheet">
	
	<!-- Resources -->
	<link href="<spring:url value="/resources/css/animate.css" />" rel="stylesheet">
	<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'>
	<link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>

	<!-- HTML5 shiv and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->
  </head>
	
  <body class="body-green">

	<!-- Navigation bar -->
	<tiles:insertAttribute name="navbar" />

	<!-- Content -->
	<div class="wrapper">
		<tiles:insertAttribute name="content" />
	</div>
	
	<!-- footer -->
	<tiles:insertAttribute name="footer" />

	<!-- Bootstrap core JavaScript and Misc
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="<spring:url value="/resources/js/bootstrap.min.js" />"></script>
	<script src="<spring:url value="/resources/js/custom.js" />"></script>
	<script src="<spring:url value="/resources/js/scrolltopcontrol.js" />"></script>
	<%@ include file="/common/sharebar.jspf" %>
</body></html>
