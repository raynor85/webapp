<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${lang == 'fr'}">
		<c:set var="langFb" value="fr_FR" />
	</c:when>
	<c:otherwise>
		<c:set var="langFb" value="en_US" />
	</c:otherwise>
</c:choose>

<!-- Manual social buttons - must be before -->
<script>
	// Facebook
	(function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id))
			return;
		js = d.createElement(s);
		js.id = id;
		js.src = "//connect.facebook.net/${langFb}/sdk.js#xfbml=1&appId=242605515939525&version=v2.0";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
</script>

<div class="footer-wrapper">
	<hr>
	<div class="container">
		<footer>
			<ul class="list-inline pull-left">
				<li><a href="${root}/privacy"><spring:message code="footer.privacy" /></a></li>
				<li><span class="text-muted">I</span></li>
				<li><a href="${root}/faq"><spring:message code="footer.faq" /></a></li>
				<li><span class="text-muted">I</span></li>
				<li class="hidden-on-xs"><span class="text-muted"><spring:message code="footer.follow.part1" /></span></li>
				<li class="hidden-on-xs"><a href="<spring:message code="application.twitter.url" />" class="text-muted fa fa-twitter fa-2x no-underline" title="<spring:message code="footer.follow.part1" /> <spring:message code="footer.follow.twitter" />"></a></li>
				<li class="hidden-on-xs"><span class="text-muted"><spring:message code="footer.follow.part2" /></span></li>
				<li class="hidden-on-xs"><a href="<spring:message code="application.facebook.url" />" class="text-muted fa fa-facebook fa-2x no-underline" title="<spring:message code="footer.follow.part1" /> <spring:message code="footer.follow.facebook" />"></a></li>
			</ul>
			<ul class="list-inline pull-right-xs footerAwesome">
				<li><div class="fb-like" data-href="https://www.facebook.com/updapy" data-layout="button_count" data-action="like" data-show-faces="false" data-share="true"></div></li>
				<%-- <li><span class="text-muted"><spring:message code="footer.version" /> <spring:message code="application.version" /></span></li> --%>
				<%-- <li><span class="text-muted">I</span></li> --%>
				<li><span class="text-muted"><spring:message code="footer.copyright" /></span></li>
			</ul>
			<div class="clearfix"></div>
		</footer>
	</div>
</div>
