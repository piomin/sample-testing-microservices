package pl.piomin.services.trip.contract;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piomin.services.trip.client.DriverManagementClient;
import pl.piomin.services.trip.model.Driver;
import pl.piomin.services.trip.model.DriverInput;
import pl.piomin.services.trip.model.DriverStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "driver-management.ribbon.listOfServers=localhost:8080",
        "ribbon.eureka.enabled=false",
        "eureka.client.enabled=false",
        "ribbon.ReadTimeout=5000"
})
public class DriverManagementContractTests {

    @Rule
    public PactProviderRuleMk2 stubProvider = new PactProviderRuleMk2("driverManagementProvider", "localhost", 8080, this);
    @Autowired
    private DriverManagementClient driverManagementClient;

    @Pact(state = "get-nearest-driver", provider = "driverManagementProvider", consumer = "driverManagementClient")
    public RequestResponsePact callGetNearestDriver(PactDslWithProvider builder) {
        return builder.given("get-nearest-driver").uponReceiving("test-get-nearest-driver")
                .path("/drivers/10/20").method("GET").willRespondWith().status(200)
                .bodyWithSingleQuotes("{'id':1,'name':'Peter Walker','balance':4000}", "application/json").toPact();
    }

    @Pact(state = "update-driver", provider = "driverManagementProvider", consumer = "driverManagementClient")
    public RequestResponsePact callUpdatePassenger(PactDslWithProvider builder) {
        return builder.given("update-driver").uponReceiving("test-update-driver")
                .path("/drivers").method("PUT").bodyWithSingleQuotes("{'id':1,'status':'UNAVAILABLE'}", "application/json").willRespondWith().status(200)
                .bodyWithSingleQuotes("{'id':1,'name':'Adam Smith','status':'UNAVAILABLE'}", "application/json").toPact();
    }

    @Test
    @PactVerification(fragment = "callGetNearestDriver")
    public void verifyGetNearestDriverPact() {
        Driver driver = driverManagementClient.getNearestDriver(10,20);
        Assert.assertNotNull(driver);
        Assert.assertNotNull(driver.getId());
    }

    @Test
    @PactVerification(fragment = "callUpdateDriver")
    public void verifyUpdateDriverPact() {
        Driver driver = driverManagementClient.updateDriver(new DriverInput(1L, DriverStatus.UNAVAILABLE));
        Assert.assertNotNull(driver);
        Assert.assertNotNull(driver.getId());
    }

}
