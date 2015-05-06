<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<h3>
				<spring:message code="administration.stats.metrics.title" />
			</h3>
			<hr>
			<ul class="fa-ul">
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <spring:message code="administration.stats.metrics.numberOfApplications" arguments="${numberOfApplications},${numberOfApplicationsInactive}" /></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <spring:message code="administration.stats.metrics.numberOfUsers" arguments="${numberOfUsers},${numberOfUsersInactive}" /></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <spring:message code="administration.stats.metrics.numberOfSocialUsers" arguments="${numberOfSocialUsers},${numberOfFacebookSocialUsers},${numberOfLinkedInSocialUsers},${numberOfGoogleSocialUsers},${numberOfTwitterSocialUsers}" /></li>
				<li><i class="fa-li text-color fa fa-chevron-circle-right fa-1x"></i> <spring:message code="administration.stats.metrics.numberOfAccountDeletions" arguments="${numberOfAccountDeletions}" /></li>
			</ul>
			<br />
			<h3>
				<spring:message code="administration.stats.topFollowedApps" />
			</h3>
			<hr>
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr class="active">
							<th><spring:message code="administration.stats.table.topFollowedApps.head.application" /></th>
							<th width="250px"><spring:message code="administration.stats.table.topFollowedApps.head.nbFollowers" /> <i class="fa fa-sort-numeric-desc"></i></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${topFollowedApplications}" var="topFollowedApplication">
							<tr>
								<td>${topFollowedApplication.applicationName}</td>
								<td>${topFollowedApplication.nbFollowers}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br />
				<h3>
					<spring:message code="administration.stats.topFollowers" />
				</h3>
				<hr>
				<div class="table-responsive">
					<table class="table">
						<thead>
							<tr class="active">
								<th><spring:message code="administration.stats.table.topFollowers.head.follower.name" /></th>
								<th><spring:message code="administration.stats.table.topFollowers.head.follower.email" /></th>
								<th width="250px"><spring:message code="administration.stats.table.topFollowers.head.nbApplications" /> <i class="fa fa-sort-numeric-desc"></i></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${topFollowers}" var="topFollower">
								<tr>
									<td>${topFollower.name}</td>
									<td>${topFollower.email}</td>
									<td>${topFollower.nbApplications}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<br />
				<h3>
					<spring:message code="administration.stats.latestUserCreations" />
				</h3>
				<hr>
				<div class="table-responsive">
					<table class="table">
						<thead>
							<tr class="active">
								<th><spring:message code="administration.stats.table.latestUserCreations.head.creationDate" /> <i class="fa fa-sort-desc"></i></th>
								<th><spring:message code="administration.stats.table.latestUserCreations.head.active" /></th>
								<th><spring:message code="administration.stats.table.latestUserCreations.head.name" /></th>
								<th><spring:message code="administration.stats.table.latestUserCreations.head.email" /></th>
								<th><spring:message code="administration.stats.table.latestUserCreations.head.langEmail" /></th>
								<th><spring:message code="administration.stats.table.latestUserCreations.head.langUpdate" /></th>
								<th><spring:message code="administration.stats.table.latestUserCreations.head.socialMediaService" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${latestUserCreations}" var="latestUserCreation">
								<tr>
									<td><fmt:formatDate value="${latestUserCreation.creationDate}" type="both" timeStyle="short" /></td>
									<td>${latestUserCreation.active}</td>
									<td>${latestUserCreation.name}</td>
									<td>${latestUserCreation.email}</td>
									<td>${latestUserCreation.langEmail}</td>
									<td>${latestUserCreation.langUpdate}</td>
									<td>${latestUserCreation.socialMediaService}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<br />
				<h3>
					<spring:message code="administration.stats.latestAccountDeletions" />
				</h3>
				<hr>
				<div class="table-responsive">
					<table class="table">
						<thead>
							<tr class="active">
								<th><spring:message code="administration.stats.table.latestAccountDeletions.head.removeDate" /> <i class="fa fa-sort-desc"></i></th>
								<th><spring:message code="administration.stats.table.latestAccountDeletions.head.feedback" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${latestAccountDeletions}" var="latestAccountDeletion">
								<tr>
									<td><fmt:formatDate value="${latestAccountDeletion.removeDate}" type="both" timeStyle="short" /></td>
									<td>${latestAccountDeletion.feedback}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>