package com.example.spring_boot_security_demo.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring_boot_security_demo.user.dao.UserDao;

@Service("userServiceImpl")
public class UserServiceImpl implements UserDetailsService {

	private final UserDao userDao;

	@Autowired
	public UserServiceImpl(@Qualifier("userDaoImpl") UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDao.selectUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found.", username)));
	}

}
