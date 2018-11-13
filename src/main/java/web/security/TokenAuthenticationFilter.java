package web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import web.Services.UserDetailsServiceImpl;

@PropertySource("classpath:mongo.properties")
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	public Environment env;

	private String AUTH_HEADER = "Authentication";//env.getProperty("jwt.header");

	@Autowired
	UserDetailsServiceImpl userDetailServiceImpl;

	@Autowired
	private TokenHelper tokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		String error = "";
		String authToken = getToken(req);
		System.out.println("Got token: " + authToken);

		if (authToken != null) {
			String username = tokenHelper.getUsernameFromToken(authToken);
			if (username != null) {
				UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);

				TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
				authentication.setToken(authToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				error = "Username from token can't be found in DB.";
			}
		}
		if (!error.equals("")) {
			System.out.println(error);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		chain.doFilter(req, res);
	}

	private String getToken(HttpServletRequest req) {
		String authHeader = req.getHeader(AUTH_HEADER);
		if (authHeader != null) {
			return authHeader;
		}
		return null;
	}
}
