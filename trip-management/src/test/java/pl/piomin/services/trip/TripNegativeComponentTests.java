package pl.piomin.services.trip;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.piomin.services.trip.controller.TripController;
import pl.piomin.services.trip.model.TripInput;

import java.util.concurrent.TimeUnit;

@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "ribbon.eureka.enable=false",
        "passenger-management.ribbon.listOfServers=passenger-management",
        "driver-management.ribbon.listOfServers=driver-management",
        "feign.hystrix.enabled=false",
        "ribbon.ReadTimeout=500"
})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TripNegativeComponentTests {

    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @ClassRule
    public static HoverflyRule rule = HoverflyRule.inSimulationMode(SimulationSource.dsl(
            HoverflyDsl.service("passenger-management:80").andDelay(2000, TimeUnit.MILLISECONDS).forAll()
                    .get(HoverflyMatchers.startsWith("/passengers/"))
                    .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes("{'id':1,'name':'John Smith'}")))
    )).printSimulationData();

    @Test
    public void testCreateTrip() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/trips").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new TripInput("test", 15, 25, "test"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("REJECTED")));
    }
}
