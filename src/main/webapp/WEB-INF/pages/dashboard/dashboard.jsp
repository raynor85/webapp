<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<div class="row">
		<button class="btn-color ladda-button" data-toggle="modal" data-target="#followApplicationsModal">
			<spring:message code="dashboard.applications.follow.button" />
		</button>
	</div>
</div>

<!-- Modal: Follow application -->
<div class="modal fade" id="followApplicationsModal" tabindex="-1" role="dialog" aria-labelledby="followApplicationsModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form:form id="followApplicationsForm" commandName="followApplications" action="${root}/dashboard/followApplications">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="followApplicationsModalLabel">
						<spring:message code="dashboard.applications.followApplications.title" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message code="dashboard.applications.followApplications.first.description" />
					<br /> <br />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-left btn-cancel-next-ladda" data-dismiss="modal">
						<spring:message code="dashboard.applications.followApplications.button.cancel" />
					</button>
					<button type="submit" class="btn-color ladda-button">
						<spring:message code="dashboard.applications.followApplications.button.confirm" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>