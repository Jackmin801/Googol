package com.jacksteam.googol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class GoogolApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogolApplication.class, args);
		System.out.println("<<=============0=============>>");
		System.out.println("  INITIALIZATION COMPLETE!!!");
		System.out.println("<<=============0=============>>");
	}

}
