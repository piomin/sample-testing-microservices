package pl.piomin.services.passenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.passenger.repository.PassengerRepository;

@SpringBootApplication
public class PassengerManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(PassengerManagementApp.class, args);
    }

    @Bean
    PassengerRepository repository() {
        PassengerRepository repository = new PassengerRepository();
        return repository;
    }

}
