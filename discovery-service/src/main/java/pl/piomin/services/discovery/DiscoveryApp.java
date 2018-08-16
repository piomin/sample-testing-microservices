package pl.piomin.services.discovery;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryApp.class).run(args);
    }

}
