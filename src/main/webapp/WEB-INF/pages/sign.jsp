<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

  <div class="container">
    <div class="row">
	  <!-- Sign In form -->
  	  <div class="col-sm-5 col-sm-offset-1">
        <h3><spring:message code="sign.in.title" /></h3>
	    <p class="text-muted">
	      <spring:message code="sign.in.subtitle" />
	    </p>
	    <%@ include file="sign-in.jspf" %>
	  </div>
	  <!-- Sign Up form -->
	  <div class="col-sm-5">
    	<h3 class="text-right-xs"><spring:message code="sign.up.title" /></h3>
		<p class="text-muted text-right-xs">
	  	 <spring:message code="sign.up.subtitle" />
		</p>
	    <%@ include file="sign-up.jspf" %>
	  </div>
	</div>
  </div>
