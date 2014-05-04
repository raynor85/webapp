package com.updapy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("settings")
public class SettingsController {

	@RequestMapping({ "/", "" })
	public String settingsPage() {
		return "settings";
	}

}
