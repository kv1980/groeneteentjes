package be.vdab.groenetenen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class GroenetenenApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroenetenenApplication.class, args);
	}
}
