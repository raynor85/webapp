<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="end" value="13" />

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<h3>
				<spring:message code="faq.title" />
				<small><spring:message code="faq.subtitle" /></small>
			</h3>
			<hr>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<br />
			<div class="panel-group" id="accordion">
				<c:forEach begin="1" end="${end}" var="i">
					<c:set var="in" scope="page" value="" />
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" href="#collapse${i}"> <spring:message code="faq.question.${i}" />
								</a>
							</h4>
						</div>
						<c:if test="${i == 1}">
							<c:set var="in" value="in" />
						</c:if>
						<div id="collapse${i}" class="panel-collapse collapse ${in}">
							<div class="panel-body">
								<spring:message code="faq.answer.${i}" />
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
