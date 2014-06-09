<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="container">
	<div class="row">
		<c:if test="${isEmailDisabled && not isDashboardEmailDisableTipHidden}">
			<form:form id="helpMessageEmailDisableDismissMessageForm" commandName="dismissMessage" action="${root}/dashboard/dismiss">
				<div id="helpMessageEmailDisable" class="alert alert-warning pull-left">
					<spring:message code="dashboard.applications.tip.alert.email.disable" arguments="${root}" />
					<a href="javascript:ajaxDismissMessage('DASHBOARD_ALERT_DISABLED','helpMessageEmailDisable');"><spring:message code="dashboard.applications.tip.dismiss" /></a>
				</div>
			</form:form>
		</c:if>
		<c:set var="nbAppFollow">${fn:length(currentFollowApplications)}</c:set>
		<div class="alert alert-info pull-right">
			<c:choose>
				<c:when test="${nbAppFollow == 0}">
					<spring:message code="dashboard.application.status.nothing" />
				</c:when>
				<c:when test="${nbAppFollow == 1}">
					<spring:message code="dashboard.application.status.beginner" />
				</c:when>
				<c:when test="${nbAppFollow < 5}">
					<spring:message code="dashboard.application.status.normal" arguments="${nbAppFollow}" />
				</c:when>
				<c:when test="${nbAppFollow < 10}">
					<spring:message code="dashboard.application.status.experienced" arguments="${nbAppFollow}" />
				</c:when>
				<c:when test="${nbAppFollow < 15}">
					<spring:message code="dashboard.application.status.expert" arguments="${nbAppFollow}" />
				</c:when>
				<c:when test="${nbAppFollow > 15}">
					<spring:message code="dashboard.application.status.god" arguments="${nbAppFollow}" />
				</c:when>
			</c:choose>
		</div>
	</div>
	<div class="row">
		<form:form id="unfollowApplicationsForm" commandName="unfollowApplications" action="${root}/dashboard/unfollow">
			<c:forEach items="${currentFollowApplications}" var="currentFollowApplication">
				<c:set var="appName">${currentFollowApplication.applicationName}</c:set>
				<c:set var="appId">${currentFollowApplication.apiName}</c:set>
				<c:set var="appEmailActive">${currentFollowApplication.emailNotificationActive}</c:set>
				<c:set var="deleteTitle">
					<spring:message code="dashboard.applications.unfollow.title" /> ${appName}
				</c:set>
				<c:set var="disableTitle">
					<spring:message code="dashboard.applications.alert.disable.title" /> ${appName}
				</c:set>
				<c:set var="enableTitle">
					<spring:message code="dashboard.applications.alert.enable.title" /> ${appName}
				</c:set>
				<c:set var="downloadTitle">
					<spring:message code="dashboard.applications.download.title" /> ${appName} - ${currentFollowApplication.versionNumber}
				</c:set>
				<div id="div-current-${appId}" class="col-xs-6 col-sm-3 col-md-2 col-lg-2 currentFollowApplicationContainer">
					<button title="${deleteTitle}" aria-hidden="true" class="close pull-right" type="button" onclick="ajaxUnfollowCurrentApplication('${appId}');">
						<i class="fa fa-trash-o fa-1x"></i>
					</button>
					<c:choose>
						<c:when test="${isEmailDisabled}">
							<c:set var="disableStyle" value="display: none;" />
							<c:set var="enableStyle" value="display: none;" />
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${appEmailActive}">
									<c:set var="disableStyle" value="" />
									<c:set var="enableStyle" value="display: none;" />
								</c:when>
								<c:otherwise>
									<c:set var="disableStyle" value="display: none;" />
									<c:set var="enableStyle" value="" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<button id="button-current-disable-${appId}" title="${disableTitle}" aria-hidden="true" style="margin-left: -3px;${disableStyle}" class="close pull-left" type="button" onclick="ajaxDisableAlertCurrentApplication('${appId}');">
						<i class="fa fa-envelope-o fa-1x"></i>
					</button>
					<button id="button-current-enable-${appId}" title="${enableTitle}" aria-hidden="true" class="close pull-left" style="${enableStyle}" type="button" onclick="ajaxEnableAlertCurrentApplication('${appId}');">
						<i class="fa fa-ban fa-1x"></i>
					</button>
					<div class="application">
						<div class="icon">
							<a href="${currentFollowApplication.downloadUrl}" title="${downloadTitle}" target="_blank"><img class="shadowHover" src="<spring:url value="/resources/img/application/medium/${currentFollowApplication.iconFilename}" />" alt="${appName}"></a>
						</div>
						<div class="title">
							<a href="${currentFollowApplication.downloadUrl}" title="${downloadTitle}" target="_blank">${appName} [<strong>${currentFollowApplication.versionNumber}</strong>]</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</form:form>
	</div>
	<br /> <br />
	<c:if test="${nbAppFollow != 0 && not isDashboardHowToTipHidden}">
		<form:form id="helpMessageHowToDismissMessageForm" commandName="dismissMessage" action="${root}/dashboard/dismiss">
			<div id="helpMessageHowTo" class="alert alert-info pull-right">
				<spring:message code="dashboard.applications.tip.howto" />
				<a href="javascript:ajaxDismissMessage('DASHBOARD_HOW_TO','helpMessageHowTo');"><spring:message code="dashboard.applications.tip.dismiss" /></a>
			</div>
		</form:form>
	</c:if>
	<div class="row" align="center">
		<p class="button-ladda">
			<button type="button" class="btn-color ladda-button" data-toggle="modal" data-target="#followApplicationsModal">
				<spring:message code="dashboard.applications.follow.button" />
			</button>
		</p>
	</div>
</div>

<!-- Modal: Follow application -->
<div class="modal fade" id="followApplicationsModal" tabindex="-1" role="dialog" aria-labelledby="followApplicationsModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form:form id="newollowApplicationsForm" commandName="newFollowApplications" action="${root}/dashboard/follow">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="followApplicationsModalLabel">
						<spring:message code="dashboard.applications.followApplications.title" />
					</h4>
				</div>
				<div class="modal-body">
					<c:choose>
						<c:when test="${fn:length(leftApplications) == 0}">
							<spring:message code="dashboard.applications.followApplications.empty" />
						</c:when>
						<c:otherwise>
							<div class="alert alert-info">
								<c:choose>
									<c:when test="${fn:length(currentFollowApplications) == 0}">
										<spring:message code="dashboard.applications.followApplications.first.description" />
									</c:when>
									<c:otherwise>
										<spring:message code="dashboard.applications.followApplications.description" />

									</c:otherwise>
								</c:choose>
							</div>
						</c:otherwise>
					</c:choose>

					<div class="row">
						<c:forEach items="${leftApplications}" var="leftApplication" varStatus="i">
							<c:set var="appName">${leftApplication.name}</c:set>
							<c:set var="appId">${leftApplication.apiName}</c:set>
							<div class="col-xs-4 col-sm-3 col-md-3 col-lg-2 newFollowApplicationContainer">
								<div id="div-new-${appId}" class="application">
									<div class="icon" onclick="followNewApplication('${appId}');">
										<img src="<spring:url value="/resources/img/application/small/${leftApplication.iconFilename}" />" alt="${appName}">
									</div>
									<div class="title" onclick="followNewApplication('${appId}');">${appName}</div>
								</div>
							</div>
							<form:checkbox path="apiNames[${i.index}]" id="app-${appId}" value="${appId}" cssClass="hidden" />
						</c:forEach>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-left btn-cancel-next-ladda" data-dismiss="modal">
						<spring:message code="dashboard.applications.followApplications.button.cancel" />
					</button>
					<c:if test="${fn:length(leftApplications) != 0}">
						<button type="submit" class="btn-color ladda-button push-to-bottom">
							<spring:message code="dashboard.applications.followApplications.button.confirm" />
						</button>
					</c:if>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	var showModalIfNoFollowedApps = function() {
		if ("${nbAppFollow}" == 0) {
			$("#followApplicationsModal").modal("show");
		}
	}();
	function followNewApplication(appId) {
		var appCheckbox = $("#app-" + appId);
		var appDiv = $("#div-new-" + appId);
		if (appCheckbox.is(':checked')) {
			appDiv.removeClass("application-selected");
			appCheckbox.prop('checked', false);
		} else {
			appDiv.addClass("application-selected");
			appCheckbox.prop('checked', true);
		}
	};
	function ajaxUnfollowCurrentApplication(appId) {
		$("#div-current-" + appId).fadeOut();
		ajaxCallPost(null, "#unfollowApplicationsForm", {
			"apiName" : appId
		}, null, refreshPage);
	};
	var refreshPage = function() {
		setTimeout(function() {
			location.reload();
		}, 1000);
	};
	function ajaxDisableAlertCurrentApplication(appId) {
		$("#button-current-disable-" + appId).hide();
		$("#button-current-enable-" + appId).show();
		ajaxCallPostWithUrl(null, "${root}/dashboard/disable", {
			"apiName" : appId
		}, null);
	}
	function ajaxEnableAlertCurrentApplication(appId) {
		$("#button-current-enable-" + appId).hide();
		$("#button-current-disable-" + appId).show();
		ajaxCallPostWithUrl(null, "${root}/dashboard/enable", {
			"apiName" : appId
		}, null);
	}
	function ajaxDismissMessage(typeHelpMessage, elementId) {
		$("#" + elementId).fadeOut();
		ajaxCallPost(null, "#" + elementId + "DismissMessageForm", {
			"typeHelpMessage" : typeHelpMessage
		}, null);
	};
</script>
