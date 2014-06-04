<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<h3>
				<spring:message code="developers.title" />
				<small><spring:message code="developers.subtitle" /></small>
			</h3>
			<hr>
			<c:if test="${not isAuthenticated}">
				<div class="alert alert-info">
					<spring:message code="developers.accountNeeded" />
				</div>
			</c:if>
			<spring:message code="developers.description" />
			<c:if test="${not isAuthenticated}">
				<br />
				<br />
				<a class="btn btn-color" href="${root}/sign-up"><spring:message code="welcome.action" /></a>
			</c:if>

		</div>
		<c:if test="${isAuthenticated}">
			<div class="row">
				<div class="col-sm-12">
					<br /> <br />
					<%@ include file="api.jspf"%>
				</div>
			</div>
		</c:if>
	</div>
</div>