package pl.piomin.services.driver.contract;

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
import pl.piomin.services.driver.model.Driver;
import pl.piomin.services.driver.model.DriverInput;
import pl.piomin.services.driver.model.DriverStatus;

public class DriverControllerIntegrationTests {

    private TestRestTemplate template = new TestRestTemplate();

    @ClassRule
    public static GenericContainer appContainer = new GenericContainer<>("piomin/driver-management")
            .withCreateContainerCmdModifier(cmd -> cmd.withName("driver-management").withHostName("driver-management"))
            .withNetworkMode("tests")
            .withNetworkAliases("driver-management")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/drivers"));

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule
            .inCaptureMode("driver.json", HoverflyConfig.remoteConfigs().host("192.168.99.100"))
            .printSimulationData();

    @Test
    public void testFindNearestDriver() {
        Driver driver = template.getForObject("http://driver-management:8080/drivers/{locationX}/{locationY}", Driver.class, 40, 20);
        Assert.assertNotNull(driver);
        driver = template.getForObject("http://driver-management:8080/drivers/{locationX}/{locationY}", Driver.class, 10, 20);
        Assert.assertNotNull(driver);
    }

    @Test
    public void testUpdateDriver() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        DriverInput input = new DriverInput();
        input.setId(2L);
        input.setStatus(DriverStatus.UNAVAILABLE);
        HttpEntity<DriverInput> entity = new HttpEntity<>(input, headers);
        template.put("http://driver-management:8080/drivers", entity);
        input.setId(1L);
        input.setStatus(DriverStatus.AVAILABLE);
        entity = new HttpEntity<>(input, headers);
        template.put("http://driver-management:8080/drivers", entity);
    }

}
