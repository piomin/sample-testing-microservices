package pl.piomin.services.passenger;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.MockMvcTarget;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.piomin.services.passenger.controller.PassengerController;
import pl.piomin.services.passenger.model.Passenger;
import pl.piomin.services.passenger.repository.PassengerRepository;

@RunWith(SpringRestPactRunner.class)
@Provider("passengerManagementProvider")
@PactBroker(host = "localhost", port = "9292")
public class PassengerControllerContractTests {

    @InjectMocks
    private PassengerController controller = new PassengerController();
    @Mock
    private PassengerRepository repository;
    @TestTarget
    public final MockMvcTarget target = new MockMvcTarget();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        target.setControllers(controller);
    }

    @State("get-passenger")
    public void testGetPassenger() {
        target.setRunTimes(3);
        Mockito.when(repository.findByLogin(Mockito.anyString()))
                .thenReturn(new Passenger(1L, "Adam Smith", "test", 4000))
                .thenReturn(new Passenger(3L, "Tom Hamilton", "hamilton", 400000))
                .thenReturn(new Passenger(5L, "John Scott", "scott", 222));
    }

    @State("update-passenger")
    public void testUpdatePassenger() {
        target.setRunTimes(1);
        Passenger passenger = new Passenger(1L, "Adam Smith", "test", 4000);
        Mockito.when(repository.findById(1L)).thenReturn(passenger);
        Mockito.when(repository.update(Mockito.any(Passenger.class)))
                .thenReturn(new Passenger(1L, "Adam Smith", "test", 5000));
    }
}
