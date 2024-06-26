package com.programming.techie.javasftpserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JavaSftpserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaSftpserverApplication.class, args);
		while (true);
	}

}
