<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- Banner -->
<div class="crp-showcase">
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<h1 class="text-center animated2 fadeInDown">
					<span class="logo logobig"><spring:message code="application.name" /></span>
					<spring:message code="application.tagline.short" />
				</h1>
				<h3 class="text-center animated2 fadeInDown shadow">
					<spring:message code="welcome.introduction" />
				</h3>
				<sec:authorize access="isAnonymous()">
					<div class="text-center actions animated2 fadeInDown delay2">
						<c:choose>
							<c:when test="${phase == 'early'}">
								<a class="btn btn-color" href="#early-user"><spring:message code="welcome.action.early" /></a>
							</c:when>
							<c:otherwise>
								<a class="btn btn-color" href="${root}/sign-up/"><spring:message code="welcome.action" /></a>
							</c:otherwise>
						</c:choose>

					</div>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<div>
						<div>
							<h2 class="text-center animated2 fadeInDown shadow delay4">
								<spring:message code="welcome.thanks" />
							</h2>
						</div>
						<div id="arrowShare" class="animated2 wobble delay10"></div>
					</div>
				</sec:authorize>
				<div class="alt-index">
					<img src="<spring:url value="/resources/img/welcome/updapy-preview.jpg" />" alt="Updapy Preview">
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Carousel -->
<div id="carousel-updapy" class="carousel slide" data-ride="carousel">
	<!-- Indicators -->
	<ol class="carousel-indicators">
		<li data-target="#carousel-updapy" data-slide-to="0" class="active"></li>
		<li data-target="#carousel-updapy" data-slide-to="1"></li>
		<li data-target="#carousel-updapy" data-slide-to="2"></li>
		<li data-target="#carousel-updapy" data-slide-to="3"></li>
		<li data-target="#carousel-updapy" data-slide-to="4"></li>
	</ol>
	<!-- Wrapper for slides -->
	<div class="carousel-inner">
		<div class="item active">
			<img src="<spring:url value="/resources/img/welcome/carousel-time.png" />" alt="Time">
			<div class="carousel-caption">
				<h4>
					<spring:message code="welcome.carousel.time.title" />
				</h4>
				<p>
					<spring:message code="welcome.carousel.time.description" />
				</p>
			</div>
		</div>
		<div class="item">
			<img src="<spring:url value="/resources/img/welcome/carousel-custom.png" />" alt="Custom">
			<div class="carousel-caption">
				<h4>
					<spring:message code="welcome.carousel.custom.title" />
				</h4>
				<p>
					<spring:message code="welcome.carousel.custom.description" />
				</p>
			</div>
		</div>
		<div class="item">
			<img src="<spring:url value="/resources/img/welcome/carousel-security.png" />" alt="Security">
			<div class="carousel-caption">
				<h4>
					<spring:message code="welcome.carousel.security.title" />
				</h4>
				<p>
					<spring:message code="welcome.carousel.security.description" />
				</p>
			</div>
		</div>
		<div class="item">
			<img src="<spring:url value="/resources/img/welcome/carousel-trust.png" />" alt="Trust">
			<div class="carousel-caption">
				<h4>
					<spring:message code="welcome.carousel.trust.title" />
				</h4>
				<p>
					<spring:message code="welcome.carousel.trust.description" />
				</p>
			</div>
		</div>
		<div class="item">
			<img src="<spring:url value="/resources/img/welcome/carousel-free.png" />" alt="Free">
			<div class="carousel-caption">
				<h4>
					<spring:message code="welcome.carousel.free.title" />
				</h4>
				<p>
					<spring:message code="welcome.carousel.free.description" />
				</p>
			</div>
		</div>
	</div>
	<!-- Controls -->
	<a class="left carousel-control" href="#carousel-updapy" data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"></span>
	</a> <a class="right carousel-control" href="#carousel-updapy" data-slide="next"> <span class="glyphicon glyphicon-chevron-right"></span>
	</a>
</div>

<div style="height: 50px;">
	<hr>
</div>

<div class="container">
	<!-- Features -->
	<div class="row">
		<div class="col-sm-4">
			<div class="crp-ft">
				<i class="text-color fa fa-bookmark fa-6x"></i>
				<h4>
					<spring:message code="welcome.follow.title" />
				</h4>
				<p class="text-muted lh">
					<spring:message code="welcome.follow.description" />
				</p>
			</div>
		</div>
		<div class="col-sm-4">
			<div class="crp-ft">
				<i class="text-color fa fa-bell fa-6x"></i>
				<h4>
					<spring:message code="welcome.notify.title" />
				</h4>
				<p class="text-muted lh">
					<spring:message code="welcome.notify.description" />
				</p>
			</div>
		</div>
		<div class="col-sm-4">
			<div class="crp-ft">
				<i class="text-color fa fa-download fa-6x"></i>
				<h4>
					<spring:message code="welcome.download.title" />
				</h4>
				<p class="text-muted lh">
					<spring:message code="welcome.download.description" />
				</p>
			</div>
		</div>
	</div>

	<c:if test="${phase == 'early'}">
		<!-- Early user -->
		<a id="early-user"> </a>
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2">
				<h2 class="oswald text-center">
					<spring:message code="early.interest.title" />
				</h2>
				<hr>
			</div>
		</div>
		<div class="row crp-desc">
			<div class="col-sm-5 col-sm-offset-1">
				<img src="<spring:url value="/resources/img/welcome/macbook-first.jpg" />" alt="Updapy Newsletter" class="img-responsive">
			</div>
			<div class="col-sm-5">
				<h3 class="text-color text-center-xs">
					<spring:message code="early.interest.subtitle" />
				</h3>
				<p class="text-muted text-center-xs lh">
					<spring:message code="early.interest.description" />
				</p>
				<form:form id="registerEarlyUserForm" commandName="registerEarlyUser" action="${root}/user/register-early" class="form-vertical">
					<p>
						<c:set var="emailPlaceholder">
							<spring:message code="early.interest.add.field.email.tip" />
						</c:set>
						<form:input path="email" id="email" class="form-control" placeholder="${emailPlaceholder}" />
					</p>
					<p class="text-center-xs button-ladda">
						<button type="button" class="btn-color ladda-button" data-style="zoom-in" onclick="ajaxRegisterEarlyUser();">
							<spring:message code="early.interest.add.button" />
						</button>
					</p>
					<div id="registerEarlyUserResponse"></div>
				</form:form>
			</div>
		</div>
	</c:if>
</div>

<c:if test="${phase == 'early'}">
	<script type="text/javascript">
		function ajaxRegisterEarlyUser() {
			var json = {
				"email" : $("#email").val()
			};
			ajaxCall("#registerEarlyUserForm", json,
					"#registerEarlyUserResponse");
		};
		$("#registerEarlyUserForm").submit(function() {
			ajaxRegisterEarlyUser();
			return false;
		});
	</script>
</c:if>

