package web.security;

//import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import web.GlobalVar.JwtVariable;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	TokenHelper tokenHelper;

	@Autowired 
	JwtVariable jwtVariable;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
			Authentication authentication) {
		System.out.println("Authetication success handler");
		clearAuthenticationAttributes(req);
		String jwt = tokenHelper.generateToken(authentication.getName());
		try {
			System.out.println(jwt);
			res.addHeader(jwtVariable.getHEADER(), jwt);
			res.sendRedirect("home");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
