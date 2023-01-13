package com.techelevator.models;

import java.math.BigDecimal;

public abstract class Item {
    private final int MAX_STOCK = 6;
    private String slot;
    private String name;
    private BigDecimal price;
    private int stock = MAX_STOCK;
    private boolean outOfStock = false;

    public Item(String slot, String name, BigDecimal price) {
        this.slot = slot;
        this.name = name;
        this.price = price;

    }
    public abstract String getMessage();

    public abstract String getType();

    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void vend() {
        if (stock < 1) {
            outOfStock = true;

        }
       else {
           this.stock--;
        }
    }

    public boolean isOutOfStock() {
        return outOfStock;
    }
}
