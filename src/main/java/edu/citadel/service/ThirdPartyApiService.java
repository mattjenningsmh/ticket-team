package edu.citadel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ThirdPartyApiService {

    @Value("${thirdparty.api.base-url}")
    private String baseUrl;

    @Value("${thirdparty.api.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public ThirdPartyApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getSomeData() {
        String url = baseUrl + "/data?apiKey=" + apiKey;
        return restTemplate.getForObject(url, String.class);
    }
}
