package com.n26.challenges;

import com.n26.challenges.util.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application starter.
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    private static final int MAX_TRANSACTION_TIME_IN_SECONDS = 60;

    /**
     * Starting point of the application.
     *
     * @param args arguments to add
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Builds an application configuration bean.
     *
     * @return {@link AppConfig}
     */
    @Bean
    public AppConfig configuration() {
        return new AppConfig(MAX_TRANSACTION_TIME_IN_SECONDS);
    }
}
