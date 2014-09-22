package com.updapy.service.security;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import com.updapy.model.User;

public class UpdapyUser extends SocialUser {

	private String name;

	private String email;

	private boolean socialUser;

	private String avatarUrl;

	public UpdapyUser(String email, String username, boolean socialUser, String avatarUrl, String password, Collection<? extends GrantedAuthority> authorities) {
		super(email, password, authorities);
		this.name = username;
		this.email = email;
		this.socialUser = socialUser;
		this.avatarUrl = avatarUrl;
	}

	public static UpdapyUser createUpdapyUser(User user, String avatarUrl, Collection<? extends GrantedAuthority> authorities) {
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
		return new UpdapyUser(email, username, socialUser, avatarUrl, password, authorities);
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public boolean isSocialUser() {
		return socialUser;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

}
