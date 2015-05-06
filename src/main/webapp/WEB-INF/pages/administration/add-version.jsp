<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<div class="row">
		<div class="col-sm-10 col-md-9 col-lg-8 col-sm-offset-1 col-md-offset-2 col-lg-offset-2">
			<h2 class="text-center">
				<spring:message code="administration.addVersion.title" />
			</h2>
			<p class="text-muted text-center">
				<spring:message code="administration.addVersion.subtitle" />
			</p>
			<div class="form-white-contact form-contact">
				<form:form id="addVersionForm" commandName="addVersion" action="${root}/administration/version/add">
					<div id="addVersionResponse" style="margin-top: 10px;">&nbsp;</div>
					<div class="form-group" style="margin-top: 20px;">
						<label for="apiName"><spring:message code="administration.addVersion.field.apiName" /> </label>
						<c:set var="apiNamePlaceholder">
							<spring:message code="administration.addVersion.field.apiName.tip" />
						</c:set>
						<form:input path="apiName" class="form-control show" id="apiName" placeholder="${apiNamePlaceholder}" />
					</div>
					<div class="form-group" style="margin-top: 20px;">
						<label for="win32UrlEn"><spring:message code="administration.addVersion.field.win32UrlEn" /> </label>
						<c:set var="win32UrlEnPlaceholder">
							<spring:message code="administration.addVersion.field.url.tip" />
						</c:set>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-home"></i></span>
							<form:input path="win32UrlEn" class="form-control show" id="win32UrlEn" placeholder="${win32UrlEnPlaceholder}" />
						</div>
					</div>
					<div class="form-group" style="margin-top: 20px;">
						<label for="win64UrlEn"><spring:message code="administration.addVersion.field.win64UrlEn" /> </label>
						<c:set var="win64UrlEnPlaceholder">
							<spring:message code="administration.addVersion.field.url.tip" />
						</c:set>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-home"></i></span>
							<form:input path="win64UrlEn" class="form-control show" id="win64UrlEn" placeholder="${win64UrlEnPlaceholder}" />
						</div>
					</div>
					<div class="form-group" style="margin-top: 20px;">
						<label for="win32UrlFr"><spring:message code="administration.addVersion.field.win32UrlFr" /> </label>
						<c:set var="win32UrlFrPlaceholder">
							<spring:message code="administration.addVersion.field.url.tip" />
						</c:set>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-home"></i></span>
							<form:input path="win32UrlFr" class="form-control show" id="win32UrlFr" placeholder="${win32UrlFrPlaceholder}" />
						</div>
					</div>
					<div class="form-group" style="margin-top: 20px;">
						<label for="win64UrlFr"><spring:message code="administration.addVersion.field.win64UrlFr" /> </label>
						<c:set var="win64UrlFrPlaceholder">
							<spring:message code="administration.addVersion.field.url.tip" />
						</c:set>
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-home"></i></span>
							<form:input path="win64UrlFr" class="form-control show" id="win64UrlFr" placeholder="${win64UrlFrPlaceholder}" />
						</div>
					</div>
					<div class="form-group" style="margin-top: 20px;">
						<label for="versionNumber"><spring:message code="administration.addVersion.field.versionNumber" /> </label>
						<c:set var="versionNumberPlaceholder">
							<spring:message code="administration.addVersion.field.versionNumber.tip" />
						</c:set>
						<form:input path="versionNumber" class="form-control show" id="versionNumber" placeholder="${versionNumberPlaceholder}" />
					</div>
					<button type="button" id="addVersionButton" class="btn-block btn-color ladda-button" data-style="zoom-in" onclick="ajaxAddVersion();">
						<spring:message code="administration.addVersion.add.button" />
					</button>
				</form:form>
				<div class="form-contact">
					<span class="fa-stack fa-4x"> <i class="fa fa-circle fa-stack-2x"></i> <i class="fa fa-tag fa-stack-1x"></i>
					</span>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	// focus
	mainFocus();
	function mainFocus() {
		setTimeout(function() {
			$("#apiName").focus();
		}, 1000);
	}
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
