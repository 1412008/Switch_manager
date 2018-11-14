package web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import web.GlobalVar.JwtVariable;
import web.Services.UserDetailsServiceImpl;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	UserDetailsServiceImpl userDetailServiceImpl;

	@Autowired
	private TokenHelper tokenHelper;

	@Autowired
	JwtVariable jwtVariable;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		String error = "";
		String authToken = getToken(req);
		System.out.println("Got token: " + authToken);

		if (tokenHelper.validateToken(authToken)) {
			String username = tokenHelper.getUsernameFromToken(authToken);
			if (username != null) {
				UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);

				TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
				authentication.setToken(authToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				error = "Username from token can't be found in DB.";
			}
		} else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				System.out.println(auth.getName());
			}
			System.out.println("Invalid token!");
		}
		if (!error.equals("")) {
			System.out.println(error);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		chain.doFilter(req, res);
	}

	private String getToken(HttpServletRequest req) {
		String authHeader = req.getHeader(jwtVariable.getHEADER());
		if (authHeader != null) {
			return authHeader;
		}
		return null;
	}
}
