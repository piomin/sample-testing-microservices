package pl.piomin.services.trip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.trip.client.DriverManagementClient;
import pl.piomin.services.trip.client.PassengerManagementClient;
import pl.piomin.services.trip.model.*;
import pl.piomin.services.trip.repository.TripRepository;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripRepository repository;
    @Autowired
    private DriverManagementClient driverManagementClient;
    @Autowired
    private PassengerManagementClient passengerManagementClient;

    @PostMapping
    public Trip create(@RequestBody TripInput tripInput) {
        Trip trip = new Trip();
        Passenger passenger = passengerManagementClient.getPassenger(tripInput.getUsername());
        trip.setPassengerId(passenger.getId());
        Driver driver  = driverManagementClient.getNearestDriver(passenger.getHomeLocationX(), passenger.getHomeLocationY());
        trip.setDriverId(driver.getId());
        trip.setStatus(TripStatus.NEW);
        trip = repository.add(trip);
        return trip;
    }

    @PutMapping("/cancel/{id}")
    public Trip cancel(@PathVariable("id") Long id) {
        Trip trip = repository.findById(id);
        driverManagementClient.updateDriver(new DriverInput(trip.getDriverId(), DriverStatus.UNAVAILABLE));
        Driver driver  = driverManagementClient.getNearestDriver(trip.getLocationX(), trip.getLocationY());
        trip.setDriverId(driver.getId());
        return trip;
    }

    @PutMapping("/payment/{id}")
    public Trip payment(@PathVariable("id") Long id) {
        Trip trip = repository.findById(id);
        passengerManagementClient.updatePassenger(new PassengerInput(id, trip.getPrice()));
        driverManagementClient.updateDriver(new DriverInput(trip.getDriverId(), trip.getPrice()));
        trip.setStatus(TripStatus.PAYED);
        return trip;
    }

    @GetMapping("/{id}")
    public Trip getById(Long id) {
        return repository.findById(id);
    }

    @GetMapping("/")
    public List<Trip> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{staus}")
    public List<Trip> getByStatus(TripStatus status) {
        return repository.findByStatus(status);
    }

}
