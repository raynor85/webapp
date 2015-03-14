<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<div class="row">
		<!-- Sign In form -->
		<div class="col-sm-5 col-sm-offset-1">
			<h3>
				<spring:message code="sign.in.title" />
			</h3>
			<p class="text-muted">
				<spring:message code="sign.in.subtitle" />
			</p>
			<c:set var="requestUri" value="signGlobal" />
			<%@ include file="sign-in.jspf"%>
			<h3>
				<spring:message code="sign.social.title" />
			</h3>
			<p class="text-muted">
				<spring:message code="sign.social.subtitle" />
			</p>
			<%@ include file="sign-social.jspf"%>
		</div>
		<!-- Sign Up form -->
		<div class="col-sm-5">
			<h3 class="text-right-xs">
				<spring:message code="sign.up.title" />
			</h3>
			<p class="text-muted text-right-xs">
				<spring:message code="sign.up.subtitle" />
			</p>
			<%@ include file="sign-up.jspf"%>
		</div>
	</div>
</div>

<script type="text/javascript">
	// focus
	mainFocus();
	function mainFocus() {
		setTimeout(function() { $("#username").focus(); }, 1000);
	}
</script>