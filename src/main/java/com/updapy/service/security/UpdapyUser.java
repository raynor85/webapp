package com.updapy.service.security;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import com.updapy.model.User;

public class UpdapyUser extends SocialUser {

	private String email;

	public UpdapyUser(String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.email = email;
	}

	public static UpdapyUser createUpdapyUser(User user, Collection<? extends GrantedAuthority> authorities) {
		String password = user.getPassword();
		if (user.isSocialUser()) {
			password = "socialUser";
		}
		String email = user.getEmail();
		String username = user.getName();
		if (StringUtils.isBlank(username)) {
			username = email;
		}
		return new UpdapyUser(username, email, password, authorities);
	}

	public String getEmail() {
		return email;
	}

}
