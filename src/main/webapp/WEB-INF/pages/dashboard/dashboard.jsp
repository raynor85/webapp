<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="container">
	<div class="row rowWithPadding">
		<div id="successRequestApplicationResponse"></div>
		<c:if test="${nbAppFollow != 0 && not isDashboardHowToTipHidden}">
			<form:form id="helpMessageHowToDismissMessageForm" commandName="dismissMessage" action="${root}/dashboard/dismiss">
				<div id="helpMessageHowTo" class="alert alert-info pull-right-lg">
					<spring:message code="dashboard.applications.tip.howto" />
					<a class="alert-link" href="javascript:ajaxDismissMessage('DASHBOARD_HOW_TO','helpMessageHowTo');"><spring:message code="dashboard.applications.tip.dismiss" /></a>
				</div>
			</form:form>
		</c:if>
		<c:if test="${isEmailDisabled && not isDashboardEmailDisableTipHidden}">
			<form:form id="helpMessageEmailDisableDismissMessageForm" commandName="dismissMessage" action="${root}/dashboard/dismiss">
				<div id="helpMessageEmailDisable" class="alert alert-warning pull-left">
					<spring:message code="dashboard.applications.tip.alert.email.disable" arguments="${root}" />
					<a class="alert-link" href="javascript:ajaxDismissMessage('DASHBOARD_ALERT_DISABLED','helpMessageEmailDisable');"><spring:message code="dashboard.applications.tip.dismiss" /></a>
				</div>
			</form:form>
		</c:if>
		<c:set var="nbAppFollow">${fn:length(currentFollowedApplications)}</c:set>
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
				<c:when test="${nbAppFollow < 15}">
					<spring:message code="dashboard.application.status.experienced" arguments="${nbAppFollow}" />
				</c:when>
				<c:when test="${nbAppFollow <= 30}">
					<spring:message code="dashboard.application.status.expert" arguments="${nbAppFollow}" />
				</c:when>
				<c:when test="${nbAppFollow > 30}">
					<spring:message code="dashboard.application.status.god" arguments="${nbAppFollow}" />
				</c:when>
			</c:choose>
		</div>
	</div>
	<div class="row rowWithPadding">
		<form:form id="unfollowApplicationsForm" commandName="unfollowApplications" action="${root}/dashboard/unfollow">
			<c:forEach items="${currentFollowedApplications}" var="currentFollowedApplication">
				<c:set var="appName">${currentFollowedApplication.applicationName}</c:set>
				<c:set var="appId">${currentFollowedApplication.apiName}</c:set>
				<c:set var="appEmailActive">${currentFollowedApplication.emailNotificationActive}</c:set>
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
					<spring:message code="dashboard.applications.download.title" /> ${appName} - ${currentFollowedApplication.versionNumber}
				</c:set>
				<div id="div-current-${appId}" class="col-xs-6 col-sm-3 col-md-2 col-lg-2 currentFollowedApplicationContainer">
					<button title="${deleteTitle}" aria-hidden="true" class="close pull-right" type="button" onclick="ajaxUnfollowCurrentApplication('${appId}');">
						<i class="fa fa-trash-o fa-1x"></i>
					</button>
					<c:choose>
						<c:when test="${isEmailOnEachUpdateDisabled}">
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
						<i class="fa fa-envelope-o fa-1x" style="color: green;"></i>
					</button>
					<button id="button-current-enable-${appId}" title="${enableTitle}" aria-hidden="true" class="close pull-left" style="${enableStyle}" type="button" onclick="ajaxEnableAlertCurrentApplication('${appId}');">
						<i class="fa fa-ban fa-1x" style="color: red;"></i>
					</button>
					<div class="application">
						<div class="icon">
							<a href="${currentFollowedApplication.downloadUrl}" title="${downloadTitle}" target="_blank"><img class="shadowHover" src="<spring:url value="/resources/img/application/medium/${currentFollowedApplication.iconFilename}" />" alt="${appName}"></a>
						</div>
						<div class="title">
							<h4>
								<span class="label label-success">${appName}</span>
							</h4>
							<span class="label label-danger">${currentFollowedApplication.versionNumber}</span>
						</div>
					</div>
				</div>
			</c:forEach>
		</form:form>
	</div>
	<br />
	<div class="row rowWithPadding" align="center">
		<div class="button-ladda">
			<button type="button" class="btn-color ladda-button" data-toggle="modal" data-target="#followApplicationsModal">
				<spring:message code="dashboard.applications.follow.button" />
			</button>
		</div>
		<div class="button-ladda" id="requestApplicationLink">
			<a href="#" data-toggle="modal" data-target="#requestApplicationModal"><spring:message code="dashboard.applications.request.link" /></a>
		</div>
	</div>
</div>

<!-- Modal: Follow application -->
<div class="modal fade" id="followApplicationsModal" tabindex="-1" role="dialog" aria-labelledby="followApplicationsModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form:form id="newFollowedApplicationsForm" commandName="newFollowedApplications" action="${root}/dashboard/follow">
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
									<c:when test="${fn:length(currentFollowedApplications) == 0}">
										<spring:message code="dashboard.applications.followApplications.first.description" />
									</c:when>
									<c:otherwise>
										<spring:message code="dashboard.applications.followApplications.description" />
									</c:otherwise>
								</c:choose>
							</div>
							<div>
								<h3>
									<small><spring:message code="dashboard.applications.followApplications.latest.title" /></small>
								</h3>
							</div>
							<div class="row rowWithPadding" style="margin-bottom: 25px;">
								<c:forEach items="${latestApplications}" var="latestApplication">
									<div class="col-xs-6 col-md-3 col-lg-3">
										<i class="text-color fa fa-chevron-circle-right fa-1x"></i>&nbsp;${latestApplication.name}
									</div>
								</c:forEach>
							</div>
							<c:set var="filterPlaceholder">
								<spring:message code="dashboard.applications.followApplications.filter.description" />
							</c:set>
							<div class="inner-addon left-addon">
								<i class="fa fa-search"></i> <input id="filter" type="search" class="form-control filter" placeholder="${filterPlaceholder}">
							</div>
							<div id="filter-count"></div>
						</c:otherwise>
					</c:choose>
					<div id="appsGrid" class="row">
						<c:forEach items="${leftApplications}" var="leftApplication" varStatus="i">
							<c:set var="appName">${leftApplication.name}</c:set>
							<c:set var="appId">${leftApplication.apiName}</c:set>
							<div class="col-xs-4 col-sm-3 col-md-3 col-lg-2 newFollowApplicationContainer" onclick="followNewApplication('${appId}');">
								<div id="div-new-${appId}" class="application" title="${appName}">
									<div class="icon">
										<img src="<spring:url value="/resources/img/application/small/${leftApplication.iconFilename}" />" alt="${appName}">
									</div>
									<div class="title">
										<span class="label label-success" style="display: inline-block !important; max-width: 100px !important; white-space: normal !important;">${appName}</span>
									</div>
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
							(<span id="counterNewApp">0</span>)
						</button>
					</c:if>
				</div>
			</form:form>
		</div>
	</div>
</div>

<!-- Modal: Request missing app -->
<div class="modal fade" id="requestApplicationModal" tabindex="-1" role="dialog" aria-labelledby="requestApplicationModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form:form id="requestApplicationForm" commandName="requestApplication" action="${root}/dashboard/request">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="requestApplicationModalLabel">
						<spring:message code="dashboard.applications.request.title" />
					</h4>
				</div>
				<div class="modal-body">
					<div id="requestApplicationResponse"></div>
					<spring:message code="dashboard.applications.request.description" />
					<br /> <br />
					<div class="form-group">
						<c:set var="requestedAppNamePlaceholder">
							<spring:message code="dashboard.applications.request.field.name.tip" />
						</c:set>
						<label for="requestedAppName"><spring:message code="dashboard.applications.request.field.name" /></label>
						<form:input path="name" class="form-control" id="requestedAppName" placeholder="${requestedAppNamePlaceholder}" />
					</div>
					<div class="form-group">
						<label for="requestedAppUrl"><spring:message code="dashboard.applications.request.field.url" /></label>
						<c:set var="requestedAppUrlPlaceholder">
							<spring:message code="dashboard.applications.request.field.url.tip" />
						</c:set>
						<form:input path="url" class="form-control" id="requestedAppUrl" placeholder="${requestedAppUrlPlaceholder}" />
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-left btn-cancel-next-ladda" data-dismiss="modal">
						<spring:message code="dashboard.applications.request.button.cancel" />
					</button>
					<button type="button" id="requestApplicationButton" class="btn-color ladda-button" data-style="zoom-in" onclick="ajaxRequestApplication();">
						<spring:message code="dashboard.applications.request.button.confirm" />
					</button>
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
		var currentCounterValue = parseInt($("#counterNewApp").text());
		if (appCheckbox.is(':checked')) {
			appDiv.removeClass("application-selected");
			appCheckbox.prop('checked', false);
			$("#counterNewApp").text(currentCounterValue - 1);
		} else {
			appDiv.addClass("application-selected");
			appCheckbox.prop('checked', true);
			$("#counterNewApp").text(currentCounterValue + 1);
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

	function ajaxRequestApplication() {
		var json = {
			"name" : $("#requestedAppName").val(),
			"url" : $("#requestedAppUrl").val(),
		};
		ajaxCallPost("#requestApplicationButton", "#requestApplicationForm",
				json, "#requestApplicationResponse", confirmRequestApplication,
				true);
	};
	$("#requestApplicationForm").submit(function() {
		ajaxRequestApplication();
		return false;
	});
	var confirmRequestApplication = function() {
		// close modal
		$("#requestApplicationModal").modal("hide");
		// empty fields
		$("#requestedAppName").val("");
		$("#requestedAppUrl").val("");
		// display success
		$("#successRequestApplicationResponse").html(
				$("#requestApplicationResponse").html());
	};

	$("#filter").keydown(function() {
		setTimeout(function() {
			filterApps($("#filter").val());
		}, 500); // timeout needed to let the time for the field to be populated
	});

	function filterApps(filter) {
		// retrieve the input field text and reset the count to zero
		var count = 0;
		// loop through the app list
		$("#appsGrid div.newFollowApplicationContainer").each(
				function() {
					// if the name of the glossary term does not contain the text phrase fade it out
					if (jQuery(this).find("span.label-success").text().search(
							new RegExp(filter, "i")) < 0) {
						$(this).fadeOut();

						// show the list item if the phrase matches and increase the count by 1
					} else {
						$(this).show();
						count++;
					}

				});

		// update the count
		var app = " application";
		if (count >= 2) {
			app += "s";
		}
		var found = "<spring:message code='dashboard.applications.followApplications.filter.found' />";
		$("#filter-count").html(
				"<small>" + count + app + " " + found + "</small>");
	}
</script>
