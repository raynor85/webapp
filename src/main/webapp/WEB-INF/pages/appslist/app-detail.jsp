<div class="container">
	<%@ include file="app-detail.jspf"%>
	<br />
	<div class="row rowWithPadding" align="center">
		<div class="button-ladda">
			<button type="button" class="btn-color ladda-button" onclick="location.href='${root}/applications';">
				<spring:message code="appslist.detail.showall.button" />
			</button>
		</div>
	</div>
</div>
