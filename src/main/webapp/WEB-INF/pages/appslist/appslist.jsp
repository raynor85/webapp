<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.updapy.model.ApplicationDescription"%>
<%@ page import="java.util.Locale"%>

<c:set var="nbAppAvailable">${fn:length(applicationDescriptions)}</c:set>
<c:set var="lang" value="${lang}" />

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
	<fieldset>
		<legend class="legend">
			<spring:message code="appslist.filter.title" />
		</legend>
		<div class="row rowWithPadding" style="margin-top: -7px !important;">
			<div class="inner-addon left-addon col-md-4">
				<i class="fa fa-search"></i> <input id="filter" type="search" class="form-control filter" placeholder="${filterPlaceholder}">
			</div>
			<div id="filter-count" class="col-md-3">&nbsp;</div>
		</div>
	</fieldset>

	<div class="row">
		<div class="col-sm-12">
			<br />
			<div class="panel-group" id="accordion">
				<c:forEach items="${applicationDescriptions}" var="applicationDescription" varStatus="i">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="panel-app-name" data-toggle="collapse" href="#collapse${i.count}"> ${applicationDescription.application.name} </a> <a class="pull-right" href="${applicationDescription.website}" target="_blank" title="<spring:message code="appslist.website" />"> <i class="fa fa-external-link"></i>
								</a>
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
	$("#filter").keydown(function() {
		setTimeout(function() {
			filterApps($("#filter").val());
		}, 500); // timeout needed to let the time for the field to be populated
	});
	function filterApps(filter) {
		// retrieve the input field text and reset the count to zero
		var count = 0;
		// loop through the app list
		$("#accordion div.panel").each(
				function() {
					// if the name of the glossary term does not contain the text phrase fade it out
					if (jQuery(this).find("a.panel-app-name").text().search(
							new RegExp(filter, "i")) < 0) {
						$(this).fadeOut();

						// show the list item if the phrase matches and increase the count by 1
					} else {
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
