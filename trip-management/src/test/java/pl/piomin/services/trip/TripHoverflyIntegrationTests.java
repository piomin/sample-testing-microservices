package pl.piomin.services.trip;

import io.specto.hoverfly.junit.api.HoverflyClient;
import io.specto.hoverfly.junit.core.HoverflyConfig;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import pl.piomin.services.trip.client.PassengerManagementClient;
import pl.piomin.services.trip.model.Passenger;

import java.nio.file.Paths;

@SpringBootTest(properties = {
        "eureka.client.registerWithEureka=false",
        "ribbon.eureka.enable=false"
})
@RunWith(SpringRunner.class)
public class TripHoverflyIntegrationTests {

    @Autowired
    PassengerManagementClient passengerManagementClient;

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule
//            .inCaptureMode("passenger-management-2.json")
            .inCaptureMode("passenger-management.json", HoverflyConfig.remoteConfigs().host("192.168.99.100"))
//            .inSpyMode(SimulationSource.file(Paths.get("src/test/resources/hoverfly","passenger-management.json")))
            .printSimulationData();

    @Test
    public void testGetPassengerByLogin() {
        final String login = "walker";
        Passenger passenger = passengerManagementClient.getPassenger(login);
        Assert.notNull(passenger, "No passenger");
        Assert.notNull(passenger.getId(), "No passenger id");
        Assert.notNull(passenger.getName(), "No passenger name");
        Assert.notNull(passenger.getLogin(), "No passenger login");
        Assert.isTrue(passenger.getLogin().equals(login), "Invalid passenger login");

        passenger = passengerManagementClient.getPassenger("smith");
        Assert.notNull(passenger, "No passenger");
        Assert.notNull(passenger.getId(), "No passenger id");
        Assert.notNull(passenger.getName(), "No passenger name");
        Assert.notNull(passenger.getLogin(), "No passenger login");
        Assert.isTrue(passenger.getLogin().equals("smith"), "Invalid passenger login");
    }

}
