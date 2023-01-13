package com.techelevator.models;

import java.math.BigDecimal;

public class Candy extends Item{
    private String type = "Candy";
    public Candy(String slot, String name, BigDecimal price) {
        super(slot, name, price);

    }
    @Override
    public String getMessage(){
        return "Sugar, Sugar, SO Sweet!!!";
    }

    public String getType() {
        return type;
    }
}
