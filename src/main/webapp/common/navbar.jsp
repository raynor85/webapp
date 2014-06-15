<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container container-menu">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand logo" href="${root}/" title="<spring:message code="menu.welcome" />"><spring:message code="application.name" /></a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="menu.language" /> <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="javascript:changeLocale('en');"><spring:message code="menu.language.en" /></a></li>
						<li><a href="javascript:changeLocale('fr');"><spring:message code="menu.language.fr" /></a></li>
					</ul></li>
				<li id="nav-faq"><a href="${root}/faq/"><spring:message code="menu.faq" /></a></li>
				<li id="nav-developers"><a href="${root}/developers/"><spring:message code="menu.developers" /></a></li>
				<c:if test="${isAuthenticated}">
					<li id="nav-dashboard"><a href="${root}/dashboard/"><spring:message code="menu.dashboard" /></a></li>
					<c:choose>
						<c:when test="${nbNotifications < 1}">
							<c:set var="messageKeyNotification">menu.notification.single</c:set>
							<c:set var="styleNotification"></c:set>
						</c:when>
						<c:when test="${nbNotifications == 1}">
							<c:set var="messageKeyNotification">menu.notification.single</c:set>
							<c:set var="styleNotification">badge-notification</c:set>
						</c:when>
						<c:when test="${nbNotifications > 1}">
							<c:set var="messageKeyNotification">menu.notification.multi</c:set>
							<c:set var="styleNotification">badge-notification</c:set>
						</c:when>
					</c:choose>
					<li class="dropdown"><a href="#" onclick="javascript:ajaxReadNotifications();" class="dropdown-toggle" data-toggle="dropdown"><span id="badge-notification" class="badge ${styleNotification}">${nbNotifications}</span> <span id="text-notification"><spring:message code="${messageKeyNotification}" /></span> <b class="caret"></b></a>
						<ul id="notifications" class="dropdown-menu dropdown-menu-right-sm">
						</ul></li>
				</c:if>
			</ul>
			<c:if test="${phase != 'early'}">
				<c:choose>
					<c:when test="${isAuthenticated}">
						<c:url value="/j_spring_security_logout" var="logoutUrl" />
						<!-- csrt for log out-->
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form>
						<ul class="nav navbar-nav navbar-right hidden-xs">
							<li><span class="user-bar-icons"> <a href="javascript:logout()" title="<spring:message code="menu.logout" />"><i class="fa fa-sign-out" id="sign-out"></i></a> <a href="${root}/settings/" title="<spring:message code="menu.settings" />"><i class="fa fa-cog"></i></a>
							</span></li>
							<li id="user-bar" class="hidden-sm"><span class="user-bar-avatar pull-right"> <img src="<spring:url value="/resources/img/dashboard/user-normal.png" />" alt="User default avatar">
							</span> <a class="pull-right noHover" id="username"> <c:set var="username">
										<sec:authentication property="principal.username" />
									</c:set> <c:out value="${username}" escapeXml="false" />
							</a></li>
						</ul>
						<ul class="nav navbar-nav navbar-right visible-xs">
							<li><a href="${root}/settings/"><i class="fa fa-cog fa-1-4x" title="<spring:message code="menu.settings" />"></i>&nbsp;&nbsp;<spring:message code="menu.settings" /></a></li>
							<li><a href="javascript:logout();"><i class="fa fa-sign-out fa-1-4x" title="<spring:message code="menu.logout" />"></i>&nbsp; <spring:message code="menu.logout" /></a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul class="nav navbar-nav navbar-right hidden-xs">
							<li><div>
									<a class="btn btn-color ladda-button ladda-button-small btn-nav-sign" href="${root}/sign/"><spring:message code="menu.sign" /></a>
								</div></li>
						</ul>
						<ul class="nav navbar-nav navbar-right visible-xs">
							<li><div>
									<a class="btn btn-color ladda-button ladda-button-small btn-nav-sign-xs" href="${root}/sign/"><spring:message code="menu.sign" /></a>
								</div></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</div>
</div>

<script>
	function logout() {
		$("#logoutForm").submit();
	}
	function changeLocale(newLocale) {
		ajaxChangeLocale(newLocale);
		location.href = location.pathname + "?lang=" + newLocale;
	}
	function ajaxChangeLocale(newLocale) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			type : "POST",
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			url : "${root}/user/locale",
			data : newLocale,
			contentType : "text/plain",
			async : false,
			cache : false
		});
	};
	function ajaxReadNotifications() {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$
				.ajax({
					type : "POST",
					beforeSend : function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					url : "${root}/dashboard/notifications",
					contentType : "application/json",
					cache : false,
					success : function(response) {
						responseNotifications = "";
						for (var i = 0; i < response.length; i++) {
							var bgcolor = "transparent";
							var darkColor = "#333";
							var lightColor = "#848484";
							if (!response[i].wasRead) {
								bgcolor = "#FBEEED";
								darkColor = "#B93935";
								lightColor = "#E17572";
							}
							responseNotifications += "<li><a class='noHover' style='background-color: "
									+ bgcolor
									+ " !important;color: "
									+ darkColor
									+ " !important;'><div style='display: inline-block;min-width:270px;width:100%;'><div class='pull-left'>"
									+ response[i].applicationName
									+ " <em style='color:" + lightColor + ";'>("
									+ response[i].versionNumber
									+ ")</em></div>";
							if (!response[i].wasRead) {
								responseNotifications += "<div class='pull-right'><span class='label label-danger'><spring:message code='menu.notification.new' /></span></div>";
							}
							responseNotifications += "</div></a></li>";
						}
						$("#notifications").html(responseNotifications);
						$("#badge-notification").text(0);
						$("#text-notification")
								.html(
										"<spring:message code='menu.notification.single' />");
						$("#badge-notification").removeClass(
								"badge-notification");
					}
				});
	}
	var setActiveMenu = function() {
		if (location.href.match(/faq.?/)) {
			$("#nav-faq").addClass("active");
		} else if (location.href.match(/developers.?/)) {
			$("#nav-developers").addClass("active");
		} else if (location.href.match(/dashboard.?/)) {
			$("#nav-dashboard").addClass("active");
		}
	}();
</script>
