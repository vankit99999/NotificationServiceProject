package com.meesho.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories("com.meesho.notificationservice.repositories.JPArepositories")
public class NotificationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
}
