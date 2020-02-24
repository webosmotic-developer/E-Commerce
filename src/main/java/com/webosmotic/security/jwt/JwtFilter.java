package com.webosmotic.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.webosmotic.controller.AuthController;
import com.webosmotic.entity.User;
import com.webosmotic.pojo.MyUserDetail;
import com.webosmotic.service.UserService;

/**
 * Custom Filter class to tap every incoming request once to check if the request contains the jwt
 * token. If contains JWT token then validate the token and pass the request If
 * not then pass the request.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private UserService userService;

	/*
	 * method to generate the JWT token and pass it to the next filter
	 * @Param HttpServletRequest , HttpServletResponse and FiltercChain
	 */
	@Override
	@Transactional
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final Logger logger = LoggerFactory.getLogger(AuthController.class);

		String jwt = getToken(request);
		if (jwt != null && jwtProvider.validate(jwt)) {
			try {
				String userName = jwtProvider.getUserName(jwt);
				User user = userService.getUserByUserName(userName);
				// if jwt ok, then authenticate
				MyUserDetail userDetails = new MyUserDetail(user);
				SimpleGrantedAuthority sga = new SimpleGrantedAuthority("Role_Buyer");
				ArrayList<SimpleGrantedAuthority> list = new ArrayList<>();
				list.add(sga);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
						null, list);
				SecurityContextHolder.getContext().setAuthentication(auth);

			} catch (Exception e) {
				logger.error("Set Authentication from JWT failed");
			}
		}
		filterChain.doFilter(request, response);
	}

	/*
	 * method to extract the JWT token from the request header.
	 * @Param HttpServletRequest
	 * @Return extracted JWT Token
	 */
	private String getToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		logger.info("authHeader" + " " + authHeader);
		logger.info("authHeader" + " " + request.getRequestURI());
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.replace("Bearer ", "");
		}
		return null;
	}
}
