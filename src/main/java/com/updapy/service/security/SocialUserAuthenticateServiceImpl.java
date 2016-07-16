package com.updapy.service.security;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SocialUserAuthenticateServiceImpl implements SocialUserDetailsService {

	@Inject
	private UserAuthenticateServiceImpl userAuthenticateServiceImpl;

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
		UserDetails userDetails = userAuthenticateServiceImpl.loadUserByUsername(userId);
		return (SocialUserDetails) userDetails;
	}

}
