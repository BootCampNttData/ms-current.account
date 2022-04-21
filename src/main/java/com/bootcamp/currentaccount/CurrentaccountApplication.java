package com.bootcamp.currentaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrentaccountApplication {

	public static void main(String[] args) {
		System.setProperty("jdk.tls.client.protocols","TLSv1.2");
		SpringApplication.run(CurrentaccountApplication.class, args);
	}

}
