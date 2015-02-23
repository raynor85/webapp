<div class="container">
	<%@ include file="app-detail.jspf"%>
	<br />
	<div class="row rowWithPadding" align="center">
		<div class="button-ladda">
			<button type="button" class="btn-color ladda-button" onclick="location.href='${downloadLink}';">
				<spring:message code="appslist.detail.download.button" arguments="${versionNumber}" />
			</button>
		</div>
	</div>
</div>
