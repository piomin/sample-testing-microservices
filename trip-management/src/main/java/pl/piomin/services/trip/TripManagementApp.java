package pl.piomin.services.trip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.trip.repository.TripRepository;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class TripManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(TripManagementApp.class, args);
    }

    @Bean
    TripRepository repository() {
        TripRepository repository = new TripRepository();
        return repository;
    }

    @Bean
    public Docket swaggerPersonApi10() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pl.piomin.services.trip.controller"))
                .paths(regex("/trips/*"))
                .build()
                .apiInfo(new ApiInfoBuilder().version("1.0").title("Trip Management").description("Documentation Trip API").build());
    }

}
