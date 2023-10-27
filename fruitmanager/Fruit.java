/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruitmanager;

/**
 *
 * @author phamt
 */
public class Fruit {

    private int fruitID;
    private String fruitName;
    private double price;
    private int quantity;
    private String origin;

    public Fruit() {
    }

    public Fruit(int fruitID, String fruitName, double price, int quantity, String origin) {
        this.fruitID = fruitID;
        this.fruitName = fruitName;
        this.price = price;
        this.quantity = quantity;
        this.origin = origin;
    }

    public int getFruitID() {
        return fruitID;
    }

    public String getFruitName() {
        return fruitName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrigin() {
        return origin;
    }

    public void setFruitID(int fruitID) {
        this.fruitID = fruitID;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void list() {
        System.out.printf("|%6d|%20s|%7.2f$|%10d|%12s|%n", getFruitID(), getFruitName(), getPrice(), getQuantity(), getOrigin());
    }

    public void listForShopping() {
        System.out.printf("      %-10d%-19s%-18s%4.2f$\n", getFruitID(), getFruitName(), getOrigin(), getPrice());
    }
}
