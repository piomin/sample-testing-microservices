package pl.piomin.services.trip.repository;

import pl.piomin.services.trip.model.Trip;
import pl.piomin.services.trip.model.TripStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TripRepository {

    private List<Trip> trips = new ArrayList<>();

    public Trip add(Trip trip) {
        trip.setId(trips.size() + 1L);
        trips.add(trip);
        return trip;
    }

    public Trip update(Trip trip) {
        int index = trips.indexOf(trip);
        return trips.set(index, trip);
    }

    public Trip findById(Long id) {
        return trips.stream().filter(t -> t.getId().equals(id)).findAny().get();
    }

    public List<Trip> findAll() {
        return trips;
    }

    public List<Trip> findByStatus(TripStatus status) {
        return trips.stream().filter(t -> t.getStatus().equals(status)).collect(Collectors.toList());
    }

}
