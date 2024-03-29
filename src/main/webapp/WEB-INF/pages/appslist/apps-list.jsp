<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="com.updapy.model.ApplicationDescription"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.updapy.model.enumeration.ApplicationType"%>

<link href="<spring:url value="/resources/css/bootstrap-select.min.css" />" rel="stylesheet">
<script src="<spring:url value="/resources/js/bootstrap-select.min.js" />"></script>

<c:set var="nbAppAvailable">${fn:length(applicationDescriptions)}</c:set>
<c:set var="lang" value="${lang}" />
<c:set var="COMMERCIAL" value="<%=ApplicationType.COMMERCIAL%>" />

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<h3>
				<spring:message code="appslist.title" />
				<small><spring:message code="appslist.subtitle" /></small>
			</h3>
			<hr>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<c:choose>
				<c:when test="${not isAuthenticated}">
					<div class="alert alert-info" role="alert">
						<i class="fa fa-info-circle"></i>
						<span class="sr-only">Info:</span>
						<spring:message code="appslist.description.notRegistered" />
					</div>
					<br />
					<div class="col-sm-12 text-center">
						<a class="btn-color ladda-button" href="${root}/signup"><spring:message code="welcome.action" /></a>
					</div>
					<br />
					<br />
				</c:when>
				<c:otherwise>
					<div class="alert alert-info" role="alert">
						<i class="fa fa-info-circle"></i>
						<span class="sr-only">Info:</span>
						<spring:message code="appslist.description.registered" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<c:set var="filterPlaceholder">
		<spring:message code="appslist.filter.description" />
	</c:set>
	<div class="row rowWithPadding">
		<fieldset>
			<legend class="legend">
				<spring:message code="appslist.filter.title" />
			</legend>
			<div class="row rowWithPadding" style="margin-top: -7px !important; margin-bottom: 10px !important;">
				<div class="col-sm-4 col-md-3 col-lg-3 form-group">
					<label for="applicationCategory" style="min-width: 100px;"><spring:message code="appslist.filter.application.category" /></label> <select class="selectpicker" id="applicationCategory">
						<option value="ALL"><spring:message code="appslist.filter.application.category.all" /></option>
						<c:forEach items="${applicationCategories}" var="applicationCategory">
							<option value="${applicationCategory}"><spring:message code="appslist.category.${applicationCategory}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-4 col-md-3 col-lg-3 form-group">
					<label for="applicationType" style="min-width: 100px;"><spring:message code="appslist.filter.application.type" /></label> <select class="selectpicker" id="applicationType">
						<option value="ALL"><spring:message code="appslist.filter.application.type.all" /></option>
						<c:forEach items="${applicationTypes}" var="applicationType">
							<option value="${applicationType}"><spring:message code="appslist.type.${applicationType}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-4 col-md-3 col-lg-3 form-group">
					<label for="applicationRating" style="min-width: 100px;"><spring:message code="appslist.filter.application.rating" /></label> <select class="selectpicker" id="applicationRating">
						<option value="ALL"><spring:message code="appslist.filter.application.rating.all" /></option>
						<option value="1"><spring:message code="appslist.filter.application.rating.good" /></option>
						<option value="2"><spring:message code="appslist.filter.application.rating.average" /></option>
						<option value="3"><spring:message code="appslist.filter.application.rating.bad" /></option>
					</select>
				</div>
			</div>
			<div class="row rowWithPadding">
				<div class="col-sm-2">
					<label for="filter"><spring:message code="appslist.filter.name" /></label>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-9">
					<div class="col-sm-6 col-md-5 col-lg-4">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-search"></i></span>
							<input id="filter" type="search" class="form-control" placeholder="${filterPlaceholder}" />
						</div>
					</div>
					<div id="filter-count-list" class="col-sm-5">&nbsp;</div>
				</div>
			</div>
		</fieldset>
	</div>

	<script>
		// activate elements
		$(".selectpicker").selectpicker();
	</script>

	<div class="row">
		<div class="col-sm-12">
			<br />
			<div class="panel-group" id="accordion">
				<c:forEach items="${applicationDescriptions}" var="applicationDescription" varStatus="i">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="panel-app-name" data-toggle="collapse" href="#collapse${i.count}"> ${applicationDescription.application.name} </a> <a class="pull-right" style="margin-left: 15px;" href="${applicationDescription.application.website}" target="_blank" title="<spring:message code="appslist.website" />"> <i class="fa fa-external-link"></i></a>
								<c:choose>
									<c:when test="${applicationDescription.application.type == COMMERCIAL}">
										<c:set var="styleBadgeType" value="warning" />
									</c:when>
									<c:otherwise>
										<c:set var="styleBadgeType" value="success" />
									</c:otherwise>
								</c:choose>
								<span class="badge${applicationDescription.application.type} label label-${styleBadgeType} pull-right"> <i class="fa fa-shopping-cart" style="margin-right: 2px;"></i>&nbsp;<spring:message code="appslist.type.${applicationDescription.application.type}" /></span>
							</h4>
						</div>
						<div id="collapse${i.count}" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="media">
									<a class="pull-left media-object sprite sprite-${applicationDescription.application.apiName} sprite-app-list" href="${applicationDescription.application.website}"> </a>
									<div class="media-body">
										<%
											ApplicationDescription applicationDescription = (ApplicationDescription) pageContext
														.getAttribute("applicationDescription");
												Object lang = pageContext.getAttribute("lang");
												if (lang instanceof Locale) {
													lang = ((Locale) lang).getCountry();
												}
												if (((String) lang).equalsIgnoreCase("fr")) {
													out.println(applicationDescription.getDescriptionFr());
												} else {
													out.println(applicationDescription.getDescriptionEn());
												}
										%>
										<div class="row" style="margin-top: 15px; margin-bottom: 3px;">
											<div class="col-lg-2 pull-left">
												<span class="badge${applicationDescription.application.category} label label-default"><i class="fa fa-tag" style="margin-right: 2px;"></i>&nbsp;<spring:message code="appslist.category.${applicationDescription.application.category}" /></span>
												<span class="currentRating" style="display: none;">${applicationDescription.averageRating.scoreRoundedToHalf}</span>
											</div>
											<div class="col-lg-2 pull-right">
												<div class="pull-right" style="margin-left: 10px">
													<c:choose>
														<c:when test="${applicationDescription.averageRating.nbVotes > 1}">
															<spring:message code="appslist.detail.votes" arguments="${applicationDescription.averageRating.nbVotes}" />
														</c:when>
														<c:otherwise>
															<spring:message code="appslist.detail.vote" arguments="${applicationDescription.averageRating.nbVotes}" />
														</c:otherwise>
													</c:choose>
												</div>
												<div class="rating-input" style="cursor: default !important;" data-toggle="tooltip" data-placement="left" title="${applicationDescription.averageRating.scoreRounded}">
													<c:choose>
														<c:when test="${not empty applicationDescription.averageRating.scoreRoundedToHalf}">
															<c:forEach begin="1" end="${applicationDescription.averageRating.scoreRoundedToHalf}" var="i"><i class="fa fa-star"></i></c:forEach><c:if test="${applicationDescription.averageRating.scoreRoundedToHalf % 1 != 0}"><i class="fa fa-star-half-full"></i></c:if><c:forEach begin="${applicationDescription.averageRating.scoreRoundedToHalf + 1.5}" end="5" var="j"><i class="fa fa-star-o"></i></c:forEach>
														</c:when>
														<c:otherwise>
															<i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>

<script>
	// focus
	mainFocus();
	function mainFocus() {
		setTimeout(function() { $("#filter").focus(); }, 1000);
	}

	// filter by category or type
	$(".selectpicker").change(function() {
		setTimeout(function() {
			filterApps();
		}, 500); // timeout needed to let the time for the field to be populated
	});

	// filter by name
	$("#filter").keydown(function() {
		setTimeout(function() {
			filterApps();
		}, 500);
	});

	function filterApps() {
		// retrieve filters and reset the count to zero
		var filter = $("#filter").val();
		var category = $("#applicationCategory").val();
		var type = $("#applicationType").val();
		var rating = $("#applicationRating").val();
		var count = 0;
		// loop through the app list
		$("#accordion div.panel")
				.each(
						function() {
							var toFade = false;
							if (category != 'ALL') {
								// filter on category
								if ((jQuery(this))
										.find("span.badge" + category).length == 0) {
									toFade = true;
								}
							}
							if (type != 'ALL') {
								// filter on type
								if ((jQuery(this)).find("span.badge" + type).length == 0) {
									toFade = true;
								}
							}
							if (rating != 'ALL') {
								// filter on rating
								var minRating;
								var maxRating;
								if (rating == 1){
									minRating = 4;
									maxRating = 5.2;
								} else if (rating == 2){
									minRating = 2;
									maxRating = 3.7;
								} else {
									minRating = 0;
									maxRating = 1.8;
								}
								var currentRating = (jQuery(this)).find("span.currentRating").text();
								if (!currentRating || currentRating < minRating || currentRating > maxRating) {
									toFade = true;
								};
							}
							// filter on app name
							if (jQuery(this).find("a.panel-app-name").text()
									.search(new RegExp(filter, "i")) < 0) {
								toFade = true;
							}

							if (toFade) {
								$(this).fadeOut();
							} else {
								// show the app if all filters match and increase the count by 1
								$(this).show();
								count++;
							};

						});
		updateCounter(count);
	}
	function updateCounter(count) {
		var app = "application";
		if (count >= 2) {
			app += "s";
		}
		var found = "<spring:message code='appslist.filter.found' />";
		$("#filter-count-list").html(
				"<span class='label label-info'>" + count + "</span> <small>"
						+ app + " " + found + "</small>");
	}
	$(document).ready(function() {
		updateCounter('${nbAppAvailable}');
		$('[data-toggle="tooltip"]').tooltip();
	});
</script>
