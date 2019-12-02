package feedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = { "http://127.0.0.1:8000", "http://localhost:8000" })
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan({ "feedback.controller", "feedback.service", "feedback.model.*" })
public class FeedbackApplication {
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FeedbackApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FeedbackApplication.class, args);
	}

}
