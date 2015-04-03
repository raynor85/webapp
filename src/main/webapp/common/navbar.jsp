<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="lang" value="${lang}" />
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
				<li id="dropdown-lang" class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <c:choose>
							<c:when test="${lang == 'en'}">
								<img width="16" height="11" src="<spring:url value="/resources/img/flag/en.png" />">
							</c:when>
							<c:when test="${lang == 'fr'}">
								<img width="16" height="11" src="<spring:url value="/resources/img/flag/fr.png" />">
							</c:when>
						</c:choose> <i class="fa fa-angle-down "></i></a>
					<ul class="dropdown-menu">
						<li id="lang-en"><a style="border-bottom: 0px !important;" href="javascript:changeLocale('en');"><img width="16" height="11" class="flag-lang pull-right" src="<spring:url value="/resources/img/flag/en.png" />"> <spring:message code="menu.language.en" /></a></li>
						<li id="lang-fr"><a style="border-bottom: 0px !important;" href="javascript:changeLocale('fr');"><img width="16" height="11" class="flag-lang pull-right" src="<spring:url value="/resources/img/flag/fr.png" />"> <spring:message code="menu.language.fr" /></a></li>
					</ul></li>
				<c:if test="${not isAuthenticated}">
					<li id="nav-faq"><a href="${root}/faq"><spring:message code="menu.faq" /></a></li>
				</c:if>
				<c:choose>
					<c:when test="${isAdmin}">
						<li id="dropdown-admin" class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="menu.administration" /> <i class="fa fa-angle-down "></i></a>
							<ul class="dropdown-menu">
								<li id="nav-administration"><a href="${root}/administration"><spring:message code="menu.administration" /></a></li>
								<li id="nav-stats"><a href="${root}/administration/stats"><spring:message code="menu.stats" /></a></li>
								<li id="nav-message"><a href="${root}/administration/message"><spring:message code="menu.message" /></a></li>
								<li id="nav-log"><a href="https://papertrailapp.com/systems/updapy/events" target="_blank"><spring:message code="menu.log" /></a></li>
								<li id="nav-developers"><a href="${root}/developers"><spring:message code="menu.developers" /></a></li>
							</ul></li>
					</c:when>
					<c:otherwise>
						<li id="nav-developers"><a href="${root}/developers"><spring:message code="menu.developers" /></a></li>
					</c:otherwise>
				</c:choose>
				<c:if test="${not isAuthenticated}">
					<li id="nav-appslist"><a href="${root}/applications"><spring:message code="menu.appslist" /></a></li>
				</c:if>
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
					<li id="dropdown-notif" class="dropdown"><a href="#" onclick="javascript:ajaxReadNotifications();" class="dropdown-toggle" data-toggle="dropdown"><span id="badge-notification" class="badge ${styleNotification}">${nbNotifications}</span> <span id="text-notification"><spring:message code="menu.notification.${messageKeyNotification}" /></span> <i class="fa fa-angle-down "></i></a>
						<ul id="notifications" class="dropdown-menu dropdown-menu-right-sm">
							<li><a style="color: #777 !important; background-color: transparent !important;"><i style="color: #777 !important; background-color: transparent !important;" class="fa fa-refresh fa-spin"></i> &nbsp; <spring:message code="menu.notification.loading" /></a></li>
							<li><a tabindex="-1" href="${root}/rss/notifications?key=${rssKey}" target="_blank" onmouseover="javascript:$('#rss-icon').css('color', '#1a7440');" onmouseout="javascript:$('#rss-icon').css('color', '#FF6600');" style="color: #333; min-width: 310px"><i id="rss-icon" class="fa fa-rss fa-1-4x pull-right color-rss" style="display: block;"></i> <spring:message code="menu.notification.rss" /></a></li>
						</ul></li>
				</c:if>
				<li id="nav-contact" class="dropdown"><a href="#" class="dropdown-toggle share-menu" data-toggle="dropdown" title="<spring:message code='menu.support' />"> <i class="fa fa-share-alt share-icon"></i>&nbsp;<i class="fa fa-angle-down "></i></a>
					<ul id="socialMenuDropdown" class="dropdown-menu dropdown-menu-right-sm">
						<li><a tabindex="-1" href="${root}/contact"><i id="message-icon" class="fa fa-comments fa-1-4x pull-right" style="display: block;"></i> <spring:message code="menu.contact" /></a></li>
						<c:choose>
							<c:when test="${lang == 'fr'}">
								<c:set var="langFb" value="fr_FR" />
								<c:set var="langTwitter" value="fr" />
								<c:set var="langPaypal" value="fr_FR" />
							</c:when>
							<c:otherwise>
								<c:set var="langFb" value="en_US" />
								<c:set var="langTwitter" value="en" />
								<c:set var="langPaypal" value="en_US" />
							</c:otherwise>
						</c:choose>
						<li id="socialMenuDropdownFb" class="socialMenu"><iframe src="//www.facebook.com/plugins/like.php?locale=${langFb}&href=http%3A%2F%2Ffacebook.com%2Fupdapy&amp;width&amp;layout=button_count&amp;action=like&amp;show_faces=false&amp;share=true&amp;height=21&amp;appId=242605515939525" scrolling="no" frameborder="0" style="margin-bottom: -5px; border: none; overflow: hidden; height: 21px; width: 200px;" allowTransparency="true"></iframe></li>
						<li id="socialMenuDropdownG+" class="socialMenuTwitterGoogle"><div class="g-plusone" data-annotation="bubble" data-href="http://www.updapy.com" data-size="medium" style="margin-bottom: -5px;"></div></li>
						<li id="socialMenuDropdownTw" class="socialMenuTwitterGoogle"><a style="padding: 0 !important; margin-right: 12px; background-color: transparent !important;" class="twitter-follow-button" href="https://twitter.com/updapy" data-show-screen-name="false" data-show-count="false" data-lang="${langTwitter}">Follow</a> <a class="twitter-share-button" href="https://twitter.com/share" data-lang="${langTwitter}" data-url="http://www.updapy.com" data-via="updapy" data-text="<spring:message code='application.share.message' />" data-hashtags="<spring:message code='application.share.hashtags' />">Tweet</a></li>
						<li class="donatePaypal" onclick="location.href='https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=M4URMQRZH7J2G';" style="cursor: pointer;">
							<a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=M4URMQRZH7J2G" style="padding: 0 !important; margin-right: 12px; background-color: transparent !important;"><img width="86" height="21" class="pull-right" border="0" src="<spring:url value="/resources/img/other/paypal.png" />"></a>
							<input type="image" src="https://www.paypalobjects.com/${langPaypal}/i/btn/btn_donate_SM.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
						</li>
					</ul></li>
			</ul>
			<c:choose>
				<c:when test="${isAuthenticated}">
					<c:url value="/j_spring_security_logout" var="logoutUrl" />
					<!-- csrt for log out-->
					<form action="${logoutUrl}" method="post" id="logoutForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
					<ul class="nav navbar-nav navbar-right hidden-xs">
						<li class="noHover"><span class="user-bar-icons"> <a href="javascript:logout()" title="<spring:message code="menu.logout" />"><i class="fa fa-sign-out" id="sign-out"></i></a>&nbsp;<a href="${root}/settings" title="<spring:message code="menu.settings" />"><i class="fa fa-cog"></i></a>
						</span></li>
						<li id="user-bar" class="hidden-sm noHover"><span class="user-bar-avatar pull-right"> <c:choose>
									<c:when test="${isAuthenticated}">
										<c:set var="isSocialUser">
											<sec:authentication property="principal.socialUser" />
										</c:set>
										<c:choose>
											<c:when test="${isSocialUser}">
												<img src="<sec:authentication property="principal.avatarUrl" />" alt="User avatar">
											</c:when>
											<c:otherwise>
												<img width="116" height="116" src="<spring:url value="/resources/img/dashboard/user-normal.png" />" alt="User default avatar">
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<img width="116" height="116" src="<spring:url value="/resources/img/dashboard/user-normal.png" />" alt="User default avatar">
									</c:otherwise>
								</c:choose>
						</span> <c:set var="username">
								<sec:authentication property="principal.name" />
							</c:set> <a class="pull-right text-muted" id="username" title="<c:out value='${username}' escapeXml='false' />"> <c:out value="${username}" escapeXml="false" />
						</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right visible-xs">
						<li id="nav-settings"><a href="${root}/settings"><i class="fa fa-cog fa-1-4x" title="<spring:message code="menu.settings" />"></i>&nbsp;&nbsp;<spring:message code="menu.settings" /></a></li>
						<li><a href="javascript:logout();"><i class="fa fa-sign-out fa-1-4x" title="<spring:message code="menu.logout" />"></i>&nbsp; <spring:message code="menu.logout" /></a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul class="nav navbar-nav navbar-right hidden-xs">
						<li class="noHover"><div>
								<a id="menu-sign" class="btn btn-color ladda-button" href="${root}/sign" style="margin-top: 10px !important; font-size: 15px !important; padding: 8px 10px !important;"><spring:message code="menu.sign" /></a>
							</div></li>
					</ul>
					<ul class="nav navbar-form navbar-nav navbar-right visible-xs">
						<li class="noHover"><div class="text-center">
								<a class="btn btn-color ladda-button" href="${root}/sign" style="font-size: 15px !important; padding: 8px 14px !important;"><spring:message code="menu.sign" /></a>
							</div></li>
					</ul>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

<script>
	var setActiveMenu = function() {
		// current lang
		var langToHide;
		if ($("#dropdown-lang").find("img").attr("src").indexOf("fr.png") > -1) {
			langToHide = "fr";
		} else {
			langToHide = "en";
		}
		$("#lang-" + langToHide).hide();
		// current tab
		if (location.href.match(/faq.?/)) {
			$("#nav-faq").addClass("active");
		} else if (location.href.match(/developers.?/)) {
			if ("${isAdmin}" === "true") {
				$("#dropdown-admin").addClass("active");
				$("#nav-administration a").addClass("notActive");
				$("#nav-stats a").addClass("notActive");
				$("#nav-message a").addClass("notActive");
				$("#nav-log a").addClass("notActive");
			} else {
				$("#nav-developers").addClass("active");
			}
		} else if (location.href.match(/applications.?/)) {
			$("#nav-appslist").addClass("active");
		} else if (location.href.match(/dashboard.?/)) {
			$("#nav-dashboard").addClass("active");
		} else if (location.href.match(/contact.?/)) {
			$("#nav-contact").addClass("active");
		} else if (location.href.match(/settings.?/)) {
			$("#nav-settings").addClass("active");
		} else if (location.href.match(/administration\/message.?/)) {
			$("#dropdown-admin").addClass("active");
			$("#nav-administration a").addClass("notActive");
			$("#nav-stats a").addClass("notActive");
			$("#nav-log a").addClass("notActive");
			$("#nav-developers a").addClass("notActive");
		} else if (location.href.match(/administration\/stats.?/)) {
			$("#dropdown-admin").addClass("active");
			$("#nav-administration a").addClass("notActive");
			$("#nav-message a").addClass("notActive");
			$("#nav-log a").addClass("notActive");
			$("#nav-developers a").addClass("notActive");
		} else if (location.href.match(/administration.?/)) {
			$("#dropdown-admin").addClass("active");
			$("#nav-stats a").addClass("notActive");
			$("#nav-message a").addClass("notActive");
			$("#nav-log a").addClass("notActive");
			$("#nav-developers a").addClass("notActive");
		}
	}();
	// Google+1
	window.___gcfg = {
		lang : "${lang}",
	};
	(function() {
		var po = document.createElement('script');
		po.type = 'text/javascript';
		po.async = true;
		po.src = 'https://apis.google.com/js/platform.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(po, s);
	})();
	// Twitter
	!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');
	function logout() {
		$("#logoutForm").submit();
	}
	function changeLocale(newLocale) {
		ajaxChangeLocale(newLocale);
		var currentUrl = location.href;
		var langParam = "lang=";
		var newLang = langParam + newLocale;
		if (currentUrl.indexOf(langParam) > -1) {
			location.href = currentUrl.replace(new RegExp(langParam + ".."), newLang);
		} else {
			if (currentUrl.indexOf("?") > -1) {
				currentUrl = currentUrl + "&";
			} else {
				currentUrl = currentUrl + "?";
			}
			location.href = currentUrl + newLang;
		};
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
			cache : false,
		});
	};
</script>

<c:if test="${not isAuthenticated}">
	<script>
		var adaptTextToMenuSize = function() {
			var signText;
			var width = $("div.navbar-collapse").width();
			if (width < 900 && width > 300) {
				signText = "<spring:message code='menu.sign.short' />";
			} else {
				signText = "<spring:message code='menu.sign' />";
			}
			$("#menu-sign").text(signText);
		}();
	</script>
</c:if>

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
									var link = response[i].versionDownloadLink;
									if (!response[i].wasRead) {
										bgcolor = "#FBEEED";
										darkColor = "#B93935";
										lightColor = "#E17572";
									}
									responseNotifications += "<li><a style='background-color: "
											+ bgcolor
											+ ";color: "
											+ darkColor
											+ ";' href='"
											+ link
											+ "' target='_blank'><div style='display: inline-block;min-width:310px;width:100%;'><div class='pull-left'>"
											+ response[i].applicationName
											+ " <em style='color:" + lightColor + ";'>("
											+ response[i].versionNumber
											+ ")</em></div>";
								} else if (response[i].type == 'NEW_APPLICATION') {
									var styleNew = "info";
									var link = "${root}/applications/" + response[i].applicationApiName;
									if (!response[i].wasRead) {
										bgcolor = "#EFF9FC";
										darkColor = "#117EDD";
										lightColor = "#29A3DB";
									}
									responseNotifications += "<li><a style='background-color: "
											+ bgcolor
											+ ";color: "
											+ darkColor
											+ ";' href='"
											+ link
											+ "'><div style='display: inline-block;min-width:310px;width:100%;'><div class='pull-left'>"
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
								responseNotifications += "<li><a style='color: #777 !important; background-color: transparent !important;'><spring:message code='menu.notification.nothing' /></a></li>";
							}
							responseNotifications += "<li><a tabindex='-1' href='${root}/rss/notifications?key=${rssKey}' target='_blank' onmouseover=\"javascript:$('#rss-icon').css('color', '#1a7440');\" onmouseout=\"javascript:$('#rss-icon').css('color', '#FF6600');\" style='color: #333;min-width: 310px;'><i id='rss-icon' class='fa fa-rss fa-1-4x pull-right color-rss' style='display: block;'></i> <spring:message code='menu.notification.rss' /></a></li>";
							$("#notifications").html(responseNotifications);
							$("#badge-notification").text(0);
							$("#text-notification")
									.html(
											"<spring:message code='menu.notification.single' />");
							$("#badge-notification").removeClass(
									"badge-notification");
						}
					});
		};
	</script>
</c:if>
