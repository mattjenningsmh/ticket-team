package edu.citadel.api.controller;

import edu.citadel.service.ThirdPartyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thirdparty")
public class ThirdPartyApiController {

    private final ThirdPartyApiService thirdPartyApiService;

    @Autowired
    public ThirdPartyApiController(ThirdPartyApiService thirdPartyApiService) {
        this.thirdPartyApiService = thirdPartyApiService;
    }

    @GetMapping("/data")
    public ResponseEntity<String> getData() {
        String data = thirdPartyApiService.getSomeData();
        return ResponseEntity.ok(data);
    }
}