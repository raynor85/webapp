<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="footer-wrapper">
	<hr>
	<div class="container">
		<footer>
			<ul class="list-inline pull-left">
				<li><a href="${root}/privacy"><spring:message code="footer.privacy" /></a></li>
				<li><span class="text-muted">I</span></li>
				<li><a href="${root}/faq"><spring:message code="footer.faq" /></a></li>
				<li><span class="text-muted">I</span></li>
				<li><span class="text-muted"><spring:message code="footer.follow.part1" /></span></li>
				<li><a href="<spring:message code="application.twitter.url" />" class="text-muted fa fa-twitter fa-2x no-underline" title="<spring:message code="footer.follow.part1" /> <spring:message code="footer.follow.twitter" />"></a></li>
				<li><span class="text-muted"><spring:message code="footer.follow.part2" /></span></li>
				<li><a href="<spring:message code="application.facebook.url" />" class="text-muted fa fa-facebook fa-2x no-underline" title="<spring:message code="footer.follow.part1" /> <spring:message code="footer.follow.facebook" />"></a></li>
			</ul>
			<ul class="list-inline pull-right-xs" style="margin-top: 6px;">
				<%-- <li><span class="text-muted"><spring:message code="footer.version" /> <spring:message code="application.version" /></span></li> --%>
				<!-- <li><span class="text-muted">I</span></li> -->
				<li><span class="text-muted"><spring:message code="footer.copyright" /></span></li>
			</ul>
			<div class="clearfix"></div>
		</footer>
	</div>
</div>
