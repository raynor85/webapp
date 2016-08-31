<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="applicationDescription" value="${applicationDescription}" />
<c:set var="isFollowingApplication" value="${isFollowingApplication}" />

<div class="container">
	<form:form id="followApplicationForm" commandName="followApplication" action="${root}/dashboard/follow-single" class="form-horizontal">
		<div id="followApplicationResponse"></div>

		<%@ include file="app-detail.jspf"%>
		<br />
		<div class="row rowWithPadding" align="center">
			<c:if test="${not isAuthenticated || isFollowingApplication}">
				<div class="button-ladda">
					<button type="button" class="btn-color ladda-button" onclick="location.href='${root}/applications';">
						<spring:message code="appslist.detail.showall.button" />
					</button>
				</div>
			</c:if>
			<c:if test="${isAuthenticated  && not isFollowingApplication}">
				<div class="col-xs-6">
					<button type="button" id="followApplicationButton" class="btn-color ladda-button" data-style="zoom-in" onclick="ajaxFollowApplication();">
						<spring:message code="appslist.detail.follow.button" />
					</button>
				</div>
				<div class="col-xs-6">
					<button type="button" id="showAllApplicationButton" class="btn-color ladda-button" onclick="location.href='${root}/applications';">
						<spring:message code="appslist.detail.showall.button" />
					</button>
				</div>
			</c:if>
		</div>
	</form:form>
</div>

<c:if test="${isAuthenticated && not isFollowingApplication}">
	<script type="text/javascript">
		function ajaxFollowApplication() {
			var json = {
				"apiName" : '${applicationDescription.application.apiName}'
			};
			ajaxCallPost("#followApplicationButton", "#followApplicationForm",
					json, "#followApplicationResponse", hideFollowButton);
		};
		var hideFollowButton = function() {
			$("#followApplicationButton").parent().hide();
			$("#showAllApplicationButton").parent().removeClass("col-xs-6");
		};
	</script>
</c:if>