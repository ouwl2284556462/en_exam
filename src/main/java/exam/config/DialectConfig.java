package exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import exam.base.thymeleafdialect.OwlDialect;


@Configuration
public class DialectConfig {
	
	@Bean
	/**
	 * owl:*
	 * @return
	 */
    public OwlDialect owlDialect(){
        return new OwlDialect();
    }
}
