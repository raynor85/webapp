<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<div class="row">
		<div class="center-block" style="max-width: 350px">
			<h3>
				<spring:message code="sign.in.reset.title" />
			</h3>
			<p class="text-muted">
				<spring:message code="sign.in.reset.subtitle" />
			</p>
			<spring:message code="sign.in.reset.description" />
			<br />
			<br />
			<div class="form-white-sign">
				<form:form id="resetPasswordForm" commandName="resetUser" action="${root}/user/reset" method="POST">
					<div class="form-group">
						<label for="newPassword"><spring:message code="sign.in.reset.field.password" /></label>
						<c:set var="passwordPlaceholder">
							<spring:message code="sign.in.reset.field.password.tip" />
						</c:set>
						<form:input type="password" path="newPassword" id="newPassword" class="form-control" placeholder="${passwordPlaceholder}" />
					</div>
					<div class="form-group">
						<label for="repeatNewPassword"><spring:message code="sign.in.reset.field.password.repeat" /></label>
						<c:set var="passwordRepeatPlaceholder">
							<spring:message code="sign.in.reset.field.password.repeat.tip" />
						</c:set>
						<form:input type="password" path="repeatNewPassword" id="repeatNewPassword" class="form-control" placeholder="${passwordRepeatPlaceholder}" />
					</div>
					<button type="submit" class="btn btn-block btn-color btn-xxl">
						<spring:message code="sign.in.reset.button" />
					</button>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<form:hidden path="email" id="email" />
					<form:hidden path="key" id="key" />
					<spring:hasBindErrors name="resetUser">
						<br />
						<div class="alert alert-danger alert-dismissable">
							<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							<form:errors path="*" htmlEscape="false" />
						</div>
					</spring:hasBindErrors>
				</form:form>
			</div>

		</div>
	</div>
</div>
