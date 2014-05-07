<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<h3>
				<spring:message code="sign.up.activate.error.link.title" />
				<small><spring:message code="sign.up.activate.error.link.subtitle" /></small>
			</h3>
			<hr>
			<div class="col-sm-12">
				<spring:message code="sign.up.activate.error.link.description" arguments="${root}" />
			</div>
		</div>
	</div>
</div>
