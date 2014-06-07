<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<h3>
				<spring:message code="sign.up.activate.title" />
				<small><spring:message code="sign.up.activate.subtitle" /></small>
			</h3>
			<hr>
			<div class="col-sm-12">
				<spring:message code="sign.up.activate.description" arguments="${email}" />
				<br /> <br /> <br />
				<spring:message code="sign.up.activate.send.description" />
				<br /> <br /> <a class="btn-color ladda-button" href="${root}/user/activate/send?email=${email}"><spring:message code="sign.up.activate.send.button" /></a>
			</div>
		</div>
	</div>
</div>
