package com.updapy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.service.batch.ApplicationVersionScheduler;
import com.updapy.util.JsonResponseUtils;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

	@Autowired
	private JsonResponseUtils jsonResponseUtils;

	@Autowired
	private ApplicationVersionScheduler applicationVersionScheduler;

	@RequestMapping(value = "/repository/update")
	public @ResponseBody
	JsonResponse updateRepository() {
		applicationVersionScheduler.updateApplicationRepositoryAndCreateEmailSingleUpdates();
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.repository.update.confirm");
	}

}
