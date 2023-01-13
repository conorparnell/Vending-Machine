package com.techelevator.models;

import java.math.BigDecimal;

public class Munchy extends Item{
    private String type = "Munchy";

    public Munchy(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }
    @Override
    public String getMessage(){
        return "Munchy, Munchy, SO Good!!!";
    }

    public String getType() {
        return type;
    }
}
