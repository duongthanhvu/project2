package org.fpoly.nhom2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return bCryptEncoder;
	}
	
	@Autowired
	public void configGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().antMatchers("/admin/**").hasRole("admin");
		http.authorizeRequests().antMatchers("/user/**").hasAnyRole("admin, user");
		http.authorizeRequests().antMatchers("/ca/**").hasRole("ca");
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests().and().formLogin().loginProcessingUrl("/login-process")
		.loginPage("/login")
		.defaultSuccessUrl("/")
		.failureUrl("/login?message=error")
		.usernameParameter("username")
		.passwordParameter("password")
		.and()
		.logout().logoutUrl("/logout")
		.logoutSuccessUrl("/login?message=logout");
	}
}
