<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
				<h4 class="text-center animated2 fadeInDown shadow delay3" style="margin-top: 30px !important;">
					<spring:message code="welcome.applications.text" arguments="${numberOfApplicationsActive}" />
				</h4>
				<div class="h3 text-center animated2 fadeInDown delay3">
					<spring:message code="welcome.applications.button" arguments="${root}" />
				</div>
				<c:if test="${not isAuthenticated}">
					<div class="text-center actions animated2 fadeInUp delay7">
						<a class="btn btn-color ladda-button" href="${root}/signup"><spring:message code="welcome.action" /></a>
					</div>
				</c:if>
				<c:if test="${isAuthenticated}">
					<div>
						<div>
							<h2 class="text-center animated2 fadeInUp shadow delay7">
								<spring:message code="welcome.thanks" />
							</h2>
						</div>
						<div id="arrowShare" class="animated2 wobble delay10"></div>
					</div>
				</c:if>
				<div class="alt-index">
					<img width="710" height="319" src="<spring:url value="/resources/img/welcome/updapy-preview.png" />" alt="Updapy Preview">
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
			<img width="779" height="387" src="<spring:url value="/resources/img/welcome/carousel-time.png" />">
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
			<img width="779" height="387" src="<spring:url value="/resources/img/welcome/carousel-custom.png" />">
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
			<img width="779" height="387" src="<spring:url value="/resources/img/welcome/carousel-security.png" />">
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
			<img width="779" height="387" src="<spring:url value="/resources/img/welcome/carousel-trust.png" />">
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
			<img width="779" height="387" src="<spring:url value="/resources/img/welcome/carousel-free.png" />">
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
</div>
