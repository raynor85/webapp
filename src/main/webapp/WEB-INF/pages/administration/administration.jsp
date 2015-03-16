<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<div id="jobAdministrationResponse"></div>
			<h3>
				<spring:message code="administration.action.title" />
				<small><spring:message code="administration.action.subtitle" /></small>
			</h3>
			<hr>
			<ul class="fa-ul">
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/clear/cache', 'cache-clear');"><spring:message code="administration.action.cache.clear.button" /></a> <i id="cache-clear" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/repository/update', 'repository-update');"><spring:message code="administration.action.repository.update.button" /></a> <i id="repository-update" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/repository/check', 'repository-check');"><spring:message code="administration.action.repository.check.button" /></a> <i id="repository-check" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/email/application/added', 'email-added-app');"><spring:message code="administration.action.email.application.added.button" /></a> <i id="email-added-app" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/email/application/deleted', 'email-deleted-app');"><spring:message code="administration.action.email.application.deleted.button" /></a> <i id="email-deleted-app" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/email/newsletter', 'email-newsletter');"><spring:message code="administration.action.email.newsletter.button" /></a> <i id="email-newsletter" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <a href="javascript:runJobAdministration('${root}/administration/email/send', 'email-send');"><spring:message code="administration.action.email.send.button" /></a> <i id="email-send" style="color: #777; display: none;" class="fa fa-refresh fa-spin"></i></li>
			</ul>
			<br />

			<h3>
				<spring:message code="administration.error.title" />
				<small><spring:message code="administration.error.subtitle" /></small>
			</h3>
			<hr>
			<div class="table-responsive">
				<table class="table table-hover">
					<thead>
						<tr class="active">
							<th><spring:message code="administration.error.table.head.application" /></th>
							<th><spring:message code="administration.error.table.head.type" /></th>
							<th><spring:message code="administration.error.table.head.counter" /> <i class="fa fa-sort-numeric-desc"></i></th>
							<th><spring:message code="administration.error.table.head.version" /></th>
							<th><spring:message code="administration.error.table.head.globalUrl" /></th>
							<th><spring:message code="administration.error.table.head.version32Url" /></th>
							<th><spring:message code="administration.error.table.head.version64Url" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${administrationRetrievalErrors}" var="administrationRetrievalError">
							<tr>
								<td>${administrationRetrievalError.retrievalError.application.name}</td>
								<td><spring:message code="administration.error.type.${administrationRetrievalError.retrievalError.typeLastError}" /></td>
								<td>${administrationRetrievalError.retrievalError.count}</td>
								<td>${administrationRetrievalError.latestVersion.versionNumber}</td>
								<td><a href="${administrationRetrievalError.retrievalError.application.globalUrl}" target="_blank"><i class="fa fa-external-link"></i></a></td>
								<td><a href="${administrationRetrievalError.latestVersion.win32UrlEn}" target="_blank"><i class="fa fa-external-link"></i></a></td>
								<td><c:choose>
										<c:when test="${not empty administrationRetrievalError.latestVersion.win64UrlEn}">
											<a href="${administrationRetrievalError.latestVersion.win64UrlEn}" target="_blank"><i class="fa fa-external-link"></i></a>
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	function runJobAdministration(url, idspin) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			type : "GET",
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
				$("#" + idspin).show();
			},
			url : url,
			contentType : "text/plain",
			cache : false,
			success : function(response) {
				var type = "";
				if (response.status == "SUCCESS") {
					type = "success";
				} else if (response.status == "FAIL") {
					type = "danger";
				}
				responseInDiv = "<div class='alert alert-" + type
						+ " alert-dismissable'>";
				responseInDiv += "<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";
				for (var i = 0; i < response.result.length; i++) {
					responseInDiv += response.result[i] + "<br />";
				}
				responseInDiv += "</div>";
				$("#" + idspin).hide();
				$("#jobAdministrationResponse").html(responseInDiv);
			}
		});
	};
</script>