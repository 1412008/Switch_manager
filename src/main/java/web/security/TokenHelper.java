package web.security;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import web.GlobalVar.JwtVariable;

@Component
public class TokenHelper {

	@Autowired
	PasswordEncoder passwordEncoder; 
	
	@Autowired 
	JwtVariable jwtVariable;

	private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	@SuppressWarnings("deprecation")
	public String generateToken(String username) {		
		//System.out.println(jwtVariable.getSECRET() + "\nLength: " + jwtVariable.getSECRET().length());
		String jws = Jwts.builder().setIssuer(jwtVariable.getAPP_NAME()).setSubject(username).setIssuedAt(generateCurrentDate())
				.setExpiration(generateExpirationDate()).signWith(SIGNATURE_ALGORITHM, jwtVariable.getSECRET()).compact();
		return jws;
	}

	public Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(jwtVariable.getSECRET()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private long getCurrentTimeMillis() {
		return new DateTime().getMillis();
	}

	private Date generateCurrentDate() {
		return new Date(getCurrentTimeMillis());
	}

	private Date generateExpirationDate() {
		return new Date(getCurrentTimeMillis() + jwtVariable.getEXPIRES_IN() * 1000);
	}
	
	
	
	private boolean isTokenExpired(String token) {
	    Date expiration = getExpirationDateFromToken(token);
	    return expiration.before(new Date());
	  }
	
	private Date getExpirationDateFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getExpiration();
	}

	public boolean validateToken(String token) {
		if (StringUtils.isEmpty(token)) {
			return false;
		}
		
		if (StringUtils.isEmpty(getUsernameFromToken(token))) {
			return false;
		}
		
		if (isTokenExpired(token)) {
			return false;
		}
		
		return true;
	}

}
