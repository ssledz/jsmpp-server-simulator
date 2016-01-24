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

import com.google.common.eventbus.EventBus;

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

    @Bean
    public DefaultServerMessageReceiverListener defaultServerMessageReceiverListener() {
        return new DefaultServerMessageReceiverListener();
    }

    @Bean
    public DefaultServerResponseDelivery defaultServerResponseDelivery() {
        return new DefaultServerResponseDelivery();
    }

    @Bean
    public EventBus eventBus() {
        return new EventBus();
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
