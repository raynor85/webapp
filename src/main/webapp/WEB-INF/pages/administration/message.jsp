<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link href="<spring:url value="/resources/css/bootstrap-select.min.css" />" rel="stylesheet">
<script src="<spring:url value="/resources/js/bootstrap-select.min.js" />"></script>

<div class="container">
	<div class="row">
		<div class="col-sm-10 col-md-9 col-lg-8 col-sm-offset-1 col-md-offset-2 col-lg-offset-2">
			<h2 class="text-center">
				<spring:message code="message.title" />
			</h2>
			<p class="text-muted text-center">
				<spring:message code="message.description" />
			</p>
			<div class="form-white-contact form-contact">
				<form:form id="sendPersonalMessageForm" commandName="sendPersonalMessage" action="${root}/administration/message/send">
					<div id="sendPersonalMessageResponse" style="margin-top: 10px;">&nbsp;</div>
					<div class="form-group" style="margin-top: 20px;">
						<label for="email"><spring:message code="message.field.email" /> </label>
						<c:set var="emailPlaceholder">
							<spring:message code="message.field.email.tip" />
						</c:set>
						<form:input path="email" class="form-control show" id="email" type="email" placeholder="${emailPlaceholder}" />
					</div>
					<div class="form-group" style="margin-top: 20px;">
						<label for="langEmail"><spring:message code="message.field.langEmail" /></label>
						<form:select class="form-control selectpicker" path="langEmail" id="langEmail">
							<c:set var="englishLangEmail">
								<spring:message code="message.field.langEmail.english" />
							</c:set>
							<c:set var="frenchLangEmail">
								<spring:message code="message.field.langEmail.french" />
							</c:set>
							<form:option value="en" label="${englishLangEmail}" htmlEscape="false" />
							<form:option value="fr" label="${frenchLangEmail}" htmlEscape="false" />
						</form:select>
					</div>
					<div class="form-group" style="margin-top: 20px;">
						<label for="subject"><spring:message code="message.field.subject" /> </label>
						<c:set var="subjectPlaceholder">
							<spring:message code="message.field.subject.tip" />
						</c:set>
						<form:input path="subject" class="form-control show" id="subject" type="subject" placeholder="${subjectPlaceholder}" />
					</div>
					<div class="form-group" style="margin-top: 20px;">
						<label for="title"><spring:message code="message.field.title" /> </label>
						<c:set var="titlePlaceholder">
							<spring:message code="message.field.title.tip" />
						</c:set>
						<form:input path="title" class="form-control show" id="title" type="title" placeholder="${titlePlaceholder}" />
					</div>
					<div class="form-group">
						<label for="message"><spring:message code="message.field.message" /> </label>
						<c:set var="messagePlaceholder">
							<spring:message code="message.field.message.tip" />
						</c:set>
						<form:textarea path="message" class="form-control" id="message" rows="5" placeholder="${messagePlaceholder}" htmlEscape="false" />
					</div>
					<button type="button" id="sendPersonalMessageButton" class="btn-block btn-color ladda-button" data-style="zoom-in" onclick="ajaxSendPersonalMessage();">
						<spring:message code="message.send.button" />
					</button>
				</form:form>
				<div class="form-contact">
					<span class="fa-stack fa-4x"> <i class="fa fa-circle fa-stack-2x"></i> <i class="fa fa-comments fa-stack-1x"></i>
					</span>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	// activate elements
	$(".selectpicker").selectpicker();
	// focus
	mainFocus();
	function mainFocus() {
		var field = "email";
		if ($("#email").is("[readonly]")) {
			field = "message";
		}
		setTimeout(function() {
			$("#" + field).focus();
		}, 1000);
	}
	function ajaxSendPersonalMessage() {
		var json = {
			"email" : $("#email").val(),
			"langEmail" : $("#langEmail").val(),
			"subject" : $("#subject").val(),
			"title" : $("#title").val(),
			"message" : $("#message").val()
		};
		ajaxCallPost("#sendPersonalMessageButton", "#sendPersonalMessageForm",
				json, "#sendPersonalMessageResponse", clearMessage);
	};
	var clearMessage = function() {
		$("#message").val("");
	};
	$("#sendPersonalMessageForm").submit(function() {
		ajaxSendMessage();
		return false;
	});
</script>
