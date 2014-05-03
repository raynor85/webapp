package com.updapy.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.updapy.model.Account;
import com.updapy.model.AccountActivation;
import com.updapy.model.HelpMessage;
import com.updapy.model.Person;
import com.updapy.model.Setting;
import com.updapy.model.enumeration.Parameter;
import com.updapy.model.enumeration.TypeHelpMessage;
import com.updapy.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService, UserDetailsService {

	@Autowired
	PersonRepository personRepository;
	
	private final String signInErrorInvalid = "sign.in.error.invalid";
	private final String signInErrorInactive = "sign.in.error.inactive";

	@Override
	public UserDetails loadUserByUsername(String email) throws BadCredentialsException {
	    final Person user = personRepository.findByEmail(email); 
	    // Check if user exist
	    if (user == null) {
	        throw new BadCredentialsException(signInErrorInvalid);
	    }
	    // Check if user active
	    if (!user.getAccount().getActivation().isActive()) {
	    	throw new BadCredentialsException(signInErrorInactive);
	    }
	    final Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
	    String username = user.getName();
	    if (StringUtils.isBlank(username)) {
	    	username = user.getEmail();
	    }
	    return new User(username, user.getPassword(), authorities);
	}
	
	@Override
	public void registerEarlyWithEmail(String email) {
		Person person = initDefaultValuesPerson();
		person.setEarly(true);
		person.setEmail(email);
		personRepository.saveAndFlush(person);
	}
	
	public Person initDefaultValuesPerson() {
		Person person = new Person();
		
		// Account - not yet activate + generate the activation link
		Account account = new Account();
		AccountActivation accountActivation = new AccountActivation();
		accountActivation.setActive(false);
		accountActivation.setKey(RandomStringUtils.random(50, "0123456789abcdefghijklmnopqrstuvwxyz"));
		accountActivation.setGenerationKeyDate(new Date());
		account.setActivation(accountActivation);
		person.setAccount(account);
		
		// Settings - alert by email for each app + newsletter subscription
		List<Setting> defaultSettings = new ArrayList<Setting>();
		Setting settingAlertByEmail = new Setting();
		settingAlertByEmail.setParameter(Parameter.ALERT_BY_EMAIL);
		settingAlertByEmail.setActive(true);
		settingAlertByEmail.setPerson(person);
		defaultSettings.add(settingAlertByEmail);
		Setting settingEmailForEachApplication = new Setting();
		settingEmailForEachApplication.setParameter(Parameter.EMAIL_FOR_EACH_APPLICATION);
		settingEmailForEachApplication.setActive(true);
		settingEmailForEachApplication.setPerson(person);
		defaultSettings.add(settingEmailForEachApplication);
		Setting settingEmailWeeklyDigest = new Setting();
		settingEmailWeeklyDigest.setParameter(Parameter.EMAIL_WEEKLY_DIGEST);
		settingEmailWeeklyDigest.setActive(false);
		settingEmailWeeklyDigest.setPerson(person);
		defaultSettings.add(settingEmailWeeklyDigest);
		Setting settingNewsletter = new Setting();
		settingNewsletter.setParameter(Parameter.NEWSLETTER);
		settingNewsletter.setActive(true);
		settingNewsletter.setPerson(person);
		defaultSettings.add(settingNewsletter);
		person.setSettings(defaultSettings);
		
		// Help messages - all is visible at beginning
		List<HelpMessage> defaultHelpMessages = new ArrayList<HelpMessage>();
		HelpMessage helpMessageDashboardHowTo = new HelpMessage();
		helpMessageDashboardHowTo.setType(TypeHelpMessage.DASHBOARD_HOW_TO);
		helpMessageDashboardHowTo.setHidden(false);
		helpMessageDashboardHowTo.setPerson(person);
		defaultHelpMessages.add(helpMessageDashboardHowTo);
		HelpMessage helpMessageDashboardAlertDisabled = new HelpMessage();
		helpMessageDashboardAlertDisabled.setType(TypeHelpMessage.DASHBOARD_ALERT_DISABLED);
		helpMessageDashboardAlertDisabled.setHidden(false);
		helpMessageDashboardAlertDisabled.setPerson(person);
		defaultHelpMessages.add(helpMessageDashboardAlertDisabled);
		HelpMessage helpMessageSearchHowTo = new HelpMessage();
		helpMessageSearchHowTo.setType(TypeHelpMessage.SEARCH_HOW_TO);
		helpMessageSearchHowTo.setHidden(false);
		helpMessageSearchHowTo.setPerson(person);
		defaultHelpMessages.add(helpMessageSearchHowTo);
		person.setHelpMessages(defaultHelpMessages);
		
		return person;
	}

	@Override
	public Person findByEmail(String email) {
		return personRepository.findByEmail(email);
	}

}
