package com.updapy.controller;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.updapy.service.PersonService;
import com.updapy.util.MessageUtil;

@Controller
@RequestMapping("user")
public class PersonController {

	private final String invalid = "early.interest.add.invalid";
	private final String already = "early.interest.add.already";
	private final String confirm = "early.interest.add.confirm";
	
	@Autowired
	MessageUtil messageUtil;
	
    @Autowired
    private PersonService personService;
    
    @RequestMapping(value="register-early", method=RequestMethod.POST)  
    public @ResponseBody String registerEarlyPerson(@RequestBody String email) {  
    	
    	if (!EmailValidator.getInstance().isValid(email)) {
    		return messageUtil.getSimpleMessage(invalid);
    	}
    	
    	if (!personService.findByEmail(email).isEmpty()) {
    		return messageUtil.getSimpleMessage(already);
    	}
    	
    	personService.registerEarlyWithEmail(email);
		return messageUtil.getSimpleMessage(confirm);
    }

}
