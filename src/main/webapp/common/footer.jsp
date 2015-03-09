<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="footer-wrapper">
	<hr>
	<div class="container">
		<footer>
			<ul class="list-inline pull-left  hidden-on-xxs">
				<li><a href="${root}/applications"><spring:message code="footer.appslist" /></a></li>
				<li><span class="text-muted">&#124;</span></li>
				<li class="hidden-on-xs"><a href="${root}/faq"><spring:message code="footer.faq" /></a></li>
				<li class="hidden-on-xs"><span class="text-muted">&#124;</span></li>
				<li><a href="${root}/privacy"><spring:message code="footer.privacy.short" /></a></li>
				<li><span class="text-muted">&#124;</span></li>
				<li><a href="${root}/contact"><spring:message code="footer.contact" /></a></li>
				<li><span class="text-muted">&#124;</span></li>
				<li><a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=M4URMQRZH7J2G"><spring:message code="footer.donate" /></a></li>
				<li class="hidden-on-xs"><span class="text-muted">&#124;</span></li>
				<li class="hidden-on-xs hidden-on-md"><span class="text-muted"><spring:message code="footer.follow.part1" /></span></li>
				<li class="hidden-on-xs"><a href="<spring:message code="application.twitter.url" />" class="text-muted fa fa-twitter fa-2x no-underline twitter-hover" title="<spring:message code="footer.follow.part1" /> <spring:message code="footer.follow.twitter" />"></a></li>
				<li class="hidden-on-xs hidden-on-md"><span class="text-muted"><spring:message code="footer.follow.part2" /></span></li>
				<li class="hidden-on-xs show-on-md"><span class="text-muted">&#124;</span></li>
				<li class="hidden-on-xs"><a href="<spring:message code="application.facebook.url" />" class="text-muted fa fa-facebook fa-2x no-underline facebook-hover" title="<spring:message code="footer.follow.part1" /> <spring:message code="footer.follow.facebook" />"></a></li>
			</ul>
			<ul class="list-inline pull-right footerAwesome hidden-on-xxs">
				<li><span class="text-muted"><spring:message code="footer.copyright" /></span></li>
			</ul>
			<ul class="list-unstyled fa-ul pull-left show-on-xxs">
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i><a href="${root}/applications"><spring:message code="footer.appslist" /></a></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i><a href="${root}/faq"><spring:message code="footer.faq" /></a></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i><a href="${root}/privacy"><spring:message code="footer.privacy" /></a></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i><a href="${root}/contact"><spring:message code="footer.contact" /></a></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i><a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=M4URMQRZH7J2G"><spring:message code="footer.donate" /></a></li>
			</ul>
			<ul class="list-unstyled pull-right show-on-xxs">
				<li><span class="text-muted"><spring:message code="footer.copyright" /></span></li>
			</ul>
			<div class="clearfix"></div>
		</footer>
	</div>
</div>
