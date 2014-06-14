package com.updapy.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.updapy.api.exception.ResourceNotFoundException;
import com.updapy.api.exception.UnauthorizedException;
import com.updapy.api.model.ApplicationNameResource;
import com.updapy.api.model.ApplicationNameResources;
import com.updapy.api.model.LatestApplicationVersion;
import com.updapy.model.ApplicationVersion;
import com.updapy.service.ApplicationService;
import com.updapy.service.UserService;
import com.updapy.util.DozerHelper;

@Controller
@RequestMapping("/1.0")
public class ApiV1Controller {

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private DozerHelper dozerHelper;

	@RequestMapping(value = "/applicationnames")
	public @ResponseBody
	HttpEntity<ApplicationNameResources> getApplicationNames(@RequestParam(value = "key", required = true) String key) {
		if (!userService.isValidApiKey(key)) {
			throw new UnauthorizedException();
		}
		ApplicationNameResources applicationNameResources = new ApplicationNameResources();
		applicationNameResources.add(linkTo(methodOn(ApiV1Controller.class).getApplicationNames(key)).withSelfRel());
		List<ApplicationNameResource> applicationNames = dozerHelper.map(applicationService.getApplications(), ApplicationNameResource.class);
		for (ApplicationNameResource applicationName : applicationNames) {
			applicationName.add(linkTo(methodOn(ApiV1Controller.class).getLastVersion(applicationName.getApiName(), key)).withRel("latestVersion"));
		}
		applicationNameResources.setApplicationNames(applicationNames);
		return new ResponseEntity<ApplicationNameResources>(applicationNameResources, HttpStatus.OK);
	}

	@RequestMapping(value = "/lastversion")
	public @ResponseBody
	HttpEntity<LatestApplicationVersion> getLastVersion(@RequestParam(value = "application_name", required = true) String apiName, @RequestParam(value = "key", required = true) String key) {
		if (!userService.isValidApiKey(key)) {
			throw new UnauthorizedException();
		}
		ApplicationVersion version = applicationService.getLatestVersion(apiName);
		if (version == null) {
			throw new ResourceNotFoundException();
		}
		LatestApplicationVersion latestVersion = dozerHelper.map(version, LatestApplicationVersion.class);
		latestVersion.add(linkTo(methodOn(ApiV1Controller.class).getLastVersion(apiName, key)).withSelfRel());
		return new ResponseEntity<LatestApplicationVersion>(latestVersion, HttpStatus.OK);
	}

}
