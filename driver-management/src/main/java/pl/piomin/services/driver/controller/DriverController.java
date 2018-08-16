package pl.piomin.services.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.driver.model.Driver;
import pl.piomin.services.driver.repository.DriverRepository;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverRepository repository;

    @PostMapping
    public Driver create(@RequestBody Driver driver) {
        return repository.add(driver);
    }

    @GetMapping("/{id}")
    public Driver getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping
    public List<Driver> getAll() {
        return repository.findAll();
    }
}
