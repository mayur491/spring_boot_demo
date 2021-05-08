package com.example.spring_boot_security_demo.security;


import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.spring_boot_security_demo.filter.JwtTokenVerifier;
import com.example.spring_boot_security_demo.filter.JwtUsernameAndPasswordFilter;
import com.example.spring_boot_security_demo.jwt.JwtConfig;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

//	private static final String API = "/api/**";

	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;
//	private final SecretKey secretKey;
//	private final JwtConfig jwtConfig;

	@Autowired
	public ApplicationSecurityConfig(
			PasswordEncoder passwordEncoder,
			@Qualifier("userServiceImpl") UserDetailsService userDetailsService 
//			SecretKey secretKey,
//			JwtConfig jwtConfig
	) {
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
//		this.secretKey = secretKey;
//		this.jwtConfig = jwtConfig;
	}

	// Basic_Auth
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http

				.csrf().disable()
				
//				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()

//				.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				
//				.and()
				
//				.addFilter (
//					new JwtUsernameAndPasswordFilter (
//						authenticationManager(),
//						secretKey,
//						jwtConfig
//					)
//				)
				
//				.addFilterAfter (
//					new JwtTokenVerifier (
//						secretKey,
//						jwtConfig
//					),
//					JwtUsernameAndPasswordFilter.class
//				)
		
				.authorizeRequests()

				.antMatchers("/", "index", "/css/*", "/js/*")
				.permitAll()

//				.antMatchers(HttpMethod.GET, API).hasAnyRole(ApplicationUserRole.ADMIN.name(), 
//						ApplicationUserRole.ADMIN_TRAINEE.name(), ApplicationUserRole.STUDENT.name())
//			
//				.antMatchers(HttpMethod.POST, API).hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
//			
//				.antMatchers(HttpMethod.PUT, API).hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
//			
//				.antMatchers(HttpMethod.DELETE, API).hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())

				.anyRequest()
				.authenticated()

				.and()
//
				// Basic Authentication
//				.httpBasic();

				// Form Based Authentication
				.formLogin()
				.loginPage("/login")
				.usernameParameter("username") // name parameter in HTML
				.passwordParameter("password") // name parameter in HTML

				.permitAll()
				.defaultSuccessUrl("/homepage", true)

				.and()

				.rememberMe() // defaults to 2 weeks
				.rememberMeParameter("remember-me") // name parameter in HTML
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
				.key("somethingVerySecured")
				.userDetailsService(userDetailsService)

				.and()

				.logout()
				.logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("XSRF-TOKEN", "JSESSIONID", "remember-me")
				.logoutSuccessUrl("/login");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

}
