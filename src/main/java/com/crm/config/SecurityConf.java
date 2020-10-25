package com.crm.config;


import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
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

import com.crm.model.AuditImpl;
import com.crm.service.UserDetailsImpl;
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableJpaAuditing
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
		
		 @Override
		    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		        auth.authenticationProvider(authenticationProvider());
		    }	
		 
		 @Bean
		  public AuditorAware<String> auditorProvider() {
		    return new AuditImpl();
		  }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 String[] allowedForEveryone={"/main/**",
				 "default","/settings","/login","/registration","/reg",
				 "/forgetPassword","/reset-password",
				 "/forgetPasswordAction","/confirm-reset",
				 "/errorPages/detaildError","/activation/**"};
		 
		 String[] allowedForAuthenticatedUsers= {
				 "updateUserPicture/**","updateUserPassword/**","updateProfile/**",
				 "updateTicket","deleteTicket","forwardTicket","editTicket/**","userData/**",
				 
				 
		 };
		 
		http		
			.authorizeRequests()
			//oldalak engedelyezese a megfelelo csoport szamara
			.antMatchers("/listUsers").hasAnyAuthority("Vezető")
			.antMatchers("/lineCharts").hasAnyAuthority("Vezető")
			.antMatchers("/displayLineCharts").hasAnyAuthority("Vezető")
			.antMatchers("/pieCharts").hasAnyAuthority("Vezető")
			.antMatchers("/pieCharts").hasAnyAuthority("Vezető")
			.antMatchers("/displayPieCharts").hasAnyAuthority("Vezető")
			.antMatchers("/updateUserRole").hasAnyAuthority("Vezető")
			.antMatchers(allowedForAuthenticatedUsers).hasAnyAuthority("Vezető")
		
			
			
			.antMatchers("/listTicket").hasAnyAuthority("Vezető,Ügyintéző")
			.antMatchers("/addTicket").hasAnyAuthority("Vezető,Ügyintéző")
			.antMatchers("/addTicketToDatabase").hasAnyAuthority("Vezető,Ügyintéző")		
			.antMatchers("/listClient").hasAnyAuthority("Vezető,Ügyintéző")
			.antMatchers("/addClient").hasAnyAuthority("Vezető,Ügyintéző")
			.antMatchers("/editClient/**").hasAnyAuthority("Vezető,Ügyintéző")
			
			.antMatchers(allowedForAuthenticatedUsers).hasAnyAuthority("Ügyintéző")
			
			.antMatchers("/listTicketDev").hasAnyAuthority("Vezető,Fejlesztő")
			.antMatchers("/forwardTicketDev").hasAnyAuthority("Vezető,Fejlesztő")
			.antMatchers(allowedForAuthenticatedUsers).hasAnyAuthority("Fejlesztő")
			
			.antMatchers("/listTicketTest").hasAnyAuthority("Vezető,Tesztelő")
			.antMatchers("/forwardTicketTest").hasAnyAuthority("Vezető,Tesztelő")
			.antMatchers(allowedForAuthenticatedUsers).hasAnyAuthority("Tesztelő")
			
			.antMatchers("/listTicketMech").hasAnyAuthority("Vezető,Szerelő")
			.antMatchers("/forwardTicketMech").hasAnyAuthority("Vezető,Szerelő")
			.antMatchers(allowedForAuthenticatedUsers).hasAnyAuthority("Szerelő")
		
			.antMatchers("/console/**").permitAll()
				.regexMatchers(".*\\.css$").permitAll()
				.regexMatchers(".*\\.js$").permitAll()	
				.antMatchers("/db/**").permitAll()				
				.antMatchers("/images/**").permitAll()
				.antMatchers(allowedForEveryone).permitAll()
				.anyRequest().authenticated()
				.and()
		
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/default",true)
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
	