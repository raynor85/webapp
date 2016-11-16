package com.updapy.service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;

public interface SocialService {

	void sendStatusNewApplication(ApplicationReference application);

	void sendStatusNewVersion(ApplicationVersion version);

}
