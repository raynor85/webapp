<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="isSocialUser">
	<sec:authentication property="principal.socialUser" />
</c:set>

<div class="container">

	<div class="form-white">
		<form:form id="updateSettingsForm" commandName="updateSettings" action="${root}/settings/update" class="form-horizontal">
			<div id="updateSettingsResponse"></div>
			<div id="successPasswordUserResponse"></div>
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
						<a href="#" data-toggle="modal" data-target="#changePasswordModal" title="<spring:message code="settings.profile.changePassword.link" />"><spring:message code="settings.profile.changePassword.link" /></a>
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
				<label for="lang" class="col-sm-2 control-label" style="min-width: 130px;"><spring:message code="settings.update.field.lang" /></label>
				<div id="langDiv" class="col-sm-4">
					<form:select class="form-control selectpicker" path="lang" id="lang">
						<c:set var="englishLang">
							<spring:message code="settings.update.field.lang.english" />
						</c:set>
						<c:set var="frenchLang">
							<spring:message code="settings.update.field.lang.french" />
						</c:set>
						<form:option value="eng" label="${englishLang}" htmlEscape="false" />
						<form:option value="fra" label="${frenchLang}" htmlEscape="false" />
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
					<form:checkbox data-off-text="${disable}" data-on-text="${enable}" path="emailAlert" onchange="updateEmailOptions();" />
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
					<spring:message code="settings.emails.radio.email.newsletter.title" />
				</div>
				<div class="col-sm-3">
					<form:checkbox data-off-text="${disable}" data-on-text="${enable}" path="emailNewsletter" />
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
				<div id="deleteAccountLink" class="col-lg-3 pull-right">
					<a href="#" data-toggle="modal" data-target="#deleteAccountModal" title="<spring:message code="settings.account.delete.link" />"><spring:message code="settings.account.delete.link" /></a>
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
				<form:form id="changePasswordUserForm" commandName="changePasswordUser" action="${root}/settings/updapte/password">
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
			"lang" : $("#lang").val(),
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
		// display success
		$("#successPasswordUserResponse").html(
				$("#changePasswordUserResponse").html());
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
		var animateColor = "#333333";
		if (disabled) {
			var animateColor = "#B2B2B2";
		}
		$("div[id^=emailSubOption]").animate({
			color : animateColor
		}, 1000);
	}
	updateEmailOptions();
</script>