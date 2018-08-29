package pl.piomin.services.passenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.passenger.model.Passenger;
import pl.piomin.services.passenger.repository.PassengerRepository;

@SpringBootApplication
public class PassengerManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(PassengerManagementApp.class, args);
    }

    @Bean
    PassengerRepository repository() {
        PassengerRepository repository = new PassengerRepository();
        repository.add(new Passenger("Paul Walker", "walker", "550660770", 1000, 40, 20));
        repository.add(new Passenger("Adam Smith", "smith", "550660771", 2000, 50, 20));
        repository.add(new Passenger("Richard Hamilton", "hamilton", "550660772", 3000, 70, 20));
        repository.add(new Passenger("Tom Scott", "scott", "550660773", 4000, 30, 20));
        repository.add(new Passenger("Wayne Holmes", "holmes", "550660774", 5000, 60, 20));
        return repository;
    }

}
