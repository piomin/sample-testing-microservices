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
        repository.add(new Driver("Paul Walker", 20, 20, DriverStatus.AVAILABLE));
        repository.add(new Driver("Test2", 30, 20, DriverStatus.AVAILABLE));
        repository.add(new Driver("Test3", 40, 20, DriverStatus.AVAILABLE));
        repository.add(new Driver("Test4", 50, 20, DriverStatus.AVAILABLE));
        repository.add(new Driver("Test5", 60, 20, DriverStatus.AVAILABLE));
        return repository;
    }

}
