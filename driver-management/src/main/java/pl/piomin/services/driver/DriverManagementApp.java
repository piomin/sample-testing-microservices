package pl.piomin.services.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.driver.model.Driver;
import pl.piomin.services.driver.model.DriverStatus;
import pl.piomin.services.driver.repository.DriverRepository;

@SpringBootApplication
public class DriverManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(DriverManagementApp.class, args);
    }

    @Bean
    DriverRepository repository() {
        DriverRepository repository = new DriverRepository();
        repository.add(new Driver(1L, "Paul Walker", 10, 20, DriverStatus.AVAILABLE));
        return repository;
    }

}
