package com.updapy.service.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.updapy.model.User;

public class SecurityUtils {

	public static void logInSocialUser(User user) {
		final Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		UpdapyUser userDetails = UpdapyUser.createUpdapyUser(user, authorities);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public static String getEmailCurrentUser() {
		return ((UpdapyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
	}

	public static void reloadUsername(String newUsername) {
		UpdapyUser currentUpdapyUser = ((UpdapyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		UpdapyUser newUpdapyUser = new UpdapyUser(newUsername, currentUpdapyUser.getEmail(), "reloadUser", currentUpdapyUser.getAuthorities());
		Authentication authentication = new UsernamePasswordAuthenticationToken(newUpdapyUser, newUpdapyUser.getPassword(), newUpdapyUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
