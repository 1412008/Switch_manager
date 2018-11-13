package web.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Value("${jwt.expires_in}")
	private int EXPIRES_IN;

	@Autowired
	TokenHelper tokenHelper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
			Authentication authentication) {
		System.out.println("Authetication success handler");
		clearAuthenticationAttributes(req);
		String jwt = tokenHelper.generateToken(authentication.getName());
		try {
			System.out.println(jwt);
			//res.addHeader("Authentication", jwt);
			res.sendRedirect("home");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
