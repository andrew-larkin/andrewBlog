package com.example.demo.api.response;

import lombok.Data;

import java.math.BigDecimal;

public class ExchangeResponse {

    private long id;
    private BigDecimal outSum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getOutSum() {
        return outSum;
    }

    public void setOutSum(BigDecimal outSum) {
        this.outSum = outSum;
    }
}
