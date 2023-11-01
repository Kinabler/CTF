package tpbank;

/**
 *
 * @author phamt
 */
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Random;
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
        switch (option) {
            case 1: {
                loginWithVietnamese();
                break;
            }
            case 2: {
                loginWithEnglish();
                break;
            }
            case 3:
                //don't nothing
                break;
        }
    }

    private static void loginWithVietnamese() {
        //Make a Locale for Vietnamese
        Locale viLocale = new Locale("vi", "VN");
        //Create a ResourceBundle for Vietnamese
        ResourceBundle viBundle = ResourceBundle.getBundle("Vi", viLocale);
        //Perform login
        String accNum = enterAccNumber(viBundle);
        String passWord = enterPassword(viBundle);
        String captcha = generateCaptcha(viBundle);
        checkCaptchaCode(captcha, viBundle);

    }
    
    private static void loginWithEnglish() {
        //Make a Locale for English
        Locale enLocale = new Locale("en", "US");
        //Create a ResourceBundle for English
        ResourceBundle enBundle = ResourceBundle.getBundle("En", enLocale);
        //Perform login
        String accNum = enterAccNumber(enBundle);
        String passWord = enterPassword(enBundle);
        String captcha = generateCaptcha(enBundle);
        checkCaptchaCode(captcha, enBundle);
    }

    private static String enterAccNumber(ResourceBundle Bundle) {
        Scanner sc = new Scanner(System.in);
        //Loop until enter a valid account number
        while (true) {
            System.out.print(Bundle.getString("AccNum"));
            String accNum = sc.nextLine();
            //Check is Empty and length
            if (accNum.isEmpty() || accNum.length() < 10) {
                System.out.println(Bundle.getString("ErrorAccNum"));
            } else {
                //Create Regex matches [0->9]
                Pattern pattern = Pattern.compile("^[0-9]+$");
                Matcher matcher = pattern.matcher(accNum);
                if (matcher.matches()) {
                    return accNum;
                } else {
                    System.out.println(Bundle.getString("ErrorAccNum"));
                }
            }
        }
    }

    private static String enterPassword(ResourceBundle Bundle) {
        Scanner sc = new Scanner(System.in);
        //Loop until enter valid password 
        while (true) {
            System.out.print(Bundle.getString("Pass"));
            String pass = sc.nextLine();
            //Check is Empty and length
            if (pass.isEmpty() || pass.length() < 8 && pass.length() > 31) {
                System.out.println(Bundle.getString("ErrorPass"));
            } else {
                //Create Regex matches [0->9] and [a-z] and [A-Z]
                Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]+$");
                Matcher matches = pattern.matcher(pass);
                if (matches.matches()) {
                    return pass;
                } else {
                    System.out.println(Bundle.getString("ErrorPass"));
                }
            }
        }
    }

    private static String generateCaptcha(ResourceBundle Bundle) {
        String storeToGen = "qwertyuiopasdfghjklzxcvbnm1234567890".toUpperCase();
        Random random = new Random();
        String capTChar = "";
        //Traversal capTChar has length is 5
        for(int i=0; i < 5; i++){
            // Generate a random number between 65 and 90 (ASCII values for uppercase letters)
            int randomValue = random.nextInt(36);
            capTChar += storeToGen.charAt(randomValue);
        }
        System.out.println(Bundle.getString("Captcha") + capTChar);
        return capTChar;
    }

    private static void checkCaptchaCode(String captcha, ResourceBundle Bundle) {
        Scanner sc = new Scanner(System.in);
        //Loop until enter any text
        while(true){
            System.out.print(Bundle.getString("EnterACaptcha"));
            String aCaptcha = sc.nextLine();
            if(aCaptcha.isEmpty()){
                System.out.println(Bundle.getString("WrongCaptcha"));
            }else{
                if(captcha.contains(aCaptcha)){
                    return;
                }else{
                    System.out.println(Bundle.getString("WrongCaptcha"));
                }
            }
        }
    }

}
