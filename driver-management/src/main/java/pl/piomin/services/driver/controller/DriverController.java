package pl.piomin.services.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.driver.model.Driver;
import pl.piomin.services.driver.model.DriverInput;
import pl.piomin.services.driver.model.DriverStatus;
import pl.piomin.services.driver.repository.DriverRepository;
import pl.piomin.services.driver.service.DriverLocationService;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverRepository repository;
    @Autowired
    private DriverLocationService driverLocationService;

    @PostMapping
    public Driver create(@RequestBody Driver driver) {
        return repository.add(driver);
    }

    @PutMapping
    public Driver update(@RequestBody DriverInput driverInput) {
        Driver driver = repository.findById(driverInput.getId());
        driver.setBalance(driver.getBalance() + driverInput.getAmount());
        driver.setStatus(driverInput.getStatus());
        repository.update(driver);
        return driver;
    }

    @GetMapping("/{id}")
    public Driver getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping
    public List<Driver> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{locationX}/{locationY}")
    public Driver findNearestDriver(@PathVariable("locationX") int locationX, @PathVariable("locationY") int locationY) {
        List<Driver> drivers = repository.findByStatus(DriverStatus.AVAILABLE);
        return driverLocationService.searchNearestDriver(locationX, locationY, drivers);
    }
}
