package com.example.spring_boot_security_demo.user.dao;

import java.util.Optional;

import com.example.spring_boot_security_demo.user.entity.User;

public interface UserDao {

	public Optional<User> selectUserByUsername(String username);
	
}
