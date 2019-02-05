package pl.piomin.services.passenger;

import io.specto.hoverfly.junit.core.HoverflyConfig;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import pl.piomin.services.passenger.model.Passenger;
import pl.piomin.services.passenger.model.PassengerInput;

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

    @Test
    public void testUpdatePassenger() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        PassengerInput input = new PassengerInput();
        input.setId(1L);
        input.setAmount(2000);
        HttpEntity<PassengerInput> entity = new HttpEntity<>(input, headers);
        template.put("http://passenger-management:8080/passengers", entity);
        input.setAmount(0);
        entity = new HttpEntity<>(input, headers);
        template.put("http://passenger-management:8080/passengers", entity);
    }

}
