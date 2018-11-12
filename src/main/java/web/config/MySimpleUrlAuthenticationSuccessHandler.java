//package web.config;
//
//import java.io.IOException;
//import java.util.Collection;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.WebAttributes;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//	public MySimpleUrlAuthenticationSuccessHandler() {
//		super();
//	}
//
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//			Authentication authentication) throws IOException, ServletException {
//		handle(request, response, authentication);
//		clearAuthenticationAttributes(request);
//	}
//
//	protected void handle(final HttpServletRequest request, final HttpServletResponse response,
//			final Authentication authentication) throws IOException {
//		final String targetUrl = determineTargetUrl(authentication);
//
//		if (response.isCommitted()) {
//			System.out.println("Response has already been committed. Unable to redirect to " + targetUrl);
//			return;
//		}
//
//		redirectStrategy.sendRedirect(request, response, targetUrl);
//	}
//
//	protected String determineTargetUrl(final Authentication authentication) {
//		boolean isUser = false;
//		boolean isAdmin = false;
//		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		for (final GrantedAuthority grantedAuthority : authorities) {
//			if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
//				isUser = true;
//				break;
//			} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
//				isAdmin = true;
//				break;
//			}
//		}
//
//		if (isUser) {
//			return "/home";
//		} else if (isAdmin) {
//			return "/home";
//		} else {
//			throw new IllegalStateException();
//		}
//	}
//
//	public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
//		this.redirectStrategy = redirectStrategy;
//	}
//
//	protected RedirectStrategy getRedirectStrategy() {
//		return redirectStrategy;
//	}
//
//	protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
//		final HttpSession session = request.getSession(false);
//
//		if (session == null) {
//			return;
//		}
//
//		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//	}
//
//}
