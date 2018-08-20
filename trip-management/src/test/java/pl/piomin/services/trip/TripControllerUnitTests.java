package pl.piomin.services.trip;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.piomin.services.trip.client.DriverManagementClient;
import pl.piomin.services.trip.client.PassengerManagementClient;
import pl.piomin.services.trip.controller.TripController;
import pl.piomin.services.trip.model.Driver;
import pl.piomin.services.trip.model.Passenger;
import pl.piomin.services.trip.model.Trip;
import pl.piomin.services.trip.model.TripInput;
import pl.piomin.services.trip.repository.TripRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(TripController.class)
public class TripControllerUnitTests {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TripRepository repository;
    @MockBean
    private PassengerManagementClient passengerManagementClient;
    @MockBean
    private DriverManagementClient driverManagementClient;

    @Before
    public void init() {
        Mockito.when(passengerManagementClient.getPassenger(Mockito.anyString())).thenReturn(new Passenger(1L, "John Smith", "550660770"));
        Mockito.when(driverManagementClient.getNearestDriver(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Driver(1L, "Adam Walker", 20, 10));
        Mockito.when(repository.add(Mockito.any(Trip.class))).thenAnswer(new Answer<Trip>() {
            @Override
            public Trip answer(InvocationOnMock invocation) throws Throwable {
                Trip o = invocation.getArgument(0);
                o.setId(1L);
                return o;
            }
        });
    }

    @Test
    public void testCreateTrip() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/trips").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new TripInput("test", 15, 25, "test"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.any(Integer.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("NEW")));
    }

}
