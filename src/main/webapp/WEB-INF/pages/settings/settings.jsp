<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<link href="<spring:url value="/resources/css/bootstrap-select.min.css" />" rel="stylesheet">
<script src="<spring:url value="/resources/js/bootstrap-select.min.js" />"></script>
<link href="<spring:url value="/resources/css/bootstrap-switch.min.css" />" rel="stylesheet">
<script src="<spring:url value="/resources/js/bootstrap-switch.min.js" />"></script>

<c:set var="isSocialUser">
	<sec:authentication property="principal.socialUser" />
</c:set>

<div class="container">

	<div class="form-white">
		<form:form id="updateSettingsForm" commandName="updateSettings" action="${root}/settings/update" class="form-horizontal">
			<div id="updateSettingsResponse"></div>
			<div id="successPasswordUserResponse"></div>
			<div id="successEmailUserResponse"></div>
			<h3>
				<spring:message code="settings.profile.title" />
				<small><spring:message code="settings.profile.subtitle" /></small>
			</h3>
			<hr>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label" style="min-width: 130px;"><spring:message code="settings.profile.field.name" /></label>
				<div class="col-sm-5">
					<form:input class="form-control" path="name" id="name" maxlength="35" />
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-sm-2 control-label" style="min-width: 130px;"><spring:message code="settings.profile.field.email" /></label>
				<div class="col-sm-5">
					<c:set var="email">
						<sec:authentication property="principal.email" />
					</c:set>
					<input class="form-control" value="${email}" readonly="true" />
				</div>
				<c:if test="${not isSocialUser}">
					<div class="col-lg-3 pull-right">
						<a href="#" data-toggle="modal" data-target="#changePasswordModal"><spring:message code="settings.profile.changePassword.link" /></a><br /> <a href="#" data-toggle="modal" data-target="#changeEmailModal"><spring:message code="settings.profile.changeEmail.link" /></a>
					</div>
				</c:if>
			</div>
			<br />

			<h3>
				<spring:message code="settings.update.title" />
				<small><spring:message code="settings.update.subtitle" /></small>
			</h3>
			<hr>
			<div class="alert alert-info">
				<spring:message code="settings.update.tip" />
			</div>
			<div class="form-group">
				<label for="langUpdate" class="col-sm-2 control-label" style="min-width: 130px;"><spring:message code="settings.update.field.langUpdate" /></label>
				<div id="langUpdateDiv" class="col-sm-4">
					<form:select class="form-control selectpicker" path="langUpdate" id="langUpdate">
						<c:set var="englishLangUpdate">
							<spring:message code="settings.update.field.langUpdate.english" />
						</c:set>
						<c:set var="frenchLangUpdate">
							<spring:message code="settings.update.field.langUpdate.french" />
						</c:set>
						<form:option value="en" label="${englishLangUpdate}" htmlEscape="false" />
						<form:option value="fr" label="${frenchLangUpdate}" htmlEscape="false" />
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label for="osVersion" class="col-sm-2 control-label" style="min-width: 130px;"><spring:message code="settings.update.field.osVersion" /></label>
				<div id="osVersionDiv" class="col-sm-4">
					<form:select class="form-control selectpicker" path="osVersion" id="osVersion">
						<c:set var="win32BitsOsVersion">
							<spring:message code="settings.update.field.osVersion.win32" />
						</c:set>
						<c:set var="win64BitsOsVersion">
							<spring:message code="settings.update.field.osVersion.win64" />
						</c:set>
						<form:option value="WIN_32_BITS" label="${win32BitsOsVersion}" htmlEscape="false" />
						<form:option value="WIN_64_BITS" label="${win64BitsOsVersion}" htmlEscape="false" />
					</form:select>
				</div>
			</div>
			<br />

			<h3>
				<spring:message code="settings.emails.title" />
				<small><spring:message code="settings.emails.subtitle" /></small>
			</h3>
			<hr>
			<c:set var="enable">
				<spring:message code="settings.emails.radio.enable" />
			</c:set>
			<c:set var="disable">
				<spring:message code="settings.emails.radio.disable" />
			</c:set>
			<div class="form-group">
				<div class="col-sm-12">
					<spring:message code="settings.emails.radio.email.alert.title" />
				</div>
				<div class="col-sm-3">
					<form:checkbox data-off-text="${disable}" data-on-text="${enable}" path="emailAlert" id="optionEmail" onchange="updateEmailOptions();" />
				</div>
			</div>
			<div style="height: 8px"></div>
			<div class="form-group">
				<div class="col-sm-12" id="emailSubOptionEachUpdateTitle">
					<spring:message code="settings.emails.radio.email.each.title" />
				</div>
				<div class="col-sm-3">
					<form:checkbox data-off-text="${disable}" data-on-text="${enable}" path="emailEachUpdate" id="emailSubOptionEachUpdate" />
				</div>
			</div>
			<div style="height: 8px"></div>
			<div class="form-group">
				<div class="col-sm-12" id="emailSubOptionWeeklyTitle">
					<spring:message code="settings.emails.radio.email.weekly.title" />
				</div>
				<div class="col-sm-3">
					<form:checkbox data-off-text="${disable}" data-on-text="${enable}" path="emailWeekly" id="emailSubOptionWeekly" />
				</div>
			</div>
			<div style="height: 8px"></div>
			<div class="form-group">
				<div class="col-sm-12">
					<spring:message code="settings.emails.radio.email.app.added.title" />
				</div>
				<div class="col-sm-3">
					<form:checkbox data-off-text="${disable}" data-on-text="${enable}" path="emailAppAdded" id="optionEmailAppAdded" />
				</div>
			</div>
			<div style="height: 8px"></div>
			<div class="form-group">
				<div class="col-sm-12">
					<spring:message code="settings.emails.radio.email.newsletter.title" />
				</div>
				<div class="col-sm-3">
					<form:checkbox data-off-text="${disable}" data-on-text="${enable}" path="emailNewsletter" id="optionNewsletter" />
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-sm-5">
					<p class="text-center-xs button-ladda">
						<button type="button" id="updateSettingsButton" class="btn-color ladda-button" data-style="zoom-in" onclick="ajaxUpdateSettings();">
							<spring:message code="settings.save.button" />
						</button>
					</p>
				</div>
				<div class="col-lg-3 pull-right discreetLink">
					<a href="#" data-toggle="modal" data-target="#deleteAccountModal"><spring:message code="settings.account.delete.link" /></a>
				</div>
			</div>
		</form:form>
	</div>
</div>

<c:if test="${not isSocialUser}">
	<!-- Modal: Change password -->
	<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form:form id="changePasswordUserForm" commandName="changePasswordUser" action="${root}/settings/update/password">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="changePasswordModalLabel">
							<spring:message code="settings.profile.changePassword.title" />
						</h4>
					</div>
					<div class="modal-body">
						<div id="changePasswordUserResponse"></div>
						<div class="form-group">
							<label for="currentPassword"><spring:message code="settings.profile.changePassword.field.current.password" /></label>
							<c:set var="currentPasswordPlaceholder">
								<spring:message code="settings.profile.changePassword.field.current.password.tip" />
							</c:set>
							<form:input type="password" path="currentPassword" class="form-control" id="currentPassword" placeholder="${currentPasswordPlaceholder}" />
						</div>
						<div class="form-group">
							<label for="newPassword"><spring:message code="settings.profile.changePassword.field.new.password" /></label>
							<c:set var="newPasswordPlaceholder">
								<spring:message code="settings.profile.changePassword.field.new.password.tip" />
							</c:set>
							<form:input type="password" path="newPassword" class="form-control" id="newPassword" placeholder="${newPasswordPlaceholder}" />
						</div>
						<div class="form-group">
							<label for="repeatNewPassword"><spring:message code="settings.profile.changePassword.field.repeat.password" /></label>
							<c:set var="repeatPasswordPlaceholder">
								<spring:message code="settings.profile.changePassword.field.repeat.password.tip" />
							</c:set>
							<form:input type="password" path="repeatNewPassword" class="form-control" id="repeatNewPassword" placeholder="${repeatPasswordPlaceholder}" />
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default pull-left btn-cancel-next-ladda" data-dismiss="modal">
							<spring:message code="settings.profile.changePassword.button.cancel" />
						</button>
						<button type="button" id="changePasswordUserButton" class="btn-color ladda-button" data-style="zoom-in" onclick="ajaxChangePasswordUser();">
							<spring:message code="settings.profile.changePassword.button.confirm" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</c:if>

<c:if test="${not isSocialUser}">
	<!-- Modal: Change email -->
	<div class="modal fade" id="changeEmailModal" tabindex="-1" role="dialog" aria-labelledby="changeEmailModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form:form id="changeEmailUserForm" commandName="changeEmailUser" action="${root}/settings/update/email">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="changeEmailModalLabel">
							<spring:message code="settings.profile.changeEmail.title" />
						</h4>
					</div>
					<div class="modal-body">
						<div id="changeEmailUserResponse"></div>
						<div class="form-group">
							<label for="newEmail"><spring:message code="settings.profile.changeEmail.field.new.email" /></label>
							<c:set var="newEmailPlaceholder">
								<spring:message code="settings.profile.changeEmail.field.new.email.tip" />
							</c:set>
							<form:input type="email" path="newEmail" class="form-control" id="newEmail" placeholder="${newEmailPlaceholder}" />
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default pull-left btn-cancel-next-ladda" data-dismiss="modal">
							<spring:message code="settings.profile.changeEmail.button.cancel" />
						</button>
						<button type="button" id="changeEmailUserButton" class="btn-color ladda-button" data-style="zoom-in" onclick="ajaxChangeEmailUser();">
							<spring:message code="settings.profile.changeEmail.button.confirm" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</c:if>

<!-- Modal: Delete account -->
<div class="modal fade" id="deleteAccountModal" tabindex="-1" role="dialog" aria-labelledby="deleteAccountModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form:form id="deleteAccountForm" commandName="deleteAccount" action="${root}/settings/delete">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="deleteAccountModalLabel">
						<spring:message code="settings.account.delete.title" />
					</h4>
				</div>
				<div class="modal-body">
					<div class="alert alert-danger">
						<spring:message code="settings.account.delete.field.warning" />
					</div>
					<spring:message code="settings.account.delete.field.feedback" />
					<br /> <br />
					<div class="form-group">
						<c:set var="feedbackPlaceholder">
							<spring:message code="settings.account.delete.field.feedback.tip" />
						</c:set>
						<form:textarea rows="3" path="feedback" class="form-control" id="feedback" placeholder="${feedbackPlaceholder}" />
					</div>
					<spring:message code="settings.account.delete.field.feedback.description" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-left" data-dismiss="modal">
						<spring:message code="settings.account.delete.button.cancel" />
					</button>
					<button type="submit" class="btn btn-danger">
						<spring:message code="settings.account.delete.button.confirm" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>


<script type="text/javascript">
	// activate elements
	$(".selectpicker").selectpicker();
	$("input[type='checkbox']").bootstrapSwitch();

	function ajaxUpdateSettings() {
		var json = {
			"name" : $("#name").val(),
			"langUpdate" : $("#langUpdate").val(),
			"osVersion" : $("#osVersion").val(),
			"emailAlert" : $("input[name='emailAlert']:checked").val(),
			"emailEachUpdate" : $("input[name='emailEachUpdate']:checked")
					.val(),
			"emailWeekly" : $("input[name='emailWeekly']:checked").val(),
			"emailNewsletter" : $("input[name='emailNewsletter']:checked")
					.val()
		};
		ajaxCallPost("#updateSettingsButton", "#updateSettingsForm", json,
				"#updateSettingsResponse", refreshUsername);
	};
	$("#updateSettingsForm").submit(function() {
		ajaxUpdateSettings();
		return false;
	});
	var refreshUsername = function() {
		$("html, body").scrollTop(0);
		var newName = $("#name").val();
		var currentName = $("#username").text();
		if (!$.trim(newName)) {
			// use email if username is not defined
			newName = "${email}";
		}
		if (newName != currentName) {
			$("#username").html(newName);
		}
	};
	function ajaxChangePasswordUser() {
		var json = {
			"currentPassword" : $("#currentPassword").val(),
			"newPassword" : $("#newPassword").val(),
			"repeatNewPassword" : $("#repeatNewPassword").val()
		};
		ajaxCallPost("#changePasswordUserButton", "#changePasswordUserForm",
				json, "#changePasswordUserResponse", confirmChangePassword,
				true);
	};
	$("#changePasswordUserForm").submit(function() {
		ajaxChangePasswordUser();
		return false;
	});
	var confirmChangePassword = function() {
		// close modal
		$("#changePasswordModal").modal("hide");
		// Empty fields
		$("#currentPassword").val("");
		$("#newPassword").val("");
		$("#repeatNewPassword").val("");
		// display success
		$("#successPasswordUserResponse").html(
				$("#changePasswordUserResponse").html());
	};
	function ajaxChangeEmailUser() {
		var json = {
			"newEmail" : $("#newEmail").val(),
		};
		ajaxCallPost("#changeEmailUserButton", "#changeEmailUserForm", json,
				"#changeEmailUserResponse", confirmChangeEmail, true);
	};
	$("#changeEmailUserForm").submit(function() {
		ajaxChangeEmailUser();
		return false;
	});
	var confirmChangeEmail = function() {
		// close modal
		$("#changeEmailModal").modal("hide");
		// Empty fields
		$("#newEmail").val("");
		// display success
		$("#successEmailUserResponse").html(
				$("#changeEmailUserResponse").html());
		// Disconnect
		setTimeout(function() {
			logout();
		}, 7000);
	};
	function updateEmailOptions() {
		var emailActive = $("input[name='emailAlert']:checked").val();
		if (emailActive === "true") {
			// enable options
			changeEmailOptions(false);
		} else {
			// disable options
			changeEmailOptions(true);
		}
	}
	function changeEmailOptions(disabled) {
		// deactivate switchers
		$("input[id^=emailSubOption]").bootstrapSwitch("readonly", disabled);
		$("input[id^=emailSubOption]").bootstrapSwitch("disabled", disabled);
		// put titles in grey
		var textColor = "#333333";
		if (disabled) {
			var textColor = "#B2B2B2";
		}
		$("div[id^=emailSubOption]").animate({
			color : textColor
		}, 0);
	}
	$("#emailSubOptionWeekly").on(
			"switchChange.bootstrapSwitch",
			function(event, state) {
				if (state) {
					$("#emailSubOptionEachUpdate").bootstrapSwitch("state",
							false);
				}
			});
	$("#emailSubOptionEachUpdate").on("switchChange.bootstrapSwitch",
			function(event, state) {
				if (state) {
					$("#emailSubOptionWeekly").bootstrapSwitch("state", false);
				}
			});
	$("input[type='checkbox']").on("click.bootstrapSwitch", function(e, data) {
		$(this).bootstrapSwitch("toggleState");
		if ($.browser.mozilla) {
			$(this).bootstrapSwitch("toggleState");
		}
	});
	updateEmailOptions();
</script>