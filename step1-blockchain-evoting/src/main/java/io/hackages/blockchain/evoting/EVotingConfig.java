package io.hackages.blockchain.evoting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Basic configuration for the application.
 *
 * @author Tata DA SILVEIRA
 */
@Configuration
@EnableSwagger2 // initialize Swagger.
@ComponentScan({"io.hackages.blockchain.evoting", "io.hackages.blockchain.evoting.service", "io.hackages.blockchain.evoting.helper", "io.hackages.blockchain.evoting.api"})
public class EVotingConfig {

    /**
     * initialize the Swagger docklet.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

    /*
    @Bean
    public HashHelper hashHelper() {
        return new HashHelper();
    }
    */

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}