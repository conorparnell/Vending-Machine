package com.techelevator.ui;

import com.techelevator.models.Item;

import java.awt.image.ShortLookupTable;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

/**
 * Responsibilities: This class should handle receiving ALL input from the User
 * <p>
 * Dependencies: None
 */
public class UserInput {
    private static Scanner scanner = new Scanner(System.in);
    private static BigDecimal userMoney = new BigDecimal("0.00");
    private static int purchaseNumber = 1;
    private static final BigDecimal DISCOUNT = new BigDecimal("1.00");
    private static BigDecimal placeHolderBill = new BigDecimal("0.00");

    public static String getHomeScreenOption() {
        System.out.println("What would you like to do?");
        System.out.println();

        System.out.println("D) Display Vending Machine Items");
        System.out.println("P) Purchase");
        System.out.println("E) Exit");

        System.out.println();
        System.out.print("Please select an option: ");

        String selectedOption = scanner.nextLine();
        String option = selectedOption.trim().toUpperCase();

        if (option.equals("D")) {
            return "display";
        } else if (option.equals("P")) {
            return "purchase";
        } else if (option.equals("E")) {
            return "exit";
        } else {
            return "";
        }

    }

    public static void purchase(Map<String, Item> inventory) {
        while (true) {
            System.out.println("What would you like to do?");
            System.out.println();

            System.out.println("M) Insert Money");
            System.out.println("S) Select item");
            System.out.println("F) Finish transaction");

            System.out.println();
            System.out.print("Please select an option: ");

            String selectedOption = scanner.nextLine();
            String option = selectedOption.trim().toUpperCase();

            if (option.equals("M")) {
                insert();
            } else if (option.equals("S")) {
                UserInput.select(inventory);
            } else if (option.equals("F")) {
                getChange();
                break;
            } else {
                System.out.println("Invalid entry, please try again!");
            }
        }
    }

    public static void insert() {
        String option = "";
        while (option != "E") {
            System.out.println("Insert bill here ($1, $5, $10, $20 only), or press E and exit to menu");
            placeHolderBill = userMoney;
            UserOutput.displayBills();
            option = scanner.nextLine();
            if (option.equals("1")) {
                userMoney = userMoney.add(new BigDecimal("1.00"));
                System.out.println("You have inserted a $1 bill. Your total is now $" + userMoney);
                auditFeedMoney(placeHolderBill, userMoney);
            } else if (option.equals("5")) {
                userMoney = userMoney.add(new BigDecimal("5.00"));
                System.out.println("You have inserted a $5 bill. Your total is now $" + userMoney);
                auditFeedMoney(placeHolderBill, userMoney);
            } else if (option.equals("10")) {
                userMoney = userMoney.add(new BigDecimal("10.00"));
                System.out.println("You have inserted a $10 bill. Your total is now $" + userMoney);
                auditFeedMoney(placeHolderBill, userMoney);
            } else if (option.equals("20")) {
                userMoney = userMoney.add(new BigDecimal("20.00"));
                System.out.println("You have inserted a $20 bill. Your total is now $" + userMoney);
                auditFeedMoney(placeHolderBill, userMoney);
            } else if (option.equalsIgnoreCase("E")) {
                break;
            } else {
                System.out.println("Invalid entry, please try again");
            }
        }
    }

    public static void select(Map<String, Item> inventory) {
        UserOutput.displaySlots(inventory);
        while (true) {
            System.out.println("Please enter the slot identifier of the item you would like, or press [E] to exit: ");
            String slotNumber = scanner.nextLine();
            if (inventory.containsKey(slotNumber)) {
                if (userMoney.compareTo(inventory.get(slotNumber).getPrice()) >= 0) {
                    inventory.get(slotNumber).vend();
                    if (inventory.get(slotNumber).isOutOfStock()) {
                        System.out.println("This item is out of stock, please make another selection");
                    } else {
                        if (purchaseNumber % 2 == 0) {
                            auditVendItem(inventory, slotNumber);
                            System.out.println("Vending: " + inventory.get(slotNumber).getName() + " for $" + (inventory.get(slotNumber).getPrice()).subtract(DISCOUNT) + " (BOGODO DISCOUNT - $1 Off!)");
                            userMoney = userMoney.subtract(inventory.get(slotNumber).getPrice().subtract(DISCOUNT));
                            System.out.println(inventory.get(slotNumber).getMessage());
                            System.out.println("You have $" + userMoney + " remaining");
                            purchaseNumber++;
                        } else {
                            auditVendItem(inventory, slotNumber);
                            System.out.println("Vending: " + inventory.get(slotNumber).getName() + " for $" + inventory.get(slotNumber).getPrice());
                            userMoney = userMoney.subtract(inventory.get(slotNumber).getPrice());
                            System.out.println(inventory.get(slotNumber).getMessage());
                            System.out.println("You have $" + userMoney + " remaining");
                            purchaseNumber++;
                        }
                    }
                } else {
                    System.out.println("You do not have enough funds for that selection. Please insert more money.");
                }
            } else if (slotNumber.equalsIgnoreCase("e")) {
                break;
            } else {
                System.out.println("No item found, please try again.");
            }
        }
    }

    public static void getChange() {
        auditChangeBack();
        BigDecimal userTotal = new BigDecimal(String.valueOf(userMoney));
        final BigDecimal DOLLAR = new BigDecimal("1.00");
        int dollarCount = 0;
        final BigDecimal QUARTER = new BigDecimal("0.25");
        int quarterCount = 0;
        final BigDecimal DIME = new BigDecimal("0.10");
        int dimeCount = 0;
        final BigDecimal NICKEL = new BigDecimal("0.05");
        int nickelCount = 0;

        while (userMoney.compareTo(DOLLAR) >= 0) {
            userMoney = userMoney.subtract(DOLLAR);
            dollarCount++;
        }
        while (userMoney.compareTo(QUARTER) >= 0) {
            userMoney = userMoney.subtract(QUARTER);
            quarterCount++;
        }
        while (userMoney.compareTo(DIME) >= 0) {
            userMoney = userMoney.subtract(DIME);
            dimeCount++;
        }
        while (userMoney.compareTo(NICKEL) >= 0) {
            userMoney = userMoney.subtract(NICKEL);
            nickelCount++;
        }
        purchaseNumber = 1; //reset the discount counter
        System.out.println();
        System.out.println("Thank you for your purchases!");
        System.out.println("Now vending $" + userTotal);
        System.out.println("Dispensing " + dollarCount + " dollars, " + quarterCount + " quarters, " + dimeCount + " dimes, and " + nickelCount + " nickels.");
        System.out.println("Have a great day!");
        System.out.println();


    }

    public static void auditFeedMoney(BigDecimal bill, BigDecimal total) {
        try (FileWriter audit = new FileWriter("audit.txt", true); BufferedWriter printer = new BufferedWriter(audit)) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            bill = total.subtract(bill);
            printer.write(dateFormat.format(now) + " MONEY FED:     $" + bill + " $" + total + "\n");
        } catch (Exception e) {
            System.out.println("No such file");
        }
    }
    public static void auditVendItem(Map<String, Item> inventory, String slotNumber) {
        try (FileWriter audit = new FileWriter("audit.txt", true);
             BufferedWriter printer = new BufferedWriter(audit)) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a");
            LocalDateTime now = LocalDateTime.now();


            printer.write(dateFormat.format(now) + " " + inventory.get(slotNumber).getName() + "         " + inventory.get(slotNumber).getSlot() + " $" + userMoney + " $" + userMoney.subtract(inventory.get(slotNumber).getPrice()) + "\n");
        } catch (Exception e) {
            System.out.println("Error - no file found");
        }
    }




    public static void auditChangeBack() {
        try (FileWriter audit = new FileWriter("audit.txt", true);
             BufferedWriter printer = new BufferedWriter(audit)) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a");
            LocalDateTime now = LocalDateTime.now();


            printer.write(dateFormat.format(now) + " CHANGE GIVEN:        $" + userMoney + " $" + userMoney.subtract(userMoney) + "\n");

        }catch (Exception e) {
            System.out.println("Error - no file found");
        }
    }
}
