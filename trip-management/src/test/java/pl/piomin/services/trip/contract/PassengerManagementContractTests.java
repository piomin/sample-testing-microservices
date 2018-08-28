package pl.piomin.services.trip.contract;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piomin.services.trip.client.PassengerManagementClient;
import pl.piomin.services.trip.model.Passenger;
import pl.piomin.services.trip.model.PassengerInput;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "driver-management.ribbon.listOfServers=localhost:8190",
        "passenger-management.ribbon.listOfServers=localhost:8180",
        "ribbon.eureka.enabled=false",
        "eureka.client.enabled=false",
        "ribbon.ReadTimeout=5000"
})
public class PassengerManagementContractTests {

    @Rule
    public PactProviderRuleMk2 stubProvider = new PactProviderRuleMk2("passengerManagementProvider", "localhost", 8180, this);
    @Autowired
    private PassengerManagementClient passengerManagementClient;

    @Pact(state = "get-passenger", provider = "passengerManagementProvider", consumer = "passengerManagementClient")
    public RequestResponsePact callGetPassenger(PactDslWithProvider builder) {
        DslPart body = new PactDslJsonBody().integerType("id").stringType("name").numberType("balance").close();
        return builder.given("get-passenger").uponReceiving("test-get-passenger")
                .path("/passengers/login/test").method("GET").willRespondWith().status(200).body(body).toPact();
//                .bodyWithSingleQuotes("{'id':1,'name':'Adam Smith','balance':4000}", "application/json").toPact();
    }

    @Pact(state = "update-passenger", provider = "passengerManagementProvider", consumer = "passengerManagementClient")
    public RequestResponsePact callUpdatePassenger(PactDslWithProvider builder) {
        return builder.given("update-passenger").uponReceiving("test-update-passenger")
                .path("/passengers").method("PUT").bodyWithSingleQuotes("{'id':1,'amount':1000}", "application/json").willRespondWith().status(200)
                .bodyWithSingleQuotes("{'id':1,'name':'Adam Smith','balance':5000}", "application/json").toPact();
    }

    @Test
    @PactVerification(fragment = "callGetPassenger")
    public void verifyGetPassengerPact() {
        Passenger passenger = passengerManagementClient.getPassenger("test");
        Assert.assertNotNull(passenger);
        Assert.assertNotNull(passenger.getId());
    }

    @Test
    @PactVerification(fragment = "callUpdatePassenger")
    public void verifyUpdatePassengerPact() {
        Passenger passenger = passengerManagementClient.updatePassenger(new PassengerInput(1L, 1000));
        Assert.assertNotNull(passenger);
        Assert.assertNotNull(passenger.getId());
    }

}
