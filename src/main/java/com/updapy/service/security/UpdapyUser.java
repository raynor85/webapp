package com.updapy.service.security;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import com.updapy.model.User;

public class UpdapyUser extends SocialUser {

	public UpdapyUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public static UpdapyUser createUpdapyUser(User user, Collection<? extends GrantedAuthority> authorities) {
		String password = user.getPassword();
		if (user.isSocialUser()) {
			password = "socialUser";
		}
		String username = user.getName();
		if (StringUtils.isBlank(username)) {
			username = user.getEmail();
		}
		return new UpdapyUser(username, password, authorities);
	}

}
