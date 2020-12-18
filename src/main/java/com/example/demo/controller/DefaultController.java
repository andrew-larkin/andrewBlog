package com.example.demo.controller;

import com.example.demo.api.request.ExchangeRequest;
import com.example.demo.api.response.ExchangeResponse;
import com.example.demo.model.Exchange;
import com.example.demo.service.ExchangeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DefaultController {

    private final ExchangeService exchangeService;

    public DefaultController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }


    @GetMapping("/")
    public String getIndex() {
        return "Hello!";
    }

    @GetMapping("/rates")
    public Exchange getExchange(@RequestParam String in) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.exchangeratesapi.io/latest?base=");
        sb.append(in);
        String req = sb.toString();
        Exchange exchange = restTemplate.getForObject(req, Exchange.class);
        return exchange;

    }

    @GetMapping("/exchange")
    public ResponseEntity<?> getExchange(@RequestBody ExchangeRequest exchangeRequest) {
        final ExchangeResponse exchangeResponse = exchangeService.exchange(exchangeRequest);
        return new ResponseEntity<>(exchangeResponse, HttpStatus.OK);
    }

}
