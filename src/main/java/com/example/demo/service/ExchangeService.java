package com.example.demo.service;

import com.example.demo.api.request.ExchangeRequest;
import com.example.demo.api.response.ExchangeResponse;
import com.example.demo.model.Exchange;
import com.example.demo.model.Query;
import com.example.demo.model.User;
import com.example.demo.repository.QueryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ExchangeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QueryRepository queryRepository;

    public ExchangeService(UserRepository userRepository, QueryRepository queryRepository) {
        this.userRepository = userRepository;
        this.queryRepository = queryRepository;
    }

    public ExchangeResponse exchange(ExchangeRequest exchangeRequest) {
        ExchangeResponse exchangeResponse = new ExchangeResponse();
        long userId = exchangeRequest.getUserId();
        BigDecimal inSum = exchangeRequest.getInSum();
        String inCurrency = exchangeRequest.getInCurrency();
        String outCurrency = exchangeRequest.getOutCurrency();

        RestTemplate restTemplate = new RestTemplate();
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.exchangeratesapi.io/latest?base=");
        sb.append(inCurrency);
        String req = sb.toString();
        Exchange exchange = restTemplate.getForObject(req, Exchange.class);

        BigDecimal outSum = new BigDecimal(0);
        for (String currency : exchange.getRates().keySet()) {
            if (currency.equals(outCurrency)) {
                outSum = exchange.getRates().get(currency);
            }
        }

        exchangeResponse.setOutSum(inSum.multiply(outSum));


        Query query = new Query();
       double d = Math.random()*100000;
       String w = String.valueOf(d);
       String b = w.substring(0, w.indexOf(".")-1);

       Long s = Long.parseLong(b);


            query.setId(s);
            exchangeResponse.setId(s);


        Iterable<User> userIterable = userRepository.findAll();
        for (User user : userIterable) {
            if (user.getId() == userId) {
                query.setUser(user);
            }
        }

        query.setInSum(inSum);
        query.setInCurrency(inCurrency);
        query.setOutCurrency(outCurrency);
        query.setOutSum(inSum.multiply(outSum));

        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(exchange.getDate(), DATEFORMATTER);
        LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());

        query.setTime(ldt);

        queryRepository.save(query);
        return exchangeResponse;


    }

}
