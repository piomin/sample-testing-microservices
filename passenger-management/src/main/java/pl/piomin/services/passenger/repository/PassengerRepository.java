package pl.piomin.services.passenger.repository;

import pl.piomin.services.passenger.model.Passenger;

import java.util.ArrayList;
import java.util.List;

public class PassengerRepository {

    private List<Passenger> passengers = new ArrayList<>();

    public Passenger add(Passenger passenger) {
        passenger.setId(passengers.size() + 1L);
        passengers.add(passenger);
        return passenger;
    }

    public Passenger update(Passenger passenger) {
        int index = passengers.indexOf(passenger);
        passengers.set(index, passenger);
        return passenger;
    }

    public Passenger findById(Long id) {
        return passengers.stream().filter(t -> t.getId().equals(id)).findAny().get();
    }

    public List<Passenger> findAll() {
        return passengers;
    }

    public Passenger findByLogin(String login) {
        return passengers.stream().filter(t -> t.getLogin().equals(login)).findAny().get();
    }

}
