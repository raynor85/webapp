package com.updapy.service.impl;

import java.util.Arrays;
import java.util.List;

public class RetrievalErrorIgnoredApplication {

	// Hardcoded list of completely ignored application (= no email sent except after 100 failures)
	public static final List<String> FULLY_IGNORED_APPLICATIONS = Arrays.asList("tortoisesvn", "glasswire", "manycam", "balsamiq", "autohotkey", "resiliosync", "hotspotshield", "admuncher");

	// Hardcoded list of ignored application when the download link is different for the same version
	public static final List<String> DIFFERENT_URL_IGNORED_APPLICATIONS = Arrays.asList("chromium", "aviraantivirusfree", "vlcmediaplayer", "opera");

	// Hardcoded list of ignored application when the new version of the app has been added manually
	public static final List<String> VERSION_HIGHER_IGNORED_APPLICATIONS = Arrays.asList("phraseexpress", "samsungmagician", "comodointernetsecurity");
}
