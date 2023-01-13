package com.techelevator.application;

import com.techelevator.models.*;
import com.techelevator.ui.UserInput;
import com.techelevator.ui.UserOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine 
{
    public Map<String, Item> restockInventory() {
        Map<String, Item> inventory = new HashMap<>();
        File inventoryFile = new File("catering.csv"); //TODO: Update to have customizable inventory file
        try {
            Scanner scanner = new Scanner(inventoryFile);

            while (scanner.hasNextLine()) {
                String currentItem = scanner.nextLine();
                String[] itemInfo = currentItem.split(",");
                String slot = itemInfo[0];
                String name = itemInfo[1];
                BigDecimal price = new BigDecimal(itemInfo[2]);
                String type = itemInfo[3];

                switch (type) {
                    case "Munchy":
                        Item munchy = new Munchy(slot, name, price);
                        inventory.put(slot, munchy);
                        break;
                    case "Candy":
                        Item candy = new Candy(slot, name, price);
                        inventory.put(slot, candy);
                        break;
                    case "Drink":
                        Item drink = new Drink(slot, name, price);
                        inventory.put(slot, drink);
                        break;
                    case "Gum":
                        Item gum = new Gum(slot, name, price);
                        inventory.put(slot, gum);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error - inventory file not valid.");
        } return inventory;
    }




    public void run()
    {
        Map<String, Item> inventory = restockInventory();
        UserOutput.displayHomeScreen();
        while(true)
        {
            String choice = UserInput.getHomeScreenOption();

            if(choice.equals("display"))
            {
                UserOutput.displaySlots(inventory);

            }
            else if(choice.equals("purchase"))
            {
                UserInput.purchase(inventory);
            }
            else if(choice.equals("exit"))
            {
                // good bye
                UserOutput.exit();
                break;
            }
        }
    }
    
}
