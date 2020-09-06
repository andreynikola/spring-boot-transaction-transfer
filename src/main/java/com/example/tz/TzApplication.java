package com.example.tz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Andrey Nikola
 * @version 1.0
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
@PropertySource("file:${external.config.location}/application.properties")
public class TzApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TzApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
