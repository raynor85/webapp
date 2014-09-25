<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.updapy.model.ApplicationDescription"%>
<%@ page import="java.util.Locale"%>

<c:set var="lang" value="${lang}" />
<c:set var="applicationDescription" value="${applicationDescription}" />

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<h3>
				${applicationDescription.application.name} <small><spring:message code="appslist.detail.subtitle" /></small>
			</h3>
			<hr>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<br />
			<div class="panel-group">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							${applicationDescription.application.name} <a class="pull-right" style="margin-left: 15px;" href="${applicationDescription.application.website}" target="_blank" title="<spring:message code="appslist.website" />"> <i class="fa fa-external-link"></i></a>
							<c:choose>
								<c:when test="${applicationDescription.application.type == COMMERCIAL}">
									<c:set var="styleBadgeType" value="warning" />
								</c:when>
								<c:otherwise>
									<c:set var="styleBadgeType" value="success" />
								</c:otherwise>
							</c:choose>
							<span class="badge${applicationDescription.application.type} label label-${styleBadgeType} pull-right"> <i class="fa fa-shopping-cart" style="margin-right: 2px;"></i> <spring:message code="appslist.type.${applicationDescription.application.type}" /></span>
						</h4>
					</div>
					<div class="panel-collapse">
						<div class="panel-body">
							<div class="media">
								<a class="pull-left" href="${applicationDescription.application.website}"> <img class="media-object" src="<spring:url value="/resources/img/application/small/${applicationDescription.application.iconFilename}" />" alt="${applicationDescription.application.name}">
								</a>
								<div class="media-body">
									<%
										ApplicationDescription applicationDescription = (ApplicationDescription) pageContext.getAttribute("applicationDescription");
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
											<span class="badge${applicationDescription.application.category} label label-default"><i class="fa fa-tag" style="margin-right: 2px;"></i> <spring:message code="appslist.category.${applicationDescription.application.category}" /></span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br />
	<div class="row rowWithPadding" align="center">
		<div class="button-ladda">
			<button type="button" class="btn-color ladda-button" onclick="location.href='${root}/applications';">
				<spring:message code="appslist.detail.showall.button" />
			</button>
		</div>
	</div>
</div>
