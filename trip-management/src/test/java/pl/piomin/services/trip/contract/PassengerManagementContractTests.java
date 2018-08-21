package pl.piomin.services.trip.contract;

import org.springframework.boot.test.mock.mockito.MockBean;
import pl.piomin.services.trip.client.PassengerManagementClient;

public class PassengerManagementContractTests {

    @MockBean
    private PassengerManagementClient client;
}
