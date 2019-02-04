package pl.piomin.services.passenger;

import io.specto.hoverfly.junit.core.HoverflyConfig;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import pl.piomin.services.passenger.model.Passenger;

public class PassengerControllerComponentTests {

    private TestRestTemplate template = new TestRestTemplate();

    @ClassRule
    public static GenericContainer appContainer = new GenericContainer<>("piomin/passenger-management")
            .withCreateContainerCmdModifier(cmd -> cmd.withName("passenger-management").withHostName("passenger-management"))
            .withNetworkMode("tests")
            .withNetworkAliases("passenger-management")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/passengers"));

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule
            .inCaptureMode("passenger.json", HoverflyConfig.remoteConfigs().host("192.168.99.100"))
            .printSimulationData();

    @Test
    public void testFindByLogin() {
        Passenger passenger = template.getForObject("http://passenger-management:8080/passengers/login/{login}", Passenger.class, "walker");
        Assert.assertNotNull(passenger);
    }

}
