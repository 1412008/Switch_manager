//package web.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.session.data.redis.RedisOperationsSessionRepository;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import org.springframework.session.web.http.CookieSerializer;
//import org.springframework.session.web.http.DefaultCookieSerializer;
//
//@Configuration
//@EnableRedisHttpSession
//public class SessionConfig implements ApplicationListener<ApplicationEvent> {
//
//	private final int maxInactiveIntervalInSeconds = 3 + 60 * 60;
//
//	@Autowired
//    private RedisOperationsSessionRepository redisOperation;
//
//    @Override
//    public void onApplicationEvent(ApplicationEvent event) {
//        if (event instanceof ContextRefreshedEvent) {
//            redisOperation.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
//        }
//    }
//
////	@Bean
////	public CookieSerializer cookieSerializer() {
////		DefaultCookieSerializer cookie = new DefaultCookieSerializer();
////		cookie.setCookieName("JSESSIONID");
////		cookie.setCookiePath("/");
////		cookie.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
////		cookie.setUseSecureCookie(true);
////		cookie.setCookieMaxAge(2 * 60 * 60);
////		return cookie;
////	}
//}
