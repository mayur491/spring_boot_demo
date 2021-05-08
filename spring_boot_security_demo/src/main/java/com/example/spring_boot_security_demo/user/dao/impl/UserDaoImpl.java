package com.example.spring_boot_security_demo.user.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.spring_boot_security_demo.security.UserRole;
import com.example.spring_boot_security_demo.user.dao.UserDao;
import com.example.spring_boot_security_demo.user.entity.User;
import com.google.common.collect.Lists;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserDaoImpl (PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public Optional<User> selectUserByUsername(String username) {
		return getUsers()
				.stream()
				.filter(user -> username.equals(user.getUsername()))
				.findFirst();
	}

	// TODO get from Database
	private List<User> getUsers() {
		return Lists.newArrayList(
			new User(
					"admin",
					passwordEncoder.encode("admin"),
					UserRole.ADMIN.getGrantedAuthorities(),
					true,
					true,
					true,
					true
			),
			new User(
					"adminTrainee",
					passwordEncoder.encode("adminTrainee"),
					UserRole.ADMIN_TRAINEE.getGrantedAuthorities(),
					true,
					true,
					true,
					true
			),
			new User(
					"student",
					passwordEncoder.encode("student"),
					UserRole.STUDENT.getGrantedAuthorities(),
					true,
					true,
					true,
					true
			)
		);
	}

}
