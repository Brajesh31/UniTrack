package com.bash.Unitrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class UniTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniTrackApplication.class, args);
	}

}
