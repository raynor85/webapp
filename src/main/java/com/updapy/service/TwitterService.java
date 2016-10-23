package com.updapy.service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;

public interface TwitterService {

	void sendStatusNewApplication(ApplicationReference application);

	void sendStatusNewVersion(ApplicationVersion version);

}
