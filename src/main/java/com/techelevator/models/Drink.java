package com.techelevator.models;

import java.math.BigDecimal;

public class Drink extends Item{
    private String type = "Drink";

    public Drink(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }
    @Override
    public String getMessage(){
        return "Drinky, Drinky, Slurp Slurp!!!";
    }

    public String getType() {
        return type;
    }
}
