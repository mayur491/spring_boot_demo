package com.example.spring_boot_security_demo.user.entity;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

	private static final long serialVersionUID = 6042030570998263285L;

	private final String username;
	private final String password;
	private final Set<? extends GrantedAuthority> grantedAthorities;
	private final Boolean isAccountNonExpired;
	private final Boolean isAccountNonLocked;
	private final Boolean isCredentialsNonExpired;
	private final Boolean isEnabled;

	public User(String username, String password, Set<? extends GrantedAuthority> grantedAthorities,
			Boolean isAccountNonExpired, Boolean isAccountNonLocked, Boolean isCredentialsNonExpired,
			Boolean isEnabled) {
		super();
		this.username = username;
		this.password = password;
		this.grantedAthorities = grantedAthorities;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
