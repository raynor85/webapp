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
				<div class="col-sm-8">
					<form:input class="form-control" path="name" id="name" maxlength="255" />
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-sm-2 control-label" style="min-width: 130px;"><spring:message code="settings.profile.field.email" /></label>
				<div class="col-sm-5">
					<p class="form-control-static">
						<c:set var="email">
							<sec:authentication property="principal.email" />
						</c:set>
						<c:out value="${email}" escapeXml="false" />
					</p>
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
			<div class="form-group">
				<div class="col-sm-12">
					<spring:message code="settings.emails.radio.email.alert.title" />
				</div>
				<div class="col-sm-3">
					<label class="radio-inline"> <form:radiobutton path="emailAlert" id="emailAlertEnable" value="true" onclick="updateEmailOptions();" /> <spring:message code="settings.emails.radio.enable" />
					</label> <label class="radio-inline"> <form:radiobutton path="emailAlert" id="emailAlertDisable" value="false" onclick="updateEmailOptions();" /> <spring:message code="settings.emails.radio.disable" />
					</label>
				</div>
			</div>
			<div style="height: 8px"></div>
			<div class="form-group">
				<div class="col-sm-12" id="emailOptionEachUpdateTitle">
					<spring:message code="settings.emails.radio.email.each.title" />
				</div>
				<div class="col-sm-3">
					<label class="radio-inline" id="emailOptionEachUpdateLabelEnable"> <form:radiobutton path="emailEachUpdate" id="emailOptionEachUpdateEnable" value="true" /> <spring:message code="settings.emails.radio.enable" />
					</label> <label class="radio-inline" id="emailOptionEachUpdateLabelDisable"> <form:radiobutton path="emailEachUpdate" id="emailOptionEachUpdateDisable" value="false" /> <spring:message code="settings.emails.radio.disable" />
					</label>
				</div>
			</div>
			<div style="height: 8px"></div>
			<div class="form-group">
				<div class="col-sm-12" id="emailOptionWeeklyTitle">
					<spring:message code="settings.emails.radio.email.weekly.title" />
				</div>
				<div class="col-sm-3">
					<label class="radio-inline" id="emailOptionWeeklyLabelEnable"> <form:radiobutton path="emailWeekly" id="emailOptionWeeklyEnable" value="true" /> <spring:message code="settings.emails.radio.enable" />
					</label> <label class="radio-inline" id="emailOptionWeeklyLabelDisable"> <form:radiobutton path="emailWeekly" id="emailOptionWeeklyDisable" value="false" /> <spring:message code="settings.emails.radio.disable" />
					</label>
				</div>
			</div>
			<div style="height: 8px"></div>
			<div class="form-group">
				<div class="col-sm-12">
					<spring:message code="settings.emails.radio.email.newsletter.title" />
				</div>
				<div class="col-sm-3">
					<label class="radio-inline"> <form:radiobutton path="emailNewsletter" id="emailNewsletterEnable" value="true" /> <spring:message code="settings.emails.radio.enable" />
					</label> <label class="radio-inline"> <form:radiobutton path="emailNewsletter" id="emailNewsletterDisable" value="false" /> <spring:message code="settings.emails.radio.disable" />
					</label>
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
				<form:form id="changePasswordUserForm" commandName="changePasswordUser" action="${root}/settings/changePassword">
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
						<button type="button" class="btn btn-default pull-left" data-dismiss="modal" style="margin-top: 21px;">
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
			<form:form id="deleteAccountForm" commandName="deleteAccount" action="${root}/settings/deleteAccount">
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
	$(".selectpicker").selectpicker();
	function ajaxUpdateSettings() {
		var json = {
			"name" : $("#name").val(),
			"lang" : $("#lang").val(),
			"emailAlert" : $("input[name='emailAlert']:checked").val(),
			"emailEachUpdate" : $("input[name='emailEachUpdate']:checked")
					.val(),
			"emailWeekly" : $("input[name='emailWeekly']:checked").val(),
			"emailNewsletter" : $("input[name='emailNewsletter']:checked")
					.val()
		};
		ajaxCall("#updateSettingsButton", "#updateSettingsForm", json,
				"#updateSettingsResponse", refreshUsername);
	};
	$("#updateSettingsForm").submit(function() {
		ajaxUpdateSettings();
		return false;
	});
	var refreshUsername = function() {
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
		ajaxCall("#changePasswordUserButton", "#changePasswordUserForm", json,
				"#changePasswordUserResponse", confirmChangePassword);
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
		$("input[id^=emailOption]:radio").attr('disabled', disabled);
		var animateColor = "#333333";
		if (disabled) {
			var animateColor = "#B2B2B2";
		}
		$("[id^=emailOption]").animate({
			color : animateColor
		}, 1000);
	}
	updateEmailOptions();
</script>
