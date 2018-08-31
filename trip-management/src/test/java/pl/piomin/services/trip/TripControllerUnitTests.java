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
import pl.piomin.services.trip.model.*;
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
        Mockito.when(passengerManagementClient.updatePassenger(Mockito.any(PassengerInput.class))).thenReturn(new Passenger(1L, "John Smith", "550660770"));
        Mockito.when(driverManagementClient.getNearestDriver(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Driver(1L, "Adam Walker", 20, 10));
        Mockito.when(driverManagementClient.updateDriver(Mockito.any(DriverInput.class))).thenReturn(new Driver(1L, "Adam Walker", 20, 10));
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(new Trip(1L, 10, 20, 1L, 1L));
        Mockito.when(repository.update(Mockito.any(Trip.class))).thenAnswer(new Answer<Trip>() {
            @Override
            public Trip answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArgument(0);
            }
        });
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

    @Test
    public void testCancelTrip() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/trips/cancel/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new TripInput())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.any(Integer.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("IN_PROGRESS")));
    }

    @Test
    public void testPayTrip() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/trips/payment/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new TripInput())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.any(Integer.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("PAYED")));
    }

}
