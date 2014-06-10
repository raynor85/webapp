package com.updapy.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.ApplicationFollow;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationRequest;
import com.updapy.model.ApplicationVersion;

@Transactional
public interface ApplicationService {

	@Cacheable(value = "applicationReferences", key = "'applicationReferences'")
	List<ApplicationReference> getApplicationReferences();

	@Cacheable(value = "applicationReferences", key = "#apiName")
	ApplicationReference getApplicationReference(String apiName);

	@CacheEvict(value = "applicationVersions", key = "#applicationVersion.reference.apiName")
	ApplicationVersion addApplicationVersion(ApplicationVersion applicationVersion);

	@Cacheable(value = "applicationVersions", key = "#apiName")
	ApplicationVersion getLatestApplicationVersion(String apiName);

	@Cacheable(value = "applicationVersions", key = "#applicationReference.apiName")
	ApplicationVersion getLatestApplicationVersion(ApplicationReference applicationReference);

	ApplicationVersion getLatestApplicationVersionNoCache(ApplicationReference applicationReference);

	ApplicationFollow saveApplicationFollow(ApplicationFollow applicationFollow);

	void deleteApplicationFollow(ApplicationFollow applicationFollow);

	void enableEmailAlertApplicationFollow(ApplicationFollow applicationFollow);

	void disableEmailAlertApplicationFollow(ApplicationFollow applicationFollow);

	ApplicationRequest saveApplicationRequest(ApplicationRequest applicationRequest);

}
