package pl.piomin.services.trip.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.piomin.services.trip.model.Passenger;
import pl.piomin.services.trip.model.PassengerInput;

@FeignClient(name = "passenger-management", url = "http://MINKOP1-L:8080")
public interface PassengerManagementClient {

    @GetMapping("/passengers/login/{login}")
    Passenger getPassenger(@PathVariable("login") String login);

    @PutMapping("/passengers")
    Passenger updatePassenger(@RequestBody PassengerInput passengerInput);

}
