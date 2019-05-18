package exam.config;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.pagehelper.PageHelper;

@Configuration
/**
 *	MVC配置 
 */
public class MVCConfig implements WebMvcConfigurer{
	
	@Value("${user.imagesPath}")
    private String userImagesPath;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//设置默认登录页面
		registry.addViewController("/").setViewName("/login/login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	//设置静态资源位置
    	registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    	
    	//用户头像图片保存位置
    	registry.addResourceHandler("/userImages/**").addResourceLocations(userImagesPath);
    }
    
    
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
    	//设置字符集
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	//设置转换器
        converters.add(responseBodyConverter());
    }
    
    
	@Bean
	/**
	 * 分页插件配置
	 * @return
	 */
    public PageHelper pageHelper(){
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        new SqlSessionFactoryBean().setPlugins(new Interceptor[]{pageHelper});
        return pageHelper;
    }
    
}
