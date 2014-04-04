<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
	<div class="navbar-header">
	  <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
		<span class="sr-only">Toggle navigation</span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
	  </button>
	  <a class="navbar-brand logo" href="${root}/"><spring:message code="application.name" /></a>
	</div>
	<div class="navbar-collapse collapse">
		<ul class="nav navbar-nav">
		  <li class="dropdown">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="menu.language" /> <b class="caret"></b></a>
			<ul class="dropdown-menu">
			  <li><a href="${root}?lang=en"><spring:message code="menu.language.en" /></a></li>
			  <li><a href="${root}?lang=fr"><spring:message code="menu.language.fr" /></a></li>
			</ul>
		  </li>
		  <li><a href="${root}/faq/"><spring:message code="menu.faq" /></a></li>
		  <li><a href="${root}/privacy/"><spring:message code="menu.privacy" /></a></li>
		</ul>
	</div>
  </div>
</div>
