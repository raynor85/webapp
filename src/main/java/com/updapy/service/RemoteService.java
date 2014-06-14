package com.updapy.service;

import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;

@Transactional
public interface RemoteService {

	ApplicationVersion retrieveRemoteLatestVersion(ApplicationReference application);

}
