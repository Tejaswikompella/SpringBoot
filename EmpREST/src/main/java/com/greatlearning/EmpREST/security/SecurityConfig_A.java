package com.greatlearning.EmpREST.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig_A {
	
	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";

	@Autowired
	private DataSource dataSource;
	
	@Bean
    public UserDetailsManager authenticateUsers() {
		
		UserDetails user = User.withUsername("devs").
				password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password")).build();
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.setAuthoritiesByUsernameQuery("select userName, password, isActive from user where userName=?");
		users.setUsersByUsernameQuery("select userName, roles from user where userName=?");
		users.createUser(user);
		return users;
	}
		
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//declares which Page(URL) will have What access type
		http.csrf().disable().
        authorizeRequests()
        .antMatchers(HttpMethod.POST, SecurityConstants.postEmp).hasAuthority(ADMIN)
        .antMatchers(HttpMethod.GET, SecurityConstants.getEmp).hasAnyAuthority(ADMIN)
        .antMatchers(HttpMethod.GET, SecurityConstants.getEmpById).hasAnyAuthority(USER)         
        .antMatchers(HttpMethod.GET, SecurityConstants.getEmpbyMail).hasAnyAuthority(USER)
        .antMatchers(HttpMethod.PUT, SecurityConstants.putEmpById).hasAnyAuthority(ADMIN)
        .antMatchers(HttpMethod.DELETE, SecurityConstants.delEmp).hasAnyAuthority(ADMIN)
        .antMatchers(HttpMethod.DELETE, SecurityConstants.delEmpById).hasAnyAuthority(ADMIN)
        .antMatchers(SecurityConstants.H2_CONSOLE).permitAll()  
        .anyRequest().authenticated().and();
        http.headers().frameOptions().disable();
		return http.build();
	}	
}
