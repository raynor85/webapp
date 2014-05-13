<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<h3>
				<spring:message code="sign.up.social.error.title" />
				<small><spring:message code="sign.up.social.error.subtitle" /></small>
			</h3>
			<hr>
			<div class="col-sm-12">
				<spring:message code="sign.up.social.error.description" arguments="${root}" />
			</div>
		</div>
	</div>
</div>
