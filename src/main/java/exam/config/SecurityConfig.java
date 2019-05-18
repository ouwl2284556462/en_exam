package exam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import exam.user.service.UserService;

@Configuration
/**
 *认证和鉴权配置 
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	//用户服务类
	private UserService userService;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//允许所有用户访问"/"  登录页
		http.authorizeRequests()
			//admin下的操作需要有admin角色
			.antMatchers("/admin/**").hasAnyAuthority("admin")
			//所有静态资源允许所有人访问
		    .antMatchers("/", "/login/**", "/static/**")
		    .permitAll()
			.anyRequest()
			.authenticated()
			.and()
			//设置登录页面
			.formLogin().loginPage("/").loginProcessingUrl("/doLogin.do").defaultSuccessUrl("/Workspace/toMain.do")
			//登录失败页面
			.failureForwardUrl("/login/loginErr.do")
			.and()
			//登出操作
			.logout().logoutUrl("/logout.do")
			.logoutSuccessUrl("/");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//设置加密器
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		//密码加密器
		return new BCryptPasswordEncoder();
	}
	
}
