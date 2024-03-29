<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder"%>

<c:set var="springLocale" scope="session" value="<%=LocaleContextHolder.getLocale().getLanguage()%>" />
<c:set var="lang" scope="session" value="${not empty param.lang ? param.lang : not empty lang ? lang : not empty springLocale ? springLocale : pageContext.request.locale.language}" />

<c:set var="root" scope="application">
	<spring:message code="application.root.url" />
</c:set>

<c:set var="isAuthenticated" value="false" scope="session" />
<sec:authorize access="isAuthenticated()">
	<c:set var="isAuthenticated" value="true" scope="session" />
</sec:authorize>

<c:set var="isAdmin" value="false" scope="session" />
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:set var="isAdmin" value="true" scope="session" />
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
<meta name="alexaVerifyID" content="hy2BVA3Co4m6XYlZT73StDzyU8c" />
<meta name="keywords" content="<spring:message code="application.keywords" />">
<meta name="description" content="<spring:message code="application.description" />">
<meta name="author" content="<spring:message code="application.author" />">
<link rel="canonical" href="${root}" />

<link rel="shortcut icon" href="<spring:url value="/resources/img/favicon/favicon.ico" />">
<link rel="apple-touch-icon" sizes="57x57" href="<spring:url value="/resources/img/favicon/apple-touch-icon-57x57.png" />">
<link rel="apple-touch-icon" sizes="60x60" href="<spring:url value="/resources/img/favicon/apple-touch-icon-60x60.png" />">
<link rel="apple-touch-icon" sizes="72x72" href="<spring:url value="/resources/img/favicon/apple-touch-icon-72x72.png" />">
<link rel="apple-touch-icon" sizes="76x76" href="<spring:url value="/resources/img/favicon/apple-touch-icon-76x76.png" />">
<link rel="apple-touch-icon" sizes="114x114" href="<spring:url value="/resources/img/favicon/apple-touch-icon-114x114.png" />">
<link rel="apple-touch-icon" sizes="120x120" href="<spring:url value="/resources/img/favicon/apple-touch-icon-120x120.png" />">
<link rel="apple-touch-icon" sizes="144x144" href="<spring:url value="/resources/img/favicon/apple-touch-icon-144x144.png" />">
<link rel="apple-touch-icon" sizes="152x152" href="<spring:url value="/resources/img/favicon/apple-touch-icon-152x152.png" />">
<link rel="apple-touch-icon" sizes="180x180" href="<spring:url value="/resources/img/favicon/apple-touch-icon-180x180.png" />">
<link rel="icon" type="image/png" href="<spring:url value="/resources/img/favicon/favicon-32x32.png" />" sizes="32x32">
<link rel="icon" type="image/png" href="<spring:url value="/resources/img/favicon/android-chrome-192x192.png" />" sizes="192x192">
<link rel="icon" type="image/png" href="<spring:url value="/resources/img/favicon/favicon-96x96.png" />" sizes="96x96">
<link rel="icon" type="image/png" href="<spring:url value="/resources/img/favicon/favicon-16x16.png" />" sizes="16x16">
<link rel="manifest" href="<spring:url value="/resources/img/favicon/manifest.json" />">
<meta name="msapplication-TileColor" content="#27ae60">
<meta name="msapplication-TileImage" content="<spring:url value="/resources/img/favicon/mstile-144x144.png" />">
<meta name="msapplication-config" content="<spring:url value="/resources/img/favicon/browserconfig.xml" />">
<meta name="theme-color" content="#27ae60">

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

	ga('create', 'UA-51823341-1', '<spring:message code='application.domain' />');
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
	genericErrorMessage = "<spring:message code='error.generic.message' />";
</script>

<c:set var="title">
	<tiles:insertAttribute name="title" />
</c:set>
<title><spring:message code="application.name" /> | <spring:message code="${title}" /></title>

<!-- Combined resources -->
<link href="<spring:url value="/resources/css/combined.css" />" rel="stylesheet">
<script src="<spring:url value="/resources/js/combined.begin.js" />"></script>

<!-- Resources -->
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<link href="//fonts.googleapis.com/css?family=Lobster" rel="stylesheet" type="text/css">
<link href="//fonts.googleapis.com/css?family=Oswald" rel="stylesheet" type="text/css">

<!-- HTML5 shiv and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	  <script src="//cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script>
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
	<script src="<spring:url value="/resources/js/combined.end.js" />"></script>

</body>
</html>
