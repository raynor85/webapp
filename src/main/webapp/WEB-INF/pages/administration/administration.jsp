<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<div id="jobAdministrationResponse"></div>
			<h3>
				<spring:message code="administration.action.title" />
				<small><spring:message code="administration.action.subtitle" /></small>
			</h3>
			<hr>
			<ul class="fa-ul">
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/cache/clear', 'cache-clear');"><spring:message code="administration.action.cache.clear.button" /></a> <i id="cache-clear" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/retrievalErrors/clear', 'retrieval-errors-clear', clearRetrievalErrors);"><spring:message code="administration.action.retrievalErrors.clear.button" /></a> <i id="errors-clear" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/database/cleanup', 'database-cleanup', refreshNumberOfRowsInDatabase);"><spring:message code="administration.action.database.cleanup.button" /></a> (<span id="numberOfRowsInDatabase">${numberOfRowsInDatabase}</span> <spring:message code="administration.action.database.row" />) <i id="database-cleanup" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/repository/update', 'repository-update');"><spring:message code="administration.action.repository.update.button" /></a> <i id="repository-update" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/repository/check', 'repository-check');"><spring:message code="administration.action.repository.check.button" /></a> <i id="repository-check" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/email/application/added', 'email-added-app');"><spring:message code="administration.action.email.application.added.button" /></a> <i id="email-added-app" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/email/application/deleted', 'email-deleted-app');"><spring:message code="administration.action.email.application.deleted.button" /></a> <i id="email-deleted-app" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/email/newsletter', 'email-newsletter');"><spring:message code="administration.action.email.newsletter.button" /></a> <i id="email-newsletter" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/email/send', 'email-send');"><spring:message code="administration.action.email.send.button" /></a> <i id="email-send" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="https://papertrailapp.com/systems/updapy/events" target="_blank"><spring:message code="administration.action.logs.button" /> <i class="fa fa-external-link"></i></a></li>
			</ul>
		</div>

		<div class="row rowWithPadding" align="center">
			<div class="button-ladda">
				<button type="button" class="btn-color ladda-button" data-toggle="modal" data-target="#addVersionModal">
					<spring:message code="administration.version.add.button" />
				</button>
			</div>
		</div>

		<div class="col-sm-12">
			<h3>
				<spring:message code="administration.error.title" />
				<small><spring:message code="administration.error.subtitle" /></small>
			</h3>
			<hr>
			<div class="table-responsive">
				<form:form id="deleteRetrievalErrorForm" commandName="deleteRetrievalError" action="${root}/administration/retrievalError/delete">
					<table class="table table-vertical-align">
						<thead>
							<tr class="active">
								<th><spring:message code="administration.error.table.head.application" /></th>
								<th><spring:message code="administration.error.table.head.type" /></th>
								<th><spring:message code="administration.error.table.head.counter" /> <i class="fa fa-sort-numeric-desc"></i></th>
								<th style="width: 300px"><spring:message code="administration.error.table.head.message" /></th>
								<th><spring:message code="administration.error.table.head.version" /></th>
								<th><spring:message code="administration.error.table.head.globalUrl" /></th>
								<th><spring:message code="administration.error.table.head.version32Url" /></th>
								<th><spring:message code="administration.error.table.head.version64Url" /></th>
								<th><spring:message code="administration.error.table.head.creationDate" /></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${administrationRetrievalErrors}" var="administrationRetrievalError">
								<tr id="tr-${administrationRetrievalError.retrievalError.id}">
									<td>${administrationRetrievalError.retrievalError.application.name}</td>
									<td><spring:message code="administration.error.type.${administrationRetrievalError.retrievalError.typeLastError}" /></td>
									<td>${administrationRetrievalError.retrievalError.count}</td>
									<td>${administrationRetrievalError.retrievalError.message}</td>
									<td>${administrationRetrievalError.latestVersion.versionNumber}</td>
									<td><a href="${administrationRetrievalError.retrievalError.application.globalUrl}" target="_blank"><i class="fa fa-external-link"></i></a></td>
									<td><a href="${administrationRetrievalError.latestVersion.win32UrlEn}" target="_blank"><i class="fa fa-external-link"></i></a></td>
									<td><c:choose>
											<c:when test="${not empty administrationRetrievalError.latestVersion.win64UrlEn}">
												<a href="${administrationRetrievalError.latestVersion.win64UrlEn}" target="_blank"><i class="fa fa-external-link"></i></a>
											</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose></td>
									<td><fmt:formatDate value="${administrationRetrievalError.retrievalError.creationDate}" type="both" timeStyle="short" /></td>
									<td>
										<c:set var="deleteTitle">
											<spring:message code="administration.error.table.delete.title" arguments="${administrationRetrievalError.retrievalError.application.name}" />
										</c:set>
										<button title="${deleteTitle}" aria-hidden="true" class="close" type="button" onclick="ajaxDeleteRetrievalError('${administrationRetrievalError.retrievalError.id}');">
											<i class="fa fa-trash-o fa-1x"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form:form>
			</div>
		</div>

		<div class="col-sm-12">
			<h3>
				<spring:message code="administration.ignored.title" />
				<small><spring:message code="administration.ignored.subtitle" /></small>
			</h3>
			<hr>
			<div class="table-responsive">
				<table class="table table-vertical-align">
					<thead>
						<tr class="active">
							<th><spring:message code="administration.ignored.table.head.application" /> <i class="fa fa-sort-alpha-asc"></i></th>
							<th><spring:message code="administration.ignored.table.head.type" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ignoredApplications}" var="ignoredApplication">
							<tr>
								<td>${ignoredApplication.name}</td>
								<td><spring:message code="administration.ignored.type.${ignoredApplication.ignoranceType}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<!-- Modal: Add version -->
<div class="modal fade" id="addVersionModal" tabindex="-1" role="dialog" aria-labelledby="addVersionModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form:form id="addVersionForm" commandName="addVersion" action="${root}/administration/version/add">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="addVersionModalLabel">
						<spring:message code="administration.version.add.title" />
					</h4>
				</div>
				<div class="modal-body">
					<div id="addVersionResponse"></div>
					<div class="form-group">
						<label for="apiName"><spring:message code="administration.version.add.field.apiName" /> </label>
						<c:set var="apiNamePlaceholder">
							<spring:message code="administration.version.add.field.apiName.tip" />
						</c:set>
						<form:input path="apiName" class="form-control" id="apiName" placeholder="${apiNamePlaceholder}" />
					</div>
					<div class="form-group">
						<label for="win32UrlEn"><spring:message code="administration.version.add.field.win32UrlEn" /> </label>
						<c:set var="win32UrlEnPlaceholder">
							<spring:message code="administration.version.add.field.url.tip" />
						</c:set>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-home"></i></span>
							<form:input path="win32UrlEn" class="form-control" id="win32UrlEn" placeholder="${win32UrlEnPlaceholder}" />
						</div>
					</div>
					<div class="form-group">
						<label for="win64UrlEn"><spring:message code="administration.version.add.field.win64UrlEn" /> </label>
						<c:set var="win64UrlEnPlaceholder">
							<spring:message code="administration.version.add.field.url.tip" />
						</c:set>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-home"></i></span>
							<form:input path="win64UrlEn" class="form-control" id="win64UrlEn" placeholder="${win64UrlEnPlaceholder}" />
						</div>
					</div>
					<div class="form-group">
						<label for="win32UrlFr"><spring:message code="administration.version.add.field.win32UrlFr" /> </label>
						<c:set var="win32UrlFrPlaceholder">
							<spring:message code="administration.version.add.field.url.tip" />
						</c:set>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-home"></i></span>
							<form:input path="win32UrlFr" class="form-control" id="win32UrlFr" placeholder="${win32UrlFrPlaceholder}" />
						</div>
					</div>
					<div class="form-group">
						<label for="win64UrlFr"><spring:message code="administration.version.add.field.win64UrlFr" /> </label>
						<c:set var="win64UrlFrPlaceholder">
							<spring:message code="administration.version.add.field.url.tip" />
						</c:set>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-home"></i></span>
							<form:input path="win64UrlFr" class="form-control" id="win64UrlFr" placeholder="${win64UrlFrPlaceholder}" />
						</div>
					</div>
					<div class="form-group">
						<label for="versionNumber"><spring:message code="administration.version.add.field.versionNumber" /> </label>
						<c:set var="versionNumberPlaceholder">
							<spring:message code="administration.version.add.field.versionNumber.tip" />
						</c:set>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-tag"></i></span>
							<form:input path="versionNumber" class="form-control" id="versionNumber" placeholder="${versionNumberPlaceholder}" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-left btn-cancel-next-ladda" data-dismiss="modal">
						<spring:message code="administration.version.add.button.cancel" />
					</button>
					<button type="button" id="addVersionButton" class="btn-color ladda-button" data-style="zoom-in" onclick="ajaxAddVersion();">
						<spring:message code="administration.version.add.button.confirm" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	function runJobAdministration(url, idspin, jsToExecuteWhenSucess) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			type : "GET",
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
				$("#" + idspin).show();
			},
			url : url,
			contentType : "text/plain",
			cache : false,
			success : function(response) {
				var type = "";
				var substText = "";
				var icon = "";
				if (response.status == "SUCCESS") {
					type = "success";
					substText = "Success";
					icon = "check";
				} else if (response.status == "FAIL") {
					type = "danger";
					substText = "Error";
					icon = "exclamation";
				}
				responseInDiv = "<div class='alert alert-" + type + " alert-dismissable' role='alert'>";
				responseInDiv += "<i class='fa fa-" + icon + "-circle'></i> ";
				responseInDiv += "<span class='sr-only'>" + substText + ":</span>";
				responseInDiv += "<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";
				for (var i = 0; i < response.result.length; i++) {
					responseInDiv += response.result[i] + "<br />";
				}
				responseInDiv += "</div>";
				$("#" + idspin).hide();
				$("#jobAdministrationResponse").html(responseInDiv);
				if (response.status == "SUCCESS" && jsToExecuteWhenSucess != null) {
					jsToExecuteWhenSucess();
				}
			}
		});
	};
	var refreshNumberOfRowsInDatabase = function() {
		ajaxCallGetAndRefresh("${root}/administration/numberOfRowsInDatabase", "#numberOfRowsInDatabase");
	};
	function ajaxDeleteRetrievalError(retrievalErrorId) {
		$("#tr-" + retrievalErrorId).fadeOut();
		ajaxCallPost(null, "#deleteRetrievalErrorForm", {
			"retrievalErrorId" : retrievalErrorId
		}, null);
	};
	var clearRetrievalErrors = function() {
		$("tr[id^=tr-]").fadeOut();
	};
	$("#addVersionModal").on("show.bs.modal", function (e) {
		setTimeout(function() { $("#apiName").focus(); }, 1000);
	});
	function ajaxAddVersion() {
		var json = {
			"apiName" : $("#apiName").val(),
			"win32UrlEn" : $("#win32UrlEn").val(),
			"win64UrlEn" : $("#win64UrlEn").val(),
			"win32UrlFr" : $("#win32UrlFr").val(),
			"win64UrlFr" : $("#win64UrlFr").val(),
			"versionNumber" : $("#versionNumber").val()
		};
		ajaxCallPost("#addVersionButton", "#addVersionForm", json,
				"#addVersionResponse", clearForm);
	};
	var clearForm = function() {
		$("#apiName").val("");
		$("#win32UrlEn").val("");
		$("#win64UrlEn").val("");
		$("#win32UrlFr").val("");
		$("#win64UrlFr").val("");
		$("#versionNumber").val("");
	};
	$("#addVersionForm").submit(function() {
		ajaxAddVersion();
		return false;
	});
</script>