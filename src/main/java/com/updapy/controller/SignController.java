package com.updapy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.util.MessageUtil;

@Controller
@RequestMapping("sign")
public class SignController {

	private final String signOutconfirm = "sign.out.success";
	
	@Autowired
	MessageUtil messageUtil;
	
	@RequestMapping({"/", ""})
	public ModelAndView signPage(@RequestParam(value = "logout", required = false) String logout) {
		
		ModelAndView model = new ModelAndView();
		if (logout != null) {
			model.addObject("msg", messageUtil.getSimpleMessage(signOutconfirm));
		}
		model.setViewName("sign");
		
		return model;
	}
	
}
