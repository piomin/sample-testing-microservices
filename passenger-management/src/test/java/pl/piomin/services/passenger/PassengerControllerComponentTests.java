package pl.piomin.services.passenger;

import io.specto.hoverfly.junit.core.HoverflyConfig;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

public class PassengerControllerComponentTests {

    private TestRestTemplate template = new TestRestTemplate();

    @ClassRule
    public static GenericContainer app = new GenericContainer("piomin/passenger-management")
            .withExposedPorts(8080)
            .withNetwork(Network.builder().id("tests").build());

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule
            .inCaptureMode("passenger.json", HoverflyConfig.remoteConfigs().host("192.168.99.100"))
            .printSimulationData();

    @Test
    public void testFindByLogin() {

    }
}
