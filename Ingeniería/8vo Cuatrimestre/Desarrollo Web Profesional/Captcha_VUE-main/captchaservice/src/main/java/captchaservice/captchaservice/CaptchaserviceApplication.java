package captchaservice.captchaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class CaptchaserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptchaserviceApplication.class, args);

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMapping(CorsRegistry registry) {
				registry.addMapping("/api/*").allowedOrigins(new String[] { "*" }).allowedMethods();
			}

		};
	}

}
