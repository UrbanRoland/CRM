package com.crm.config;


import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.crm.service.UserDetailsImpl;
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {
	

		
	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService);
	}


	
		
		@Resource(name="userServiceImpl")
	    UserDetailsService userDetailsService;
	     
	

		@Bean
		public static PasswordEncoder passwordEncoder() {
		      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		}
		 @Bean
		 public DaoAuthenticationProvider authenticationProvider() {
		      DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		      authenticationProvider.setUserDetailsService(userDetailsService);
		      authenticationProvider.setPasswordEncoder(passwordEncoder());
		      return authenticationProvider;
		 }

	// Enable jdbc authentication
		
		 @Override
		    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		        auth.authenticationProvider(authenticationProvider());
		    }
	  
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			
			.authorizeRequests()
			//oldalak engedélyezése
			.antMatchers("/console/**").permitAll()
				.regexMatchers(".*\\.css$").permitAll()
				.antMatchers("/registration").permitAll()
				.antMatchers("/forgetPassword").permitAll()
				.antMatchers("/errorPages/404").permitAll()
				.antMatchers("/errorPages/detaildError").permitAll()
				.antMatchers("/images/**").permitAll()
				.antMatchers("/db/**").permitAll()
				.regexMatchers(".*\\.js$").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/reg").permitAll()
				.antMatchers("/forgetPasswordAction/**").permitAll()
				.antMatchers("/activation/**").permitAll()
				.antMatchers("/forgot-password/**").permitAll()
				.antMatchers("/reset-password/**").permitAll()
				.antMatchers("/resetPassword/**").permitAll()
				.antMatchers("/confirm-reset/**").permitAll()
				.antMatchers("/main/**").permitAll()
				.antMatchers("/addTicket/**").permitAll()
				.antMatchers("/settings/**").permitAll()
				.antMatchers("/addClient/**").permitAll()
				.antMatchers("/login/**").permitAll()
				.antMatchers("/uploadImage/**").permitAll()
				.antMatchers("/upload/**").permitAll()
				.antMatchers("/addToClient/**").permitAll()
				.antMatchers("/listTicket/**").permitAll()
				.antMatchers("/test/**").permitAll()
				.antMatchers("/addTicketToDatabase/**").permitAll()
				
				
				.anyRequest().authenticated()
				.and()
		
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/listTicket",true)
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/login?logout")
				.permitAll();
		
		//ezek csak tesztelesre kellenek
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
	}	
	 

}
	