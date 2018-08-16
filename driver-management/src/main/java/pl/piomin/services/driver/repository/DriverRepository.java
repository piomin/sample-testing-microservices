package pl.piomin.services.driver.repository;

import pl.piomin.services.driver.model.Driver;
import pl.piomin.services.driver.model.DriverStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DriverRepository {

    private List<Driver> drivers = new ArrayList<>();

    public Driver add(Driver driver) {
        driver.setId(drivers.size() + 1L);
        drivers.add(driver);
        return driver;
    }

    public void update(Driver driver) {
        int index = drivers.indexOf(driver);
        drivers.set(index, driver);
    }

    public Driver findById(Long id) {
        return drivers.stream().filter(t -> t.getId().equals(id)).findAny().get();
    }

    public List<Driver> findAll() {
        return drivers;
    }

    public List<Driver> findByStatus(DriverStatus status) {
        return drivers.stream().filter(d -> d.getStatus().equals(status)).collect(Collectors.toList());
    }

}
