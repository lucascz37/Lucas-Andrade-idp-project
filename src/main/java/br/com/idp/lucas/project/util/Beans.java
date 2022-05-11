package br.com.idp.lucas.project.util;

import br.com.idp.lucas.project.dto.NotificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Beans {

    @Value("${app.url}")
    private String host;

    @Value("${server.port}")
    private Integer port;

    @Value("${app.stock-manager.protocol}")
    private String protocol;

    @Value("${app.stock-manager.url}")
    private String stockManagerUrl;

    @Value("${app.stock-manager.port}")
    private String getStockManagerPort;

    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }

    @Bean
    public NotificationRequest notification(){
        return new NotificationRequest(host, port);
    }

    @Bean
    public String stockManagerUrl(){
        return protocol + "://" + stockManagerUrl + ":" + getStockManagerPort;
    }
}
