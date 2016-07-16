package com.updapy.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.updapy.exception.ResourceNotFoundException;
import com.updapy.exception.UnauthorizedException;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.api.ApplicationNameResource;
import com.updapy.model.api.ApplicationNameResources;
import com.updapy.model.api.LatestApplicationVersion;
import com.updapy.service.ApplicationService;
import com.updapy.service.UserService;
import com.updapy.util.DozerHelper;

@Controller
@RequestMapping("/api/v1")
public class ApiV1Controller {

	@Inject
	private UserService userService;

	@Inject
	private ApplicationService applicationService;

	@Inject
	private DozerHelper dozerHelper;

	@RequestMapping(value = "/application-names")
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

	@RequestMapping(value = "/last-version")
	public @ResponseBody
	HttpEntity<LatestApplicationVersion> getLastVersion(@RequestParam(value = "application", required = true) String apiName, @RequestParam(value = "key", required = true) String key) {
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
