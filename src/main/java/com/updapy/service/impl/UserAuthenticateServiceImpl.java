package com.updapy.service.impl;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.User;
import com.updapy.repository.UserRepository;

@Service
public class UserAuthenticateServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	private final String emailInvalid = "Invalid.logUser.email";
	private final String emailInactive = "Inactive.logUser.email";

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws BadCredentialsException {
		final User user = userRepository.findByEmail(email);
		// Check if user exist
		if (user == null) {
			throw new BadCredentialsException(emailInvalid);
		}
		// Check if user active
		if (!user.getAccount().getActivation().isActive()) {
			throw new BadCredentialsException(emailInactive);
		}
		final Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		String username = user.getName();
		if (StringUtils.isBlank(username)) {
			username = user.getEmail();
		}
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
	}

}
