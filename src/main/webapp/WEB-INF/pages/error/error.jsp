<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<div class="not-found">
				<div class="not-found-header">
					<span class="background-color"> 
						<i class="fa fa-exclamation fa-5x text-white random animated2 rotateIn"></i> 
						<i class="fa fa-frown-o fa-5x text-white random animated2 rotateIn"></i>
						<i class="fa fa-warning fa-5x text-white random animated2 rotateIn"></i> 
					</span>
					<span class="background-color">
						<i class="fa fa-exclamation fa-5x text-white random2 animated2 rotateIn delay1"></i>
						<i class="fa fa-frown-o fa-5x text-white random2 animated2 rotateIn delay1"></i>
						<i class="fa fa-warning fa-5x text-white random2 animated2 rotateIn delay1"></i> 
					</span>
					<span class="background-color">
						<i class="fa fa-exclamation fa-5x text-white random3 animated2 rotateIn delay2"></i>
						<i class="fa fa-frown-o fa-5x text-white random3 animated2 rotateIn delay2"></i>
						<i class="fa fa-warning fa-5x text-white random3 animated2 rotateIn delay2"></i> 
					</span>
				</div>
				<h3 class="text-center">
					<spring:message code="error.description.short" />
				</h3>
				<p class="text-center text-muted">
					<spring:message code="error.description.long" arguments="${root}" />
				</p>
			</div>
		</div>
	</div>
</div>
