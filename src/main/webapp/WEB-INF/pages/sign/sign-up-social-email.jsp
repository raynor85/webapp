<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<div class="row rowWithPadding">
		<div class="col-sm-12">
			<h3>
				<spring:message code="sign.up.social.email.title" />
			</h3>
			<spring:message code="sign.up.social.email.description" />
			<br />
			<br />
		</div>
	</div>
	<div class="row rowWithPadding">
		<div class="center-block" style="max-width: 450px">
			<div class="form-white-sign">
				<form:form id="registerSocialEmailUserForm" commandName="registerSocialEmailUser" action="${root}/user/register/social" method="POST">
					<div class="form-group">
						<label for="email"><spring:message code="sign.up.social.email.field.email" /></label>
						<c:set var="emailPlaceholder">
							<spring:message code="sign.up.social.email.field.email.tip" />
						</c:set>
						<div class="input-group">
							<span class="input-group-addon">@</span>
							<form:input type="email" path="email" id="email" class="form-control" placeholder="${emailPlaceholder}" />
						</div>
					</div>
					<button type="submit" class="btn-block btn-color ladda-button">
						<spring:message code="sign.up.social.email.button" />
					</button>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="hidden" name="key" value="${key}" />
					<spring:hasBindErrors name="registerSocialEmailUser">
						<br />
						<div class="alert alert-danger alert-dismissable" role="alert">
							<i class="fa fa-exclamation-circle"></i> <span class="sr-only">Error:</span>
							<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							<form:errors path="*" htmlEscape="false" />
						</div>
					</spring:hasBindErrors>
				</form:form>
			</div>

		</div>
	</div>
</div>
