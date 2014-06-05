package com.updapy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.updapy.api.exception.ResourceNotFoundException;
import com.updapy.api.exception.UnauthorizedException;
import com.updapy.api.model.ApplicationName;
import com.updapy.api.model.LatestApplicationVersion;
import com.updapy.model.ApplicationVersion;
import com.updapy.service.ApplicationService;
import com.updapy.service.UserService;
import com.updapy.util.DozerHelper;

@Controller
@RequestMapping("1.0")
public class ApiV1Controller {

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private DozerHelper dozerHelper;

	@RequestMapping(value = "applicationnames")
	public @ResponseBody
	List<ApplicationName> getApplicationNames(@RequestParam(value = "key", required = true) String key) {
		if (!userService.isValidApiKey(key)) {
			throw new UnauthorizedException();
		}
		return dozerHelper.map(applicationService.getApplicationReferences(), ApplicationName.class);
	}

	@RequestMapping(value = "lastversion")
	public @ResponseBody
	LatestApplicationVersion getLastVersion(@RequestParam(value = "key", required = true) String key, @RequestParam(value = "application_name", required = true) String apiName) {
		if (!userService.isValidApiKey(key)) {
			throw new UnauthorizedException();
		}
		ApplicationVersion applicationVersion = applicationService.getLatestApplicationVersion(apiName);
		if (applicationVersion == null) {
			throw new ResourceNotFoundException();
		}
		return dozerHelper.map(applicationVersion, LatestApplicationVersion.class);
	}

}
