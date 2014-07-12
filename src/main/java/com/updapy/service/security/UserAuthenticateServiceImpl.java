package com.updapy.service.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.updapy.model.User;
import com.updapy.repository.UserRepository;

@Service
public class UserAuthenticateServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	private final String emailInvalid = "Invalid.logUser.email";
	private final String emailInactive = "Inactive.logUser.email";

	@Override
	public UserDetails loadUserByUsername(String email) throws AuthenticationException {
		final User user = userRepository.findByEmail(email);
		// check if user exist
		if (user == null) {
			throw new UsernameNotFoundException(emailInvalid);
		}
		// check if user active
		if (!user.isActive()) {
			throw new DisabledException(emailInactive);
		}
		// give roles
		Collection<? extends GrantedAuthority> authorities = null;
		if (user.getEmail().endsWith("@updapy.com")) {
			authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
		} else {
			authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		}
		return UpdapyUser.createUpdapyUser(user, authorities);
	}

}
