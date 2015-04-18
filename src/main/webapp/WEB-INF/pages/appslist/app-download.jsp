<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="container">
	<c:if test="${fn:length(otherDownloadLinks) > 0}">
		<div class="row rowWithPadding" style="margin-bottom: -30px !important;">
			<div class="center-block" style="<spring:message code="appslist.detail.download.alternative.title.style" />">
				<div class="alert alert-info" role="alert">
					<span class="sr-only">Info:</span>
					<spring:message code="appslist.detail.download.alternative.title" />
					<br>
					<ul class="fa-ul">
						<c:forEach items="${otherDownloadLinks}" var="otherDownloadLink">
							<li><i class="fa-li fa fa-chevron-circle-right fa-1x"></i><a target="_blank" href="${otherDownloadLink.url}"><spring:message code="appslist.detail.download.alternative.${otherDownloadLink.messageKey}" /></a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</c:if>
	<%@ include file="app-detail.jspf"%>
	<br />
	<div class="row rowWithPadding" align="center">
		<div class="button-ladda">
			<button type="button" class="btn-color ladda-button" onclick="location.href='${mainDownloadLink}';">
				<spring:message code="appslist.detail.download.button" arguments="${versionNumber}" />
			</button>
		</div>
	</div>
</div>
