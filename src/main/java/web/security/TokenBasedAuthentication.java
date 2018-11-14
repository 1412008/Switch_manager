package web.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class TokenBasedAuthentication extends AbstractAuthenticationToken {
	
	private String token;
	private final UserDetails userDetails;

	public TokenBasedAuthentication(UserDetails userDetails) {
		super(userDetails.getAuthorities());
		this.userDetails = userDetails;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	public Object getCredentials() {
		return token;
	}

	public UserDetails getPrincipal() {
		return userDetails;
	}
}
