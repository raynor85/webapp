<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="navbar updapy-navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container container-menu">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand logo" href="${root}" title="<spring:message code="menu.welcome" />"><spring:message code="application.name" /></a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="menu.language" /> <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="javascript:changeLocale('en');"><spring:message code="menu.language.en" /></a></li>
						<li><a href="javascript:changeLocale('fr');"><spring:message code="menu.language.fr" /></a></li>
					</ul></li>
				<c:if test="${not isAuthenticated}">
					<li id="nav-faq"><a href="${root}/faq"><spring:message code="menu.faq" /></a></li>
				</c:if>
				<li id="nav-developers"><a href="${root}/developers"><spring:message code="menu.developers" /></a></li>
				<c:if test="${isAuthenticated && nbNotifications != null}">
					<li id="nav-dashboard"><a href="${root}/dashboard"><spring:message code="menu.dashboard" /></a></li>
					<c:choose>
						<c:when test="${nbNotifications < 1}">
							<c:set var="messageKeyNotification">single</c:set>
							<c:set var="styleNotification"></c:set>
						</c:when>
						<c:when test="${nbNotifications == 1}">
							<c:set var="messageKeyNotification">single</c:set>
							<c:set var="styleNotification">badge-notification</c:set>
						</c:when>
						<c:when test="${nbNotifications > 1}">
							<c:set var="messageKeyNotification">multi</c:set>
							<c:set var="styleNotification">badge-notification</c:set>
						</c:when>
					</c:choose>
					<li class="dropdown"><a href="#" onclick="javascript:ajaxReadNotifications();" class="dropdown-toggle" data-toggle="dropdown"><span id="badge-notification" class="badge ${styleNotification}">${nbNotifications}</span> <span id="text-notification"><spring:message code="menu.notification.${messageKeyNotification}" /></span> <b class="caret"></b></a>
						<ul id="notifications" class="dropdown-menu dropdown-menu-right-sm">
							<li><a class="noHover" style="color: #777 !important; background-color: transparent !important;"><i style="color: #777 !important; background-color: transparent !important;" class="noHover fa fa-refresh fa-spin"></i> &nbsp; <spring:message code="menu.notification.loading" /></a></li>
							<li><a tabindex="-1" href="${root}/rss/notifications?key=${rssKey}" target="_blank" onmouseover="javascript:$('#rss-icon').css('color', '#1a7440');" onmouseout="javascript:$('#rss-icon').css('color', 'red');" style="color: #333; min-width: 310px"><i id="rss-icon" class="fa fa-rss fa-1-4x pull-right color-rss" style="display: block;"></i> <spring:message code="menu.notification.rss" /></a></li>
						</ul></li>
				</c:if>
				<li class="dropdown"><a href="#" class="dropdown-toggle share-icons" data-toggle="dropdown" title="<spring:message code='menu.support' />"> <i class="fa fa-share-alt"></i> <b class="caret"></b></a>
					<ul id="socialMenuDropdown" class="dropdown-menu">
						<li><a class="noHover" style="color: #555 !important; background-color: transparent !important;"><spring:message code="menu.support.description" /></a></li>
						<c:choose>
							<c:when test="${lang == 'fr'}">
								<c:set var="langFb" value="fr_FR" />
							</c:when>
							<c:otherwise>
								<c:set var="langFb" value="en_US" />
							</c:otherwise>
						</c:choose>
						<li id="socialMenuDropdownFb" class="socialMenu"><iframe src="//www.facebook.com/plugins/like.php?locale=${langFb}&href=http%3A%2F%2Ffacebook.com%2Fupdapy&amp;width&amp;layout=button_count&amp;action=like&amp;show_faces=false&amp;share=true&amp;height=21&amp;appId=242605515939525" scrolling="no" frameborder="0" style="margin-bottom: -5px; border: none; overflow: hidden; height: 21px; width: 200px;" allowTransparency="true"></iframe></li>
						<li id="socialMenuDropdownG+" class="socialMenuGoogle"><div class="g-plusone" data-annotation="bubble" data-href="http://www.updapy.com" data-size="medium" style="margin-bottom: -5px;"></div></li>
						<li id="socialMenuDropdownTw" class="socialMenu"><iframe allowtransparency="true" frameborder="0" scrolling="no" src="http://platform.twitter.com/widgets/follow_button.html?screen_name=updapy&lang=${lang}" style="margin-bottom: -6px; height: 20px; width: 200px;"></iframe></li>
					</ul></li>
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
							<li><span class="user-bar-icons"> <a href="javascript:logout()" title="<spring:message code="menu.logout" />"><i class="fa fa-sign-out" id="sign-out"></i></a> <a href="${root}/settings" title="<spring:message code="menu.settings" />"><i class="fa fa-cog"></i></a>
							</span></li>
							<li id="user-bar" class="hidden-sm"><span class="user-bar-avatar pull-right"> <img src="<spring:url value="/resources/img/dashboard/user-normal.png" />" alt="User default avatar">
							</span> <a class="pull-right noHover" id="username"> <c:set var="username">
										<sec:authentication property="principal.username" />
									</c:set> <c:out value="${username}" escapeXml="false" />
							</a></li>
						</ul>
						<ul class="nav navbar-nav navbar-right visible-xs">
							<li><a href="${root}/settings"><i class="fa fa-cog fa-1-4x" title="<spring:message code="menu.settings" />"></i>&nbsp;&nbsp;<spring:message code="menu.settings" /></a></li>
							<li><a href="javascript:logout();"><i class="fa fa-sign-out fa-1-4x" title="<spring:message code="menu.logout" />"></i>&nbsp; <spring:message code="menu.logout" /></a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul class="nav navbar-nav navbar-right hidden-xs">
							<li><div>
									<a class="btn btn-color ladda-button" href="${root}/sign" style="margin-top: 12px !important; font-size: 15px !important; padding: 8px 14px !important;"><spring:message code="menu.sign" /></a>
								</div></li>
						</ul>
						<ul class="nav navbar-nav navbar-right visible-xs">
							<li><div>
									<a class="btn btn-color ladda-button" href="${root}/sign" style="margin-top: -5px !important; margin-left: 15px !important; font-size: 15px !important; padding: 8px 14px !important;"><spring:message code="menu.sign" /></a>
								</div></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</div>
</div>

<script>
	// Google+1
	window.___gcfg = {
		lang : "${lang}"
	};
	(function() {
		var po = document.createElement('script');
		po.type = 'text/javascript';
		po.async = true;
		po.src = 'https://apis.google.com/js/platform.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(po, s);
	})();
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

<c:if test="${isAuthenticated}">
	<script>
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
								var darkColor = "#666";
								var lightColor = "#848484";
								if (response[i].type == 'NEW_VERSION') {
									var styleNew = "danger";
									if (!response[i].wasRead) {
										bgcolor = "#FBEEED";
										darkColor = "#B93935";
										lightColor = "#E17572";
									}
									responseNotifications += "<li><a style='background-color: "
											+ bgcolor
											+ " !important;color: "
											+ darkColor
											+ " !important;'><div style='display: inline-block;min-width:310px;width:100%;'><div class='pull-left'>"
											+ response[i].applicationName
											+ " <em style='color:" + lightColor + ";'>("
											+ response[i].versionNumber
											+ ")</em></div>";
								} else if (response[i].type == 'NEW_APPLICATION') {
									var styleNew = "success";
									if (!response[i].wasRead) {
										bgcolor = "#E9F7EF";
										darkColor = "#1A7440";
										lightColor = "#27AE60";
									}
									responseNotifications += "<li><a style='background-color: "
											+ bgcolor
											+ " !important;color: "
											+ darkColor
											+ " !important;'><div style='display: inline-block;min-width:310px;width:100%;'><div class='pull-left'>"
											+ response[i].applicationName
											+ " <em style='color:" + lightColor + ";'>- "
											+ "<spring:message code='menu.notification.added.application' />"
											+ "</em></div>";
								} else if (response[i].type == 'NOT_SUPPORTED_APPLICATION') {
									var styleNew = "warning";
									if (!response[i].wasRead) {
										bgcolor = "#FAEBCC";
										darkColor = "#66512C";
										lightColor = "#8A6D3B";
									}
									responseNotifications += "<li><a style='background-color: "
											+ bgcolor
											+ " !important;color: "
											+ darkColor
											+ " !important;'><div style='display: inline-block;min-width:310px;width:100%;'><div class='pull-left'>"
											+ response[i].applicationName
											+ " <em style='color:" + lightColor + ";'>- "
											+ "<spring:message code='menu.notification.deleted.application' />"
											+ "</em></div>";
								}
								if (!response[i].wasRead) {
									responseNotifications += "<div class='pull-right'><span class='label label-" + styleNew + "'><spring:message code='menu.notification.new' /></span></div>";
								}
								responseNotifications += "</div></a></li>";
							}
							if (response.length == 0) {
								responseNotifications += "<li><a class='noHover' style='color: #777 !important; background-color: transparent !important;'><spring:message code='menu.notification.nothing' /></a></li>";
							}
							responseNotifications += "<li><a tabindex='-1' href='${root}/rss/notifications?key=${rssKey}' target='_blank' onmouseover=\"javascript:$('#rss-icon').css('color', '#1a7440');\" onmouseout=\"javascript:$('#rss-icon').css('color', 'red');\" style='color: #333;min-width: 310px;'><i id='rss-icon' class='fa fa-rss fa-1-4x pull-right color-rss' style='display: block;'></i> <spring:message code='menu.notification.rss' /></a></li>";
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
	</script>
</c:if>
