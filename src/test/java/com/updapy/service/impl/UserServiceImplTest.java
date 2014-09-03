package com.updapy.service.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.updapy.model.ApplicationVersion;
import com.updapy.model.User;
import com.updapy.model.enumeration.Lang;
import com.updapy.model.enumeration.OsVersion;
import com.updapy.service.UserService;

public class UserServiceImplTest {

	UserService userService;

	@Before
	public void setUp() throws Exception {
		userService = new UserServiceImpl();
	}

	@After
	public void tearDown() throws Exception {
		userService = null;
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest01() {
		User user = createUser(Lang.en, OsVersion.WIN_32_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", "win32UrlFr", "win64UrlEn", "win64UrlFr");
		Assert.assertEquals("win32UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest02() {
		User user = createUser(Lang.en, OsVersion.WIN_64_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", "win32UrlFr", "win64UrlEn", "win64UrlFr");
		Assert.assertEquals("win64UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest03() {
		User user = createUser(Lang.fr, OsVersion.WIN_32_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", "win32UrlFr", "win64UrlEn", "win64UrlFr");
		Assert.assertEquals("win32UrlFr", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest04() {
		User user = createUser(Lang.fr, OsVersion.WIN_64_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", "win32UrlFr", "win64UrlEn", "win64UrlFr");
		Assert.assertEquals("win64UrlFr", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest05() {
		User user = createUser(Lang.en, OsVersion.WIN_32_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", null, null, null);
		Assert.assertEquals("win32UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest06() {
		User user = createUser(Lang.en, OsVersion.WIN_64_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", null, null, null);
		Assert.assertEquals("win32UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest07() {
		User user = createUser(Lang.fr, OsVersion.WIN_32_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", null, null, null);
		Assert.assertEquals("win32UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest08() {
		User user = createUser(Lang.fr, OsVersion.WIN_64_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", null, null, null);
		Assert.assertEquals("win32UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest09() {
		User user = createUser(Lang.en, OsVersion.WIN_32_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", null, "win64UrlEn", null);
		Assert.assertEquals("win32UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest10() {
		User user = createUser(Lang.en, OsVersion.WIN_64_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", null, "win64UrlEn", null);
		Assert.assertEquals("win64UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest11() {
		User user = createUser(Lang.fr, OsVersion.WIN_32_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", null, "win64UrlEn", null);
		Assert.assertEquals("win32UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest12() {
		User user = createUser(Lang.fr, OsVersion.WIN_64_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", null, "win64UrlEn", null);
		Assert.assertEquals("win64UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest13() {
		User user = createUser(Lang.en, OsVersion.WIN_32_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", "win32UrlFr", "win64UrlEn", null);
		Assert.assertEquals("win32UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest14() {
		User user = createUser(Lang.en, OsVersion.WIN_64_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", "win32UrlFr", "win64UrlEn", null);
		Assert.assertEquals("win64UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest15() {
		User user = createUser(Lang.fr, OsVersion.WIN_32_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", "win32UrlFr", "win64UrlEn", null);
		Assert.assertEquals("win32UrlFr", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	@Test
	public void testGetDownloadUrlMatchingSettingsTest16() {
		User user = createUser(Lang.fr, OsVersion.WIN_64_BITS);
		ApplicationVersion version = createVersion("win32UrlEn", "win32UrlFr", "win64UrlEn", null);
		Assert.assertEquals("win64UrlEn", userService.getDownloadUrlMatchingSettings(user, version).getUrl());
	}

	private User createUser(Lang lang, OsVersion osVersion) {
		User user = new User();
		user.setLangUpdate(lang);
		user.setOsVersion(osVersion);
		return user;
	}

	private ApplicationVersion createVersion(String win32UrlEn, String win32UrlFr, String win64UrlEn, String win64UrlFr) {
		ApplicationVersion version = new ApplicationVersion();
		version.setWin32UrlEn(win32UrlEn);
		version.setWin32UrlFr(win32UrlFr);
		version.setWin64UrlEn(win64UrlEn);
		version.setWin64UrlFr(win64UrlFr);
		return version;
	}

}
