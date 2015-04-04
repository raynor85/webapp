<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="emailReadonly" value="false" />
<c:if test="${isAuthenticated}">
	<c:set var="emailReadonly" value="true" />
</c:if>

<div class="container">
	<div class="row">
		<div class="col-sm-10 col-md-9 col-lg-8 col-sm-offset-1 col-md-offset-2 col-lg-offset-2">
			<h2 class="text-center">
				<spring:message code="contact.title" />
			</h2>
			<p class="text-muted text-center">
				<spring:message code="contact.description" />
			</p>
			<div class="form-white-contact form-contact">
				<form:form id="sendMessageForm" commandName="sendMessage" action="${root}/contact/send">
					<div id="sendMessageResponse" style="margin-top: 10px;">&nbsp;</div>
					<div class="form-group" style="margin-top: 20px;">
						<label for="email"><spring:message code="contact.field.email" /> </label>
						<c:set var="emailPlaceholder">
							<spring:message code="contact.field.email.tip" />
						</c:set>
						<div class="input-group">
							<span class="input-group-addon">@</span>
							<form:input path="email" class="form-control show" id="email" type="email" placeholder="${emailPlaceholder}" readonly="${emailReadonly}" />
						</div>
					</div>
					<div class="form-group">
						<label for="message"><spring:message code="contact.field.message" /> </label>
						<c:set var="messagePlaceholder">
							<spring:message code="contact.field.message.tip" />
						</c:set>
						<form:textarea path="message" class="form-control" id="message" rows="3" placeholder="${messagePlaceholder}" />
						<form:hidden path="anonymous" id="anonymous" />
					</div>
					<button type="button" id="sendMessageButton" class="btn-block btn-color ladda-button" data-style="zoom-in" onclick="ajaxSendMessage();">
						<spring:message code="contact.send.button" />
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
	function ajaxSendMessage() {
		var json = {
			"email" : $("#email").val(),
			"message" : $("#message").val(),
			"anonymous" : $("#anonymous").val()
		};
		ajaxCallPost("#sendMessageButton", "#sendMessageForm", json,
				"#sendMessageResponse", clearMessage);
	};
	var clearMessage = function() {
		$("#message").val("");
	};
	$("#sendMessageForm").submit(function() {
		ajaxSendMessage();
		return false;
	});
</script>
