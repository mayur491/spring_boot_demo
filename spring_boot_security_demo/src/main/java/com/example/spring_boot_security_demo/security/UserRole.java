package com.example.spring_boot_security_demo.security;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.example.spring_boot_security_demo.security.UserPermission.*;

public enum UserRole {

	ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
	
	ADMIN_TRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ)),
	
	STUDENT(Sets.newHashSet());

	private final Set<UserPermission> permissions;

	private UserRole(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<UserPermission> getPermissions() {
		return permissions;
	}

	public Set<GrantedAuthority> getGrantedAuthorities() {
		Set<GrantedAuthority> grantedAuthorities = getPermissions()
				.stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toSet());
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return grantedAuthorities;
	}

}
