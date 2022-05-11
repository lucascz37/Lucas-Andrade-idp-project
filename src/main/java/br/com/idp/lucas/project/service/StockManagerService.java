package br.com.idp.lucas.project.service;

import br.com.idp.lucas.project.dto.NotificationRequest;
import br.com.idp.lucas.project.dto.StockManagerDTO;
import br.com.idp.lucas.project.exception.StockManagerException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockManagerService {

    private final RestTemplate template;

    private final String url;

    private final NotificationRequest notificationRequest;


    public StockManagerService(RestTemplate template, String url, NotificationRequest notificationRequest) {
        this.template = template;
        this.url = url;
        this.notificationRequest = notificationRequest;
        setNotify();
    }

    public void setNotify(){
        template.postForEntity(url + "/notification", notificationRequest, String.class);
    }


    @Cacheable("stocks")
    public Map<String, String> getStocks(){
        ParameterizedTypeReference<List<StockManagerDTO>> responseType = new ParameterizedTypeReference<>() {};
        RequestEntity<Void> request = RequestEntity.get(url + "/stock")
                .accept(MediaType.APPLICATION_JSON).build();

        ResponseEntity<List<StockManagerDTO>> response;
        try{
            response = template.exchange(request, responseType);
        }catch (RestClientException ignored){
            throw new StockManagerException("Service Unavailable");
        }

        if(response.getBody() == null){
            return Map.of();
        }

        return response.getBody().stream().collect(Collectors.toMap(StockManagerDTO::getId, StockManagerDTO::getDescription));
    }
}
