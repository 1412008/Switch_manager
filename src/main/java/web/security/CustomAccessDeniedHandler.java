package web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException denied)
			throws IOException, ServletException {
		res.sendRedirect("denied");
	}

}
