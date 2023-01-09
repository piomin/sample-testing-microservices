package pl.piomin.services.driver.contract;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.MockMvcTarget;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.piomin.services.driver.controller.DriverController;
import pl.piomin.services.driver.model.Driver;
import pl.piomin.services.driver.model.DriverStatus;
import pl.piomin.services.driver.repository.DriverRepository;

import java.util.Collections;

@RunWith(SpringRestPactRunner.class)
@Provider("driverManagementProvider")
@PactBroker(host = "localhost", port = "9292")
public class DriverControllerContractTests {

    @InjectMocks
    private DriverController controller = new DriverController();
    @Mock
    private DriverRepository repository;
    @TestTarget
    public final MockMvcTarget target = new MockMvcTarget();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        target.setControllers(controller);
    }

    @State("get-nearest-driver")
    public void testGetNearestDriver() {
        target.setRunTimes(1);
        Mockito.when(repository.findByStatus(Mockito.any(DriverStatus.class)))
                .thenReturn(Collections.singletonList(new Driver(1L, "Peter Walker", 10, 10, DriverStatus.AVAILABLE)));
    }

    @State("update-driver")
    public void testUpdateDriver() {
        target.setRunTimes(1);
        Driver driver = new Driver(1L, 1000, DriverStatus.AVAILABLE);
        Mockito.when(repository.findById(1L)).thenReturn(driver);
        Mockito.when(repository.update(Mockito.any(Driver.class)))
                .thenReturn(new Driver(1L, 1000, DriverStatus.AVAILABLE));
    }
}
