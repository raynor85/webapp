<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="com.updapy.model.enumeration.ApplicationType"%>
<%@ page import="com.updapy.model.enumeration.DashboardGridSize"%>

<c:set var="COMMERCIAL" value="<%=ApplicationType.COMMERCIAL%>" />
<c:set var="SMALL" value="<%=DashboardGridSize.SMALL%>" />
<c:set var="nbAppFollow">${fn:length(currentFollowedApplications)}</c:set>

<c:choose>
	<c:when test="${dashboardGridSize == SMALL}">
		<c:set var="gridSize" value="small" />
		<c:set var="iconSize" value="64" />
		<c:set var="rowButtonStyle" value="padding-bottom: 30px;" />
	</c:when>
	<c:otherwise>
		<c:set var="gridSize" value="medium" />
		<c:set var="iconSize" value="128" />
		<c:set var="rowButtonStyle" value="padding-bottom: 10px;" />
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${showRating}">
		<c:set var="deleteButtonClass" value="pull-left" />
	</c:when>
	<c:otherwise>
		<c:set var="deleteButtonClass" value="pull-right" />
	</c:otherwise>
</c:choose>

<div class="container">
	<div class="row rowWithPadding">
		<div id="successRequestApplicationResponse"></div>
		<c:if test="${nbAppFollow != 0 && not isDashboardHowToTipHidden}">
			<form:form id="helpMessageHowToDismissMessageForm" commandName="dismissMessage" action="${root}/dashboard/dismiss">
				<div id="helpMessageHowTo" class="alert alert-info pull-right-lg" role="alert">
					<spring:message code="dashboard.applications.tip.howto.part1" />
					<c:if test="${not isEmailDisabled}">
						<spring:message code="dashboard.applications.tip.howto.part2" />
					</c:if>
					<c:if test="${showRating}">
						<spring:message code="dashboard.applications.tip.howto.part3" />
					</c:if>
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
		<div class="alert alert-info pull-right" role="alert">
			<i class="fa fa-info-circle"></i>
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
	<c:if test="${nbAppFollow > 36}">
		<div class="row rowWithPadding" align="center" style="${rowButtonStyle}">
			<div class="button-ladda">
				<button type="button" class="btn-color ladda-button" data-toggle="modal" data-target="#followApplicationsModal">
					<spring:message code="dashboard.applications.follow.button" />
				</button>
			</div>
		</div>
	</c:if>
	<div class="row rowWithPadding rowApps-center-xs">
		<form:form id="unfollowApplicationsForm" commandName="unfollowApplications" action="${root}/dashboard/unfollow">
			<c:forEach items="${currentFollowedApplications}" var="currentFollowedApplication">
				<c:set var="appName">${currentFollowedApplication.applicationName}</c:set>
				<c:set var="appId">${currentFollowedApplication.apiName}</c:set>
				<c:set var="appEmailActive">${currentFollowedApplication.emailNotificationActive}</c:set>
				<c:set var="deleteTitle">
					<spring:message code="dashboard.applications.unfollow.title" arguments="${appName}" />
				</c:set>
				<c:set var="disableTitle">
					<spring:message code="dashboard.applications.alert.disable.title" arguments="${appName}" />
				</c:set>
				<c:set var="enableTitle">
					<spring:message code="dashboard.applications.alert.enable.title" arguments="${appName}" />
				</c:set>
				<c:set var="downloadTitle">
					<spring:message code="dashboard.applications.download.title" arguments="${appName},${currentFollowedApplication.versionNumber}" />
				</c:set>
				<div id="div-current-${appId}" class="col-xs-4 col-sm-3 col-md-2 col-lg-2 currentFollowedApplicationContainer">
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
					<div style="height: 25px">
						<button id="button-current-disable-${appId}" title="${disableTitle}" aria-hidden="true" style="margin-left: -3px;${disableStyle}" class="close pull-left" type="button" onclick="ajaxDisableAlertCurrentApplication('${appId}');">
							<i class="fa fa-envelope-o fa-1x" style="color: green;"></i>
						</button>
						<button id="button-current-enable-${appId}" title="${enableTitle}" aria-hidden="true" style="${enableStyle}" class="close pull-left" type="button" onclick="ajaxEnableAlertCurrentApplication('${appId}');">
							<i class="fa fa-ban fa-1x" style="color: red;"></i>
						</button>
						<button title="${deleteTitle}" aria-hidden="true" class="close ${deleteButtonClass}" type="button" style="margin-left: 10px;" onclick="ajaxUnfollowCurrentApplication('${appId}');">
							<i class="fa fa-trash-o fa-1x"></i>
						</button>
						<c:if test="${showRating}">
							<input type="number" id="rating-${appId}" value="${currentFollowedApplication.rating}" class="rating" onchange="ajaxRateApplication('${appId}');" style="visibility: hidden;"/>
						</c:if>
					</div>
					<div class="application ${gridSize}-icon">
						<div class="icon">
							<c:set var="descriptionCaptionTitle">
								<spring:message code="dashboard.applications.caption.description.title" />
							</c:set>
							<c:set var="websiteCaptionTitle">
								<spring:message code="dashboard.applications.caption.website.title" />
							</c:set>
							<figure class="caption-figure" itemscope="itemscope" itemtype="http://schema.org/Photograph">
								<a href="${currentFollowedApplication.downloadUrl}" title="${downloadTitle}" target="_blank"><img width="${iconSize}" height="${iconSize}" id="img-${appId}" class="shadowHover caption" src="<spring:url value="/resources/img/application/${gridSize}/${currentFollowedApplication.iconFilename}" />" itemprop="image" alt="${appName}"></a>
								<figcaption class="figcaption-${gridSize}" itemprop="name">
									<c:choose>
										<c:when test="${gridSize == 'small'}">
											<c:set var="ratingCaptionTitle"><c:out value="${currentFollowedApplication.averageRating.scoreRounded}" /> <c:choose><c:when test="${currentFollowedApplication.averageRating.nbVotes > 1}"><spring:message code="appslist.detail.votes" arguments="${currentFollowedApplication.averageRating.nbVotes}" /></c:when><c:otherwise><spring:message code="appslist.detail.vote" arguments="${currentFollowedApplication.averageRating.nbVotes}" /></c:otherwise></c:choose></c:set>
											<div data-toggle="tooltip" title="${ratingCaptionTitle}">
												<c:choose>
													<c:when test="${currentFollowedApplication.averageRating.nbVotes > 0}">
														<i class="fa fa-star" style="cursor: pointer;"></i>
													</c:when>
													<c:otherwise>
														<i class="fa fa-star-o" style="cursor: pointer;"></i>
													</c:otherwise>
												</c:choose>
											</div>
											<a class="caption" href="${root}/applications/${appId}" title="${descriptionCaptionTitle}"><i class="fa fa-book"></i></a>
											<br><a class="caption" href="${currentFollowedApplication.websiteUrl}" target="_blank" title="${websiteCaptionTitle}"><i class="fa fa-home"></i></a>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${currentFollowedApplication.averageRating.nbVotes > 0}">
													<i class="fa fa-star"></i>
												</c:when>
												<c:otherwise>
													<i class="fa fa-star-o"></i>
												</c:otherwise>
											</c:choose>
											<c:if test="${not empty currentFollowedApplication.averageRating.scoreRounded}">
												<c:out value="${currentFollowedApplication.averageRating.scoreRounded}" />
											</c:if>
											<c:choose>
												<c:when test="${currentFollowedApplication.averageRating.nbVotes > 1}">
													<spring:message code="appslist.detail.votes" arguments="${currentFollowedApplication.averageRating.nbVotes}" />
												</c:when>
												<c:otherwise>
													<spring:message code="appslist.detail.vote" arguments="${currentFollowedApplication.averageRating.nbVotes}" />
												</c:otherwise>
											</c:choose>
											<br><i class="fa fa-book"></i>&nbsp;<a class="caption" href="${root}/applications/${appId}" title="${descriptionCaptionTitle}"><spring:message code="dashboard.applications.caption.description" /></a>
											<br><i class="fa fa-home"></i>&nbsp;<a class="caption" href="${currentFollowedApplication.websiteUrl}" target="_blank" title="${websiteCaptionTitle}"><spring:message code="dashboard.applications.caption.website" /></a>
										</c:otherwise>
									</c:choose>
								</figcaption>
							</figure>
						</div>
						<div class="title">
							<h4 style="margin-bottom: 0px !important; height: 100% !important;">
								<span class="label label-success" style="display: inline-block !important; white-space: normal !important;">${appName}</span>
							</h4>
							<span class="label label-danger" style="display: inline-block !important;">${currentFollowedApplication.versionNumber}</span>
						</div>
					</div>
				</div>
			</c:forEach>
		</form:form>
	</div>
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
				<c:choose>
					<c:when test="${fn:length(leftApplications) > 0}">
						<c:set var="styleModalHeader" value="style='box-shadow: 0 7px 10px rgba(182, 182, 182, 0.30);'" />
						<c:set var="styleAppsGrid" value="style='height: 400px; overflow-y: auto;'" />
					</c:when>
					<c:otherwise>
						<c:set var="styleModalHeader" value="style='border-bottom: 0px !important;'" />
					</c:otherwise>
				</c:choose>
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="followApplicationsModalLabel">
						<spring:message code="dashboard.applications.followApplications.title" />
					</h4>
				</div>
				<div class="modal-header" ${styleModalHeader}>
					<c:choose>
						<c:when test="${fn:length(leftApplications) == 0}">
							<spring:message code="dashboard.applications.followApplications.empty" />
						</c:when>
						<c:otherwise>
							<div class="alert alert-info" role="alert">
								<i class="fa fa-info-circle"></i>
								<span class="sr-only">Info:</span>
								<c:choose>
									<c:when test="${fn:length(currentFollowedApplications) == 0}">
										<spring:message code="dashboard.applications.followApplications.first.description" />
									</c:when>
									<c:otherwise>
										<spring:message code="dashboard.applications.followApplications.description" />
									</c:otherwise>
								</c:choose>
							</div>
							<fieldset>
								<legend class="legend">
									<spring:message code="dashboard.applications.followApplications.latest.title" />
								</legend>
								<div class="row rowWithPadding" style="margin-bottom: 20px !important; margin-top: -7px !important;">
									<c:forEach items="${latestApplications}" var="latestApplication">
										<div class="col-xs-6 col-md-3 col-lg-3">
											<i class="text-color fa fa-chevron-circle-right fa-1x"></i>&nbsp;${latestApplication.name}
										</div>
									</c:forEach>
								</div>
							</fieldset>
							<c:set var="filterPlaceholder">
								<spring:message code="dashboard.applications.followApplications.filter.description" />
							</c:set>
							<fieldset>
								<legend class="legend">
									<spring:message code="dashboard.applications.followApplications.filter.title" />
								</legend>
								<div class="row rowWithPadding" style="margin-top: -7px !important;">
									<div class="col-md-4">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-search"></i></span>
											<input id="filter" type="search" class="form-control" placeholder="${filterPlaceholder}" />
										</div>
									</div>
									<div id="filter-count" class="col-md-3">&nbsp;</div>
								</div>
							</fieldset>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="modal-body" style="padding-top: 0px !important; padding-bottom: 0px !important;">
					<div id="appsGrid" class="row" ${styleAppsGrid}>
						<c:forEach items="${leftApplications}" var="leftApplication" varStatus="i">
							<c:set var="appName">${leftApplication.name}</c:set>
							<c:set var="appId">${leftApplication.apiName}</c:set>
							<c:set var="styleCommercial" value="" />
							<c:if test="${leftApplication.type == COMMERCIAL}">
								<c:set var="styleCommercial" value="application-commercial" />
							</c:if>
							<div class="col-xs-4 col-sm-3 col-md-2 col-lg-2 newFollowApplicationContainer" onclick="followNewApplication('${appId}');">
								<div id="div-new-${appId}" class="application ${styleCommercial}" title="${appName} - <spring:message code='appslist.type.${leftApplication.type}' />">
									<div class="icon sprite sprite-${leftApplication.apiName}"> </div>
									<div class="title">
										<span class="label label-success" style="display: inline-block !important; max-width: 100px !important; white-space: normal !important;">${appName}</span>
									</div>
								</div>
							</div>
							<form:checkbox path="apiNames[${i.index}]" id="app-${appId}" value="${appId}" cssClass="hidden" />
						</c:forEach>
						<c:if test="${fn:length(leftApplications) > 0}">
							<div style="height: 400px;"></div>
						</c:if>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-left btn-cancel-next-ladda" data-dismiss="modal">
						<spring:message code="dashboard.applications.followApplications.button.cancel" />
					</button>
					<c:if test="${fn:length(leftApplications) != 0}">
						<button type="button" class="btn-color ladda-button push-to-bottom" onclick="submitForm();">
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
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-home"></i></span>
							<form:input path="url" class="form-control" id="requestedAppUrl" placeholder="${requestedAppUrlPlaceholder}" />
						</div>
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
	function submitForm() {
		var currentCounterValue = parseInt($("#counterNewApp").text())
		if (currentCounterValue == 0) {
			// nothing selected
			return;
		}
		$("form#newFollowedApplicationsForm").submit();
	}
	$("#followApplicationsModal").on("show.bs.modal", function (e) {
		setTimeout(function() { $("#filter").focus(); }, 1000);
	});
	$("#requestApplicationModal").on("show.bs.modal", function (e) {
		setTimeout(function() { $("#requestedAppName").focus(); }, 1000);
	});
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
	function ajaxRateApplication(appId) {
		ajaxCallPostWithUrl(null, "${root}/dashboard/rate", {
			"apiName" : appId,
			"rating" : $("#rating-" + appId).val()
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
		setTimeout(function() { $("html, body").scrollTop(0); }, 1000);
	};
	$("#filter").keydown(function() {
		setTimeout(function() {
			filterApps();
		}, 500); // timeout needed to let the time for the field to be populated
	});
	function filterApps() {
		// retrieve the input field text and reset the count to zero
		var filter = $("#filter").val();
		var count = 0;
		// loop through the app list
		$("#appsGrid div.newFollowApplicationContainer").each(
				function() {
					// if the name of the app does not contain the text phrase fade it out
					if (jQuery(this).find("span.label-success").text().search(
							new RegExp(filter, "i")) < 0) {
						$(this).fadeOut();
					} else {
						// show the app if the phrase matches and increase the count by 1
						$(this).show();
						count++;
					}

				});
		updateCounter(count);
	}
	function updateCounter(count) {
		var app = "application";
		if (count >= 2) {
			app += "s";
		}
		var found = "<spring:message code='dashboard.applications.followApplications.filter.found' />";
		$("#filter-count").html(
				"<span class='label label-info'>" + count + "</span> <small>"
						+ app + " " + found + "</small>");
	}
	$(document).ready(function() {
		$('.rating-input i:first-child').trigger('mouseleave');
		updateCounter('${fn:length(leftApplications)}');
	});
</script>

<c:if test="${gridSize == 'small'}">
	<script>
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
</c:if>
