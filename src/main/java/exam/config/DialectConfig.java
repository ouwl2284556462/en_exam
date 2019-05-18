package exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import exam.base.thymeleafdialect.OwlDialect;


@Configuration
/**
 * 自定义标签配置
 *
 */
public class DialectConfig {
	
	@Bean
	/**
	 * 返回自定的标签
	 * @return
	 */
    public OwlDialect owlDialect(){
        return new OwlDialect();
    }
}
