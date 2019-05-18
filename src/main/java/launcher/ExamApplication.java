package launcher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("exam.*")
@MapperScan({"exam.**.mapper"}) 
public class ExamApplication {

	public static void main(String[] args) {
		//程序入口
		SpringApplication.run(ExamApplication.class, args);
	}

}
