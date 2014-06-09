package com.updapy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationFollow;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.repository.ApplicationFollowRepository;
import com.updapy.repository.ApplicationReferenceRepository;
import com.updapy.repository.ApplicationVersionRepository;
import com.updapy.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationReferenceRepository applicationReferenceRepository;

	@Autowired
	private ApplicationVersionRepository applicationVersionRepository;

	@Autowired
	private ApplicationFollowRepository applicationFollowRepository;

	@Override
	public List<ApplicationReference> getApplicationReferences() {
		return applicationReferenceRepository.findByActiveTrueOrderByApiNameAsc();
	}

	@Override
	public ApplicationVersion getLatestApplicationVersion(String apiName) {
		ApplicationReference applicationReference = applicationReferenceRepository.findByApiNameAndActiveTrue(apiName);
		return getLatestApplicationVersion(applicationReference);
	}

	@Override
	public ApplicationVersion addApplicationVersion(ApplicationVersion applicationVersion) {
		return applicationVersionRepository.saveAndFlush(applicationVersion);
	}

	@Override
	public ApplicationVersion getLatestApplicationVersion(ApplicationReference applicationReference) {
		return applicationVersionRepository.findLatestByApplicationReference(applicationReference);
	}

	@Override
	public ApplicationVersion getLatestApplicationVersionNoCache(ApplicationReference applicationReference) {
		return getLatestApplicationVersion(applicationReference);
	}

	@Override
	public ApplicationReference getApplicationReference(String apiName) {
		return applicationReferenceRepository.findByApiNameAndActiveTrue(apiName);
	}

	@Override
	public ApplicationFollow saveApplicationFollow(ApplicationFollow applicationFollow) {
		return applicationFollowRepository.saveAndFlush(applicationFollow);
	}

	@Override
	public void deleteApplicationFollow(ApplicationFollow applicationFollow) {
		applicationFollowRepository.delete(applicationFollow);
	}

	@Override
	public void enableEmailAlertApplicationFollow(ApplicationFollow applicationFollow) {
		applicationFollow.setEmailNotificationActive(true);
		saveApplicationFollow(applicationFollow);
	}

	@Override
	public void disableEmailAlertApplicationFollow(ApplicationFollow applicationFollow) {
		applicationFollow.setEmailNotificationActive(false);
		saveApplicationFollow(applicationFollow);
	}
}
