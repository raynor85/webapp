package com.updapy.service.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.updapy.model.User;

public class SecurityUtils {

	public static void logInSocialUser(User user) {
		final Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		UpdapyUser userDetails = UpdapyUser.createUpdapyUser(user, authorities);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public static String getEmailCurrentUser() {
		Authentication authentification = SecurityContextHolder.getContext().getAuthentication();
		if (authentification.getAuthorities().size() == 1 && authentification.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
			return null;
		}
		return ((UpdapyUser) authentification.getPrincipal()).getEmail();
	}

	public static boolean isPasswordCorrectForCurrentUser(AuthenticationManager authenticationManager, String password) {
		UpdapyUser currentUpdapyUser = ((UpdapyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(currentUpdapyUser.getEmail(), password);
			Authentication authentication = authenticationManager.authenticate(token);
			return authentication.isAuthenticated();
		} catch (Exception e) {
			return false;
		}
	}

	public static void reloadUsername(String newUsername) {
		UpdapyUser currentUpdapyUser = ((UpdapyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		UpdapyUser newUpdapyUser = new UpdapyUser(currentUpdapyUser.getEmail(), newUsername, currentUpdapyUser.isSocialUser(), "reloadUser", currentUpdapyUser.getAuthorities());
		Authentication authentication = new UsernamePasswordAuthenticationToken(newUpdapyUser, newUpdapyUser.getPassword(), newUpdapyUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public static void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}

}
