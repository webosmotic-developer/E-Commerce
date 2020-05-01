package com.webosmotic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.webosmotic.security.jwt.JwtFilter;
import com.webosmotic.security.jwt.MyAuthenticationEntryPoint;
import com.webosmotic.security.oauth2.CustomOAuth2UserService;
import com.webosmotic.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.webosmotic.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.webosmotic.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.webosmotic.service.MyUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AppSecurtiyConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	MyUserDetailService myuserDetailService;
	@Autowired
	MyAuthenticationEntryPoint myAuthenticationEntryPoint;
	@Autowired
	JwtFilter jwtFilter;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	/* Passing the custom userDetaile service and passwordEncoder */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myuserDetailService).passwordEncoder(passwordEncoder);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	/* Configuration which request is allowed and with which roles. */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/auth/**", "/api/users/**", "/product/**", "/").permitAll().anyRequest()
				.authenticated();

		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.oauth2Login().authorizationEndpoint().baseUri("/oauth2/authorize")
				.authorizationRequestRepository(cookieAuthorizationRequestRepository()).and().redirectionEndpoint()
				.baseUri("/oauth2/callback/*").and().userInfoEndpoint().userService(customOAuth2UserService).and()
				.successHandler(oAuth2AuthenticationSuccessHandler).failureHandler(oAuth2AuthenticationFailureHandler);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
