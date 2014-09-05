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
					<div class="alert alert-info">
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
					<div class="alert alert-info">
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
			<div class="row rowWithPadding" style="margin-top: -7px !important;">
				<div class="col-sm-4 col-md-3 col-lg-3">
					<label for="applicationCategory" style="min-width: 100px;"><spring:message code="appslist.filter.application.category" /></label> <select class="selectpicker" id="applicationCategory">
						<option value="ALL"><spring:message code="appslist.filter.application.category.all" /></option>
						<c:forEach items="${applicationCategories}" var="applicationCategory">
							<option value="${applicationCategory}"><spring:message code="appslist.category.${applicationCategory}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-4 col-md-3 col-lg-3">
					<label for="applicationType" style="min-width: 100px;"><spring:message code="appslist.filter.application.type" /></label> <select class="selectpicker" id="applicationType">
						<option value="ALL"><spring:message code="appslist.filter.application.type.all" /></option>
						<c:forEach items="${applicationTypes}" var="applicationType">
							<option value="${applicationType}"><spring:message code="appslist.type.${applicationType}" /></option>
						</c:forEach>
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
					<div class="inner-addon left-addon col-sm-6 col-md-5 col-lg-4">
						<i class="fa fa-search"></i> <input id="filter" type="search" class="form-control filter" placeholder="${filterPlaceholder}">
					</div>
					<div id="filter-count" class="col-md-3">&nbsp;</div>
				</div>
			</div>
		</fieldset>
	</div>

	<div class="row">
		<div class="col-sm-12">
			<br />
			<div class="panel-group" id="accordion">
				<c:forEach items="${applicationDescriptions}" var="applicationDescription" varStatus="i">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="panel-app-name" data-toggle="collapse" href="#collapse${i.count}"> ${applicationDescription.application.name} </a> <a class="pull-right" style="margin-left: 15px;" href="${applicationDescription.website}" target="_blank" title="<spring:message code="appslist.website" />"> <i class="fa fa-external-link"></i></a>
								<c:choose>
									<c:when test="${applicationDescription.application.type == COMMERCIAL}">
										<c:set var="styleBadgeType" value="danger" />
									</c:when>
									<c:otherwise>
										<c:set var="styleBadgeType" value="success" />
									</c:otherwise>
								</c:choose>
								<span class="badge${applicationDescription.application.type} label label-${styleBadgeType} pull-right"> <i class="fa fa-shopping-cart"></i> <spring:message code="appslist.type.${applicationDescription.application.type}" /></span>
							</h4>
						</div>
						<div id="collapse${i.count}" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="media">
									<a class="pull-left" href="${applicationDescription.website}"> <img class="media-object" src="<spring:url value="/resources/img/application/small/${applicationDescription.application.iconFilename}" />" alt="${applicationDescription.application.name}">
									</a>
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
												<span class="badge${applicationDescription.application.category} label label-default"><i class="fa fa-tag"></i> <spring:message code="appslist.category.${applicationDescription.application.category}" /></span>
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
	// activate elements
	$(".selectpicker").selectpicker();

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
							}

						});
		updateCounter(count);
	}
	function updateCounter(count) {
		var app = "application";
		if (count >= 2) {
			app += "s";
		}
		var found = "<spring:message code='appslist.filter.found' />";
		$("#filter-count").html(
				"<span class='label label-info'>" + count + "</span> <small>"
						+ app + " " + found + "</small>");
	}
	$(document).ready(function() {
		updateCounter('${nbAppAvailable}');
	});
</script>
