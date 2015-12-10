package pl.softech.smpp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsmpp.util.MessageIDGenerator;
import org.jsmpp.util.RandomMessageIDGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
public class App {

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(5);
    }

    @Bean
    public MessageIDGenerator messageIDGenerator() {
        return new RandomMessageIDGenerator();
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
