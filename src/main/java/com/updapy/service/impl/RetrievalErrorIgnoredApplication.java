package com.updapy.service.impl;

import java.util.Arrays;
import java.util.List;

public class RetrievalErrorIgnoredApplication {

	// List of ignored application (= no email sent except after 100 failures)
	public static final List<String> FULLY_IGNORED_APPLICATIONS = Arrays.asList("tortoisesvn", "glasswire", "manycam", "balsamiq", "autohotkey", "resiliosync", "hotspotshield", "admuncher");

	// List of completely ignored application when the download link is different for the same version
	public static final List<String> DIFFERENT_URL_IGNORED_APPLICATIONS = Arrays.asList("chromium", "aviraantivirusfree", "vlcmediaplayer", "opera");

	// List of completely ignored application when the local version of the app is higher than the remote version (e.g it has been added manually)
	public static final List<String> LOCAL_VERSION_HIGHER_IGNORED_APPLICATIONS = Arrays.asList("phraseexpress", "samsungmagician", "comodointernetsecurity");
}
