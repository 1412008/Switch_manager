package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import web.Services.UserDetailsServiceImpl;
import web.security.AuthenticationFailureHandler;
import web.security.AuthenticationSuccessHandler;
import web.security.TokenAuthenticationFilter;

@EnableWebSecurity
@Configuration
@ComponentScan("web")
public class SpringSecutiryConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("configureGlobal");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		//auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Bean
    public TokenAuthenticationFilter jwtAuthenticationTokenFilter() throws Exception {
        return new TokenAuthenticationFilter();
    }

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		// @formatter:off
		http.addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class).authorizeRequests();
    	http.formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login_check")
        //.defaultSuccessUrl("/home")
        //.failureUrl("/login")
        .successHandler(authenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
        .usernameParameter("usname")
        .passwordParameter("password")
        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    	// @formatter:on

		http.authorizeRequests().antMatchers("/", "/login", "/create", "/create-user").permitAll();
		http.authorizeRequests().antMatchers("/home").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
		http.authorizeRequests().antMatchers("/api/*").access("hasAnyRole('ROLE_ADMIN')");
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/denied");

		//http.rememberMe().key("supersecret").tokenValiditySeconds(5*60*60);
		
		System.out.println("Config");
	}

}
