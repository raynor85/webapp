package com.updapy.model.enumeration;

public enum IgnoranceType {
	FULL, // Completely ignored application (= no email sent except after 100 failures)
	DIFFERENT_URL, // Ignored application when the download link is different for the same version
	LOCAL_VERSION_HIGHER; // Ignored application when the local version of the app is higher than the remote version (e.g it has been added manually)
}