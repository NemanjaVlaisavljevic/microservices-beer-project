package guru.sfg.springframework.msscbeerservice.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientCommuncationConfig {

    private final String inventoryUser = "good";
    private final String inventoryPassword = "beer";

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor(inventoryUser , inventoryPassword);
    }
}
