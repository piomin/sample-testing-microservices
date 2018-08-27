package pl.piomin.services.trip;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import io.specto.hoverfly.junit.dsl.HttpBodyConverter;
import io.specto.hoverfly.junit.dsl.ResponseCreators;
import io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.hamcrest.Matcher;
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
import org.springframework.util.Assert;
import pl.piomin.services.trip.client.PassengerManagementClient;
import pl.piomin.services.trip.model.Passenger;
import pl.piomin.services.trip.model.Trip;
import pl.piomin.services.trip.model.TripInput;

@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "ribbon.eureka.enable=false",
        "passenger-management.ribbon.listOfServers=passenger-management",
        "driver-management.ribbon.listOfServers=driver-management"
})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TripComponentTests {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @ClassRule
    public static HoverflyRule rule = HoverflyRule.inSimulationMode(SimulationSource.dsl(
            HoverflyDsl.service("passenger-management:80")
                    .get(HoverflyMatchers.startsWith("/passengers/login/"))
                        .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes("{'id':1,'name':'John Walker'}")))
                    .put(HoverflyMatchers.startsWith("/passengers")).anyBody()
                        .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes("{'id':1,'name':'John Walker'}"))),
            HoverflyDsl.service("driver-management:80")
                    .get(HoverflyMatchers.startsWith("/drivers/"))
                        .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes("{'id':1,'name':'David Smith','currentLocationX': 15,'currentLocationY':25}")))
                    .put(HoverflyMatchers.startsWith("/drivers")).anyBody()
                        .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes("{'id':1,'name':'David Smith','currentLocationX': 15,'currentLocationY':25}")))
    )).printSimulationData();

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
