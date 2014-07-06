<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="lang" scope="session" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" />
<c:set var="root" scope="application">
	<spring:message code="application.root.url" />
</c:set>
<c:set var="phase" scope="application">
	<spring:message code="phase.name" />
</c:set>
<c:set var="isAuthenticated" value="false" scope="session" />
<sec:authorize access="isAuthenticated()">
	<c:set var="isAuthenticated" value="true" scope="session" />
</sec:authorize>

<!DOCTYPE html>
<html lang="${lang}">

<!-- Head -->
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Security for Ajax calls -->
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<meta name="google-site-verification" content="bFNzltBrYwsapKJE-g0zTlyHG8LHp4ft6LQd8BrtGeY" />
<meta name="keywords" content="<spring:message code="application.keywords" />">
<meta name="description" content="<spring:message code="application.description" />">
<meta name="author" content="<spring:message code="application.author" />">
<link rel="canonical" href="${root}" />

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

<script>
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o), m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, 'script', '//www.google-analytics.com/analytics.js',
			'ga');

	ga('create', 'UA-51823341-1', 'updapy.com');
	ga('send', 'pageview');
</script>

<script type="text/javascript">
	if (window.location.hash && window.location.hash == '#_=_') {
		if (window.history && history.pushState) {
			window.history.pushState("", document.title,
					window.location.pathname);
		} else {
			// Prevent scrolling by storing the page's current scroll offset
			var scroll = {
				top : document.body.scrollTop,
				left : document.body.scrollLeft
			};
			window.location.hash = '';
			// Restore the scroll offset, should be flicker free
			document.body.scrollTop = scroll.top;
			document.body.scrollLeft = scroll.left;
		}
	}
</script>

<c:set var="title">
	<tiles:insertAttribute name="title" />
</c:set>
<title><spring:message code="application.name" /> | <spring:message code="${title}" /></title>

<!-- JQuery -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="<spring:url value="/resources/js/jquery.color.min.js" />"></script>

<!-- Bootstrap and plugins -->
<link href="<spring:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<spring:url value="/resources/js/bootstrap.min.js" />"></script>
<link href="<spring:url value="/resources/css/social-buttons-3.css" />" rel="stylesheet">

<!-- Ladda -->
<link href="<spring:url value="/resources/css/ladda.min.css" />" rel="stylesheet">
<script src="<spring:url value="/resources/js/spin.min.js" />"></script>
<script src="<spring:url value="/resources/js/ladda.min.js" />"></script>

<!-- Custom styles for this template -->
<link href="<spring:url value="/resources/css/color-styles.css" />" rel="stylesheet">
<link href="<spring:url value="/resources/css/ui-elements.css" />" rel="stylesheet">
<link href="<spring:url value="/resources/css/custom.css" />" rel="stylesheet">

<!-- Resources -->
<link href="<spring:url value="/resources/css/animate.css" />" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.css" rel="stylesheet">
<link href='http://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>

<!-- HTML5 shiv and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	  <script src="//cdn.jsdelivr.net/html5shiv/3.7.2/html5shiv.min.js"></script>
	  <script src="//cdn.jsdelivr.net/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>

<body class="body-green">

	<!-- Navigation bar -->
	<tiles:insertAttribute name="navbar" />

	<!-- Content -->
	<c:set var="contentWrapperCss">
		<tiles:insertAttribute name="contentWrapperCss" />
	</c:set>
	<div class="wrapper ${contentWrapperCss}">
		<tiles:insertAttribute name="content" />
	</div>

	<!-- Footer -->
	<tiles:insertAttribute name="footer" />

	<!-- Bootstrap core JavaScript and Misc
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="<spring:url value="/resources/js/custom.js" />"></script>
	<script src="<spring:url value="/resources/js/scrolltopcontrol.js" />"></script>

	<!-- Sharebar -->
	<tiles:insertAttribute name="sharebar" />

</body>
</html>
