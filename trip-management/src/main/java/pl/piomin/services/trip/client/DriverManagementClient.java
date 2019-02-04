package pl.piomin.services.trip.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.piomin.services.trip.model.Driver;
import pl.piomin.services.trip.model.DriverInput;

@FeignClient(name = "driver-management", url = "http://driver-management:8080")
public interface DriverManagementClient {

    @GetMapping("/drivers/{locationX}/{locationY}")
    Driver getNearestDriver(@PathVariable("locationX") int locationX, @PathVariable("locationY") int locationY);

    @PutMapping("/drivers")
    Driver updateDriver(@RequestBody DriverInput driverInput);

}
