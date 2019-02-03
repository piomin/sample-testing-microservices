package pl.piomin.services.passenger.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.passenger.model.Passenger;
import pl.piomin.services.passenger.model.PassengerInput;
import pl.piomin.services.passenger.repository.PassengerRepository;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    private static final Logger LOG = LoggerFactory.getLogger(PassengerController.class);

    @Autowired
    private PassengerRepository repository;

    @PostMapping
    public Passenger create(@RequestBody Passenger passenger) {
        return repository.add(passenger);
    }

    @PutMapping
    public Passenger update(@RequestBody PassengerInput passengerInput) {
        Passenger passenger = repository.findById(passengerInput.getId());
        passenger.setBalance(passenger.getBalance() + passengerInput.getAmount());
        return repository.update(passenger);
    }

    @GetMapping("/{id}")
    public Passenger getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping("/login/{login}")
    public Passenger getByLogin(@PathVariable String login) {
        LOG.info("Find by login: {}", login);
        return repository.findByLogin(login);
    }

    @GetMapping
    public List<Passenger> getAll() {
        return repository.findAll();
    }

}
