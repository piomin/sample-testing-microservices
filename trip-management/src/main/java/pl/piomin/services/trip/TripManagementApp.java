package pl.piomin.services.trip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.trip.repository.TripRepository;

@SpringBootApplication
@EnableFeignClients
public class TripManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(TripManagementApp.class, args);
    }

    @Bean
    TripRepository repository() {
        TripRepository repository = new TripRepository();
        return repository;
    }

}
