<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

  <div class="container">
	<div class="row">
	  <div class="col-sm-12">
		<h3><spring:message code="sign.up.activate.title" /> <small><spring:message code="sign.up.activate.subtitle" /></small></h3>
			<hr>
		<div class="col-sm-12">
		  <spring:message code="sign.up.activate.description" arguments="test" />
		  <br /><br /><br />
			
		  <spring:message code="sign.up.activate.send.description" />
		  <br /><br />
		  <a class="btn btn-color" href="${root}/user/activate?email=test"><spring:message code="sign.up.activate.send.button" /></a>
		</div>
	  </div>
	</div>
  </div>
