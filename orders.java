package fruitmanager;

import java.util.ArrayList;

/**
 *
 * @author phamt
 */
public class orders {
    private String name;
    ArrayList<Fruit> orderItems = new ArrayList<>();

    public orders(String name, ArrayList<Fruit> orderItems) {
        this.name = name;
        this.orderItems = orderItems;
    }

    public orders() {
    }
   
    public static void listOrderItems(ArrayList<Fruit> orderItems) {
        double total = 0;
        System.out.println(" Product     | Quantity | Price |   Amount ");
        for (Fruit orderItem : orderItems) {
            display(orderItem);
            total += orderItem.getPrice() * orderItem.getQuantity();
        }
        System.out.println("Total: " + total + "$");
    }
    
    public static void display(Fruit orderItem) {
        System.out.printf("%-16s%-6d%10.2f%10.2f\n", orderItem.getFruitName(), orderItem.getQuantity(), orderItem.getPrice(), orderItem.getPrice() * orderItem.getQuantity());
    }

    public ArrayList<Fruit> getOrderItems() {
        return orderItems;
    }
    
}
