<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
	<div class="row">
		<!-- Sign Up form -->
		<div class="center-block" style="max-width: 600px">
			<h3>
				<spring:message code="sign.up.title" />
			</h3>
			<p class="text-muted">
				<spring:message code="sign.up.subtitle" />
			</p>
			<%@ include file="sign-up.jspf"%>
			<h3>
				<spring:message code="sign.social.title" />
			</h3>
			<p class="text-muted">
				<spring:message code="sign.social.subtitle" />
			</p>
			<%@ include file="sign-social.jspf"%>
		</div>
	</div>
</div>
