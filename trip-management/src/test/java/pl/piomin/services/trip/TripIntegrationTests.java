package pl.piomin.services.trip;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.specto.hoverfly.junit.core.HoverflyConfig;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.hamcrest.Matchers;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.piomin.services.trip.model.Trip;
import pl.piomin.services.trip.model.TripInput;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TripIntegrationTests {

    ObjectMapper mapper = new ObjectMapper();

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule
            .inSimulationMode(HoverflyConfig.remoteConfigs().host("192.168.99.100"))
            .printSimulationData();

    @Autowired
    MockMvc mockMvc;

    @Test
    public void test1CreateNewTrip() throws Exception {
        TripInput ti = new TripInput("test", 10, 20, "walker");
        mockMvc.perform(MockMvcRequestBuilders.post("/trips")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(ti)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.any(Integer.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("NEW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.driverId", Matchers.any(Integer.class)));
    }

    @Test
    public void test2CancelTrip() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/trips/cancel/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(new Trip())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.any(Integer.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("IN_PROGRESS")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.driverId", Matchers.any(Integer.class)));
    }

    @Test
    public void test3PayTrip() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/trips/payment/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(new Trip())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.any(Integer.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("PAYED")));
    }

}
