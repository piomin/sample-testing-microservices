package pl.piomin.services.passenger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.passenger.model.Passenger;
import pl.piomin.services.passenger.repository.PassengerRepository;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerRepository repository;

    @PostMapping
    public Passenger create(@RequestBody Passenger passenger) {
        return repository.add(passenger);
    }

    @GetMapping("/{id}")
    public Passenger getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping
    public List<Passenger> getAll() {
        return repository.findAll();
    }

}
