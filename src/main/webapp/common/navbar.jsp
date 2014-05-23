<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize var="isAuthenticated" access="isAuthenticated()" />

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
						<li><a href="?lang=en"><spring:message code="menu.language.en" /></a></li>
						<li><a href="?lang=fr"><spring:message code="menu.language.fr" /></a></li>
					</ul></li>
				<li><a href="${root}/faq/"><spring:message code="menu.faq" /></a></li>
				<li><a href="${root}/privacy/"><spring:message code="menu.privacy" /></a></li>
				<c:if test="${isAuthenticated}">
					<li><a href="${root}/dashboard/"><spring:message code="menu.dashboard" /></a></li>
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
						<script>
							function logout() {
								document.getElementById("logoutForm").submit();
							}
						</script>
						<ul class="nav navbar-nav navbar-right hidden-xs">
							<li id="user-bar"><span class="user-bar-avatar pull-right"> <img src="<spring:url value="/resources/img/dashboard/user-normal.png" />" alt="User default avatar">
							</span> <a class="pull-right" id="username"> <c:set var="username">
										<sec:authentication property="principal.username" />
									</c:set> <c:out value="${username}" escapeXml="false" />
							</a> <span class="pull-right user-bar-icons"> <a href="javascript:logout()" title="<spring:message code="menu.logout" />"><i class="fa fa-sign-out" id="sign-out"></i></a> <a href="${root}/settings/" title="<spring:message code="menu.settings" />"><i class="fa fa-cog"></i></a>
							</span></li>
						</ul>
						<ul class="nav navbar-nav navbar-right visible-xs">
							<li><a href="${root}/settings/"><i class="fa fa-cog fa-1-4x" title="<spring:message code="menu.settings" />"></i>&nbsp;&nbsp;<spring:message code="menu.settings" /></a></li>
							<li><a href="javascript:logout()"><i class="fa fa-sign-out fa-1-4x" title="<spring:message code="menu.logout" />"></i>&nbsp; <spring:message code="menu.logout" /></a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul class="nav navbar-nav navbar-right hidden-xs">
							<li><div>
									<a class="btn btn-color btn-nav-sign" href="${root}/sign/"><spring:message code="menu.sign" /></a>
								</div></li>
						</ul>
						<ul class="nav navbar-nav navbar-right visible-xs">
							<li><div>
									<a class="btn btn-color btn-nav-sign-xs" href="${root}/sign/"><spring:message code="menu.sign" /></a>
								</div></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</div>
</div>
