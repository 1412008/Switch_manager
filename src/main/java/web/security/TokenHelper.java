package web.security;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@PropertySource("classpath:mongo.properties")
public class TokenHelper {
	@Autowired
	private Environment env;

	@Autowired
	PasswordEncoder passwordEncoder; 
	
	private String SECRET = "JDJhJDEwJFpiYm9vV1UuZ25JMkhDTHpFczl3bE83TkF1UmJSbHFOV0U1b2I2U0U4YWhaV0lCVTF6Z3J5";//env.getProperty("jwt.secret");
	private int EXPIRES_IN = 18000;//Integer.parseInt(env.getProperty("jwt.expires_in"));
	private String APP_NAME = "devices_manager";//env.getProperty("jwt.app_name");

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
		System.out.println(SECRET + "\nLength: " + SECRET.length());
		System.out.println(env.getProperty("jwt.expires_in"));
		String jws = Jwts.builder().setIssuer(APP_NAME).setSubject(username).setIssuedAt(generateCurrentDate())
				.setExpiration(generateExpirationDate()).signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		return jws;
	}

	public Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(this.SECRET).parseClaimsJws(token).getBody();
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
		return new Date(getCurrentTimeMillis() + this.EXPIRES_IN * 1000);
	}

}
