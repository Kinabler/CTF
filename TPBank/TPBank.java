package tpbank;

import java.util.Scanner;
/**
 *
 * @author phamt
 */
public class TPBank {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Loop until choose Exit option
        int option;
        do {
            //Step1: Display Menu
            menu();
            //Step2: Enter the option
            option = enterOption();
            //Step3: Perform option
            performOption(option);
        } while (option != 3);
    }

    private static void menu() {
        System.out.println("-------Login Program-------");
        System.out.println("1. Vietnamese");
        System.out.println("2. English");
        System.out.println("3. Exit");
    }

    private static int enterOption() {
        //Loop until get right option 1 , 2, 3
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Please choice one option: ");
                int option = Integer.parseInt(sc.nextLine());
                if (option >= 1 && option <= 3) {
                    return option;
                } else {
                    System.out.println("You must be enter right option [1/2/3].");
                }
            } catch (NumberFormatException e) {
                System.out.println("You must be enter numeric data!!!");
            }
        }
    }

    private static void performOption(int option) {
        Ebank ebank = new Ebank();
        switch (option) {
            case 1: {
                ebank.loginWithVietNamese();
                break;
            }
            case 2: {
                ebank.loginWithEnglish();
                break;
            }
            case 3:
                //don't nothing
                break;
        }
    }
}
