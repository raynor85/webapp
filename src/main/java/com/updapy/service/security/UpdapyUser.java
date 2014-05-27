package com.updapy.service.security;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import com.updapy.model.User;

public class UpdapyUser extends SocialUser {

	private String email;

	private boolean socialUser;

	public UpdapyUser(String username, String email, boolean socialUser, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.email = email;
		this.socialUser = socialUser;
	}

	public static UpdapyUser createUpdapyUser(User user, Collection<? extends GrantedAuthority> authorities) {
		String password = user.getPassword();
		boolean socialUser = false;
		if (user.isSocialUser()) {
			password = "socialUser";
			socialUser = true;
		}
		String email = user.getEmail();
		String username = user.getName();
		if (StringUtils.isBlank(username)) {
			username = email;
		}
		return new UpdapyUser(username, email, socialUser, password, authorities);
	}

	public String getEmail() {
		return email;
	}

	public boolean isSocialUser() {
		return socialUser;
	}

}
