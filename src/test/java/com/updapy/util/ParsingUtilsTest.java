package com.updapy.util;

import org.junit.Assert;
import org.junit.Test;

public class ParsingUtilsTest {

	@Test
	public void testBuildUrl1() {
		String url = ParsingUtils.buildUrl("http://root/", "http://url/");
		Assert.assertEquals("http://url/", url);
	}

	@Test
	public void testBuildUrl2() {
		String url = ParsingUtils.buildUrl("http://root/", "//url/");
		Assert.assertEquals("http://root/url/", url);
	}

	@Test
	public void testBuildUrl3() {
		String url = ParsingUtils.buildUrl("http://root/", "/url/");
		Assert.assertEquals("http://root/url/", url);
	}

	@Test
	public void testBuildUrl4() {
		String url = ParsingUtils.buildUrl("http://root/", "url/");
		Assert.assertEquals("http://root/url/", url);
	}
}
