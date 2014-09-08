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
			<c:if test="${not isAuthenticated && phase != 'early'}">
				<div class="alert alert-info">
					<spring:message code="developers.accountNeeded" />
				</div>
			</c:if>
			<spring:message code="developers.description" />
			<c:if test="${not isAuthenticated && phase != 'early'}">
				<br />
				<br />
				<br />
				<div class="col-sm-12 text-center">
					<a class="btn-color ladda-button" href="${root}/signup"><spring:message code="welcome.action" /></a>
				</div>
			</c:if>

		</div>
		<c:if test="${isAuthenticated}">
			<script src="<spring:url value="/resources/js/jquery.zclip.js" />"></script>
			<script src="<spring:url value="/resources/js/bootbox.min.js" />"></script>
			<script src="<spring:url value="/resources/js/json-format.min.js" />"></script>
			<div class="row rowWithPadding">
				<div class="col-sm-12">
					<br /> <br />
					<%@ include file="api.jspf"%>
				</div>
			</div>
		</c:if>
	</div>
</div>