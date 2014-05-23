<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="container">

	<div class="form-white">
		<form:form id="updateSettingsForm" commandName="updateSettings" action="${root}/settings/update" class="form-horizontal">
			<h3>
				<spring:message code="settings.profile.title" />
				<small><spring:message code="settings.profile.subtitle" /></small>
			</h3>
			<hr>
			<div class="form-group">
				<label for="name" class="col-sm-1 control-label"><spring:message code="settings.profile.field.name" /></label>
				<div class="col-sm-5">
					<form:input class="form-control" path="name" id="name" />
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-sm-1 control-label"><spring:message code="settings.profile.field.email" /></label>
				<div class="col-sm-5">
					<p class="form-control-static">
						<c:set var="email">
							<sec:authentication property="principal.email" />
						</c:set>
						<c:out value="${email}" escapeXml="false" />
					</p>
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
			<p class="text-center-xs button-ladda">
				<button type="button" class="btn-color ladda-button" data-style="zoom-in" onclick="ajaxUpdateSettings();">
					<spring:message code="settings.save.button" />
				</button>
			</p>
			<div id="updateSettingsResponse"></div>
		</form:form>
	</div>
</div>

<script type="text/javascript">
	function ajaxUpdateSettings() {
		var json = {
			"name" : $("#name").val(),
			"emailAlert" : $("input[name='emailAlert']:checked").val(),
			"emailEachUpdate" : $("input[name='emailEachUpdate']:checked")
					.val(),
			"emailWeekly" : $("input[name='emailWeekly']:checked").val(),
			"emailNewsletter" : $("input[name='emailNewsletter']:checked")
					.val()
		};
		ajaxCall('#updateSettingsForm', json, '#updateSettingsResponse',
				refreshUsername);
	};
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
	}
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
