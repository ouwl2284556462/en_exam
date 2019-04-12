package exam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import exam.user.service.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//允许所有用户访问"/"  登录页
		http.authorizeRequests()
		    .antMatchers("/", "/login/**", "/static/**")
		    .permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin().loginPage("/").loginProcessingUrl("/doLogin.do").defaultSuccessUrl("/Workspace/toMain.do")
			.failureForwardUrl("/login/loginErr.do")
			.and()
			.logout().logoutUrl("/logout.do")
			.logoutSuccessUrl("/");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
