package com.techelevator.models;

import java.math.BigDecimal;

public class Gum extends Item{
    private String type = "Gum";

    public Gum(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }
    @Override
    public String getMessage(){
     return "Chewy, Chewy, Lots O' Bubbles!!!";
    }

    public String getType() {
        return type;
    }
}
