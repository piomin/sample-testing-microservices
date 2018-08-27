package pl.piomin.services.trip;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.specto.hoverfly.junit.core.HoverflyConfig;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import io.specto.hoverfly.junit.dsl.HttpBodyConverter;
import io.specto.hoverfly.junit.dsl.ResponseCreators;
import io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.hamcrest.Matchers;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import pl.piomin.services.trip.model.TripInput;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootTest(properties = "eureka.client.registerWithEureka=false")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TripDiffIntegrationTests {

    @Value("${assertDiffs}")
    boolean assertDiffs;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;
    @ClassRule
    public static HoverflyRule hoverflyRule;

    static {
        try {
            hoverflyRule = HoverflyRule.inDiffMode(SimulationSource.dsl(
                    HoverflyDsl.service(HoverflyMatchers.startsWith(InetAddress.getLocalHost().getHostName()))
                            .get(HoverflyMatchers.startsWith("/passengers/"))
                            .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes("{'id':1,'name':'John Smith'}")).header("Content-Type", "application/json;charset=UTF-8")),
                    HoverflyDsl.service(HoverflyMatchers.startsWith(InetAddress.getLocalHost().getHostName()))
                            .get(HoverflyMatchers.startsWith("/drivers/"))
                            .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes("{'id':1,'name':'David Smith','currentLocationX': 15,'currentLocationY':25}")))
            )).printSimulationData();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateNewTrip() throws Exception {
        TripInput ti = new TripInput("test", 10, 20, "walker");
        mockMvc.perform(MockMvcRequestBuilders.post("/trips")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(ti)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.any(Integer.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("NEW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.driverId", Matchers.any(Integer.class)));
        if (assertDiffs)
            hoverflyRule.assertThatNoDiffIsReported();
    }

}
