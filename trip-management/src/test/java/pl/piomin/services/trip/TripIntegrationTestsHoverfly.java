package pl.piomin.services.trip;

import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import pl.piomin.services.trip.client.PassengerManagementClient;
import pl.piomin.services.trip.model.Passenger;

@SpringBootTest(properties = "eureka.client.registerWithEureka=false")
@RunWith(SpringRunner.class)
public class TripIntegrationTestsHoverfly {

    @Autowired
    PassengerManagementClient passengerManagementClient;

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule
            .inCaptureOrSimulationMode("passenger-management.json")
            .printSimulationData();

    @Test
    public void testGetPassengerByLogin() {
        Passenger passenger = passengerManagementClient.getPassenger("walker");
        Assert.notNull(passenger, "No passenger");
    }

}
