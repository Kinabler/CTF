package fruitmanager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 *
 * @author phamt
 */
public class FruitManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Fruit> listFruit = new ArrayList<>();
        Hashtable<String, ArrayList<orders>> orders = new Hashtable<>();
        int option = 0;
        //Loop until Shop Owners choice Exit
        do {
            //Step 1: Display menu for Shop Owners
            displayMenu();
            //Step 2: Enter Shop Owners choice
            option = enterOption();
            //Step 3: Perform Shop owners option
            performOption(option, listFruit, orders);
        } while (option != 4);
    }

    private static void displayMenu() {
        System.out.println("FRUIT SHOP SYSTEM");
        System.out.println("  1. Create Fruit");
        System.out.println("  2. View orders");
        System.out.println("  3. Shopping (for buyer)");
        System.out.println("  4. Exit");
        System.out.print("Enter your option: ");
    }

    private static int enterOption() {
        Scanner sc = new Scanner(System.in);
        //Loop until get right option [1..4] numeric data
        while (true) {
            try {
                int option = Integer.parseInt(sc.nextLine());
                if (option >= 1 && option <= 4) {
                    return option;
                } else {
                    System.out.println("Please, enter right option [1/2/3/4]");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please, enter numeric data only");
            }
        }
    }

    private static void performOption(int option, ArrayList<Fruit> listFruit, Hashtable<String, ArrayList<orders>> orders) {
        switch (option) {
            case 1: { // If option = 1 then Create list Fruit and save
                createFruit(listFruit);
                break;
            }
            case 2: { // If option = 2 view order
                viewOrders(orders);
                break;
            }
            case 3: { // If Option = 3, perfrom Shopping for Buyer
                shoppingForBuyer(listFruit, orders);
                break;
            }
            case 4: {
                // Don't Anything
                break;
            }
        }
    }

    private static void shoppingForBuyer(ArrayList<Fruit> listFruit, Hashtable<String, ArrayList<orders>> orders) {
        ArrayList<Fruit> orderItems = new ArrayList<>();
        //If listfruit don't have any fruit --> Error msg
        // If list fruit have a list --> perform
        if(listFruit.isEmpty()){
            System.out.println("You don't have any list fruit");
            return;
        }
        boolean continuing;
        do {
            listforBuyer(listFruit);
            int item = enterItem(listFruit);
            Fruit fruitOrdered = getFruitfromItem(item, listFruit);
            System.out.println("You selected: " + fruitOrdered.getFruitName());
            int quantity = enterQuantity(fruitOrdered);
            updateQuantity(quantity, fruitOrdered);
            Fruit orderItem = new Fruit(fruitOrdered.getFruitID(), fruitOrdered.getFruitName(), fruitOrdered.getPrice(), quantity, fruitOrdered.getOrigin());
            orderItems.add(orderItem);
            continuing = enterContinue();
        } while (continuing == true);
        listOrderItems(orderItems);
        System.out.print("Enter your name: ");
        String name = enterName();
        //Save to Orders
        ArrayList<orders> CustomerOrders = new ArrayList<>();
        orders order = new orders(name, orderItems);
        CustomerOrders.add(order);
        if (orders.containsKey(name)) {
            orders.get(name).add(order);
        } else {
            orders.put(name, CustomerOrders);
        }
    }

    private static void viewOrders(Hashtable<String, ArrayList<orders>> orders) {
        ArrayList<orders> listOrder = new ArrayList<>();
        int size = orders.size();
        if (size == 0) {
            System.out.println("You don't have any orders");
        } else {
            //Duyệt từng key
            for (String key : orders.keySet()) {
                System.out.println("Customer: " + key);
                listOrder = orders.get(key);
                //assgin thằng array list --> orderItem
                // Mỗi key sẽ duyệt hết 1 ListOrder
                for (orders order : listOrder) {
                    double total = 0;
                    System.out.println(" Product     | Quantity | Price |   Amount ");
                    //Mỗi thằng order --> bao gồm các orderItem
                    for (Fruit orderItem : order.getOrderItems()) {
                        display(orderItem);
                        total += orderItem.getPrice() * orderItem.getQuantity();
                    }
                    System.out.println("Total: " + total + "$");
                }
            }
        }
    }

    private static String enterName() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String name = sc.nextLine();
            if (!name.isEmpty()) {
                return name;
                //Thêm cái check string ở đây nữa cũng được
            } else {
                System.out.println("You must be enter your name!! ");
            }
        }
    }

    private static void listOrderItems(ArrayList<Fruit> orderItems) {
        double total = 0;
        System.out.println(" Product     | Quantity | Price |   Amount ");
        for (Fruit orderItem : orderItems) {
            display(orderItem);
            total += orderItem.getPrice() * orderItem.getQuantity();
        }
        System.out.println("Total: " + total + "$");
    }

    private static void display(Fruit orderItem) {
        System.out.printf("%-16s%-6d%10.2f%10.2f\n", orderItem.getFruitName(), orderItem.getQuantity(), orderItem.getPrice(), orderItem.getPrice() * orderItem.getQuantity());
    }

    private static void listforBuyer(ArrayList<Fruit> listFruit) {
        System.out.println("List of Fruit");
        System.out.println("| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |");
        for (Fruit fruit : listFruit) {
            fruit.listForShopping();
        }
    }

    private static int enterItem(ArrayList<Fruit> listFruit) {
        Scanner sc = new Scanner(System.in);
        // Check xem thằng listFruit có tồn tại ít nhất 1 thằng không đã
        // Khi tồn tại ít nhất 1 thằng thì làm đoạn dưới.
        while (true) {
            System.out.print("Enter item: ");
            int item = sc.nextInt();
            Fruit lastFruit = listFruit.get(listFruit.size() - 1);
            if (item >= 0 && item <= lastFruit.getFruitID()) {
                return item;
            } else {
                System.out.println("Don't exist!!");
            }
        }
    }

    private static Fruit getFruitfromItem(int item, ArrayList<Fruit> listFruit) {
        for (Fruit fruit : listFruit) {
            if (item == fruit.getFruitID()) {
                return fruit;
            }
        }
        return null;
    }

    private static int enterQuantity(Fruit fruitOrder) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Please input quantity: ");
            int quantity = sc.nextInt();
            if (fruitOrder.getQuantity() <= quantity) {
                System.out.println("So du khong du!!");
            } else {
                return quantity;
            }
        }
    }

    private static void updateQuantity(int quantity, Fruit fruitOrder) {
        fruitOrder.setQuantity(fruitOrder.getQuantity() - quantity);
        System.out.println("You have " + fruitOrder.getQuantity() + " left!!");
    }

    private static void createFruit(ArrayList<Fruit> listFruit) {
        Scanner sc = new Scanner(System.in);
        boolean continuing;
        //Enter until Shop Owners don't choose contine
        do {
            int fruitID = enterFruitID();
            // If has DUP --> return true;
            // Not DUP --> return false;
            String fruitName = enterFruitName();
            double price = enterPrice();
            int quantity = enterQuantity();
            String origin = enterOrigin();
            Fruit newFruit = new Fruit(fruitID, fruitName, price, quantity, origin);
            listFruit.add(newFruit);
            continuing = enterContinue();
            // If shop owner want to stop, display all Fruits have been created
            if (continuing == false) {
                System.out.println("Fruit list, which you have been entered:");
                System.out.printf("|%6s|%20s|%8s|%10s|%12s|%n", "ID", "Fruit Name", "Price", "Quantity", "Origin");
                System.out.println("+------+--------------------+--------+----------+------------+");
                //List fruits existed in array list by method list
                for (Fruit fruit : listFruit) {
                    fruit.list();
                }
                System.out.println("Exiting....");
            }
        } while (continuing == true);
    }

    private static int enterFruitID() {
        Scanner sc = new Scanner(System.in);
        //Loop until enter right IntegerNumber format > 0
        while (true) {
            try {
                System.out.print("Enter Fruit ID: ");
                int number = Integer.parseInt(sc.nextLine());
                if (number >= 0) {
                    return number;
                } else {
                    System.out.println("Your number enter must be positive");
                }
            } catch (NumberFormatException e) {
                System.out.println("Pls, enter numberic data only");
            }
        }
    }

    private static int enterQuantity() {
        Scanner sc = new Scanner(System.in);
        //Loop until enter right IntegerNumber format > 0
        while (true) {
            try {
                System.out.print("Enter Quantity: ");
                int number = Integer.parseInt(sc.nextLine());
                if (number >= 0) {
                    return number;
                } else {
                    System.out.println("Your number enter must be positive");
                }
            } catch (NumberFormatException e) {
                System.out.println("Pls, enter numberic data only");
            }
        }
    }

    private static String enterFruitName() {
        Scanner sc = new Scanner(System.in);
        //Enter text format without EndLine
        while (true) {
            System.out.print("Enter Fruit Name: ");
            String text = sc.nextLine();
            if (text.isEmpty()) {
                System.out.println("You must be enter not empty!!");
            } else if (chechTextEntered(text) == false) {
                System.out.println("Your Fruit Name contains other character with [a->z][A->Z] & SPACE!!!");
            } else {
                return text;
            }
        }
    }

    private static String enterOrigin() {
        Scanner sc = new Scanner(System.in);
        //Enter text format without EndLine
        while (true) {
            System.out.print("Enter Origin: ");
            String text = sc.nextLine();
            if (text.isEmpty()) {
                System.out.println("You must be enter not empty!!");
            } else if (chechTextEntered(text) == false) {
                System.out.println("Your Origin contains other character with [a->z][A->Z] & SPACE!!!");
            } else {
                return text;
            }
        }
    }

    private static boolean chechTextEntered(String text) {
        for (char c : text.toCharArray()) {
            if (c < ' ' || c > ' ' && c < 'A' || c > 'Z' && c < 'a' || c > 'z') {
                return false;
            }
        }
        return true;
    }

    private static double enterPrice() {
        Scanner sc = new Scanner(System.in);
        //Loop until enter right double number format must be greater than 0
        while (true) {
            try {
                System.out.print("Enter Price: ");
                double number = Double.parseDouble(sc.nextLine());
                if (number > 0) {
                    return number;
                } else {
                    System.out.println("You must be enter a number greater than 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Pls, enter numberic data only");
            }
        }

    }

    private static boolean enterContinue() {
        Scanner sc = new Scanner(System.in);
        //Loop until enter right 2 character Y/N
        while (true) {
            System.out.print("Do you want to continue (Y/N)?");
            Character choice = sc.next().charAt(0);
            switch (choice) {
                case 'Y':
                case 'y': {
                    System.out.println("Continuing...");
                    return true;
                }
                case 'N':
                case 'n': {
                    return false;
                }
                default: {
                    System.out.println("Pls, You must be entered Y/N.");
                }
            }
        }
    }

}
