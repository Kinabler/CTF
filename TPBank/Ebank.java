package tpbankk;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author phamt
 */
public class Ebank {

    void loginWithVietNamese() {
        Locale viLocale = new Locale("vi");
        setLocate(viLocale);
    }

    void loginWithEnglish() {
        Locale enLocale = new Locale("en");
        setLocate(enLocale);

    }

    void setLocate(Locale locale) {
        loginSystem(locale);
    }

    private void loginSystem(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("tpbankk.lang", locale);
        String accNum = enterAccNumber(bundle);
        String passWord = enterPassword(bundle, accNum);
        String captcha = generateCaptcha(bundle);
        checkCaptcha(captcha, bundle);
        System.out.println(bundle.getString("Success"));
    }

    private String enterAccNumber(ResourceBundle bundle) {
        Scanner sc = new Scanner(System.in);
        //Loop until enter a valid account number
        while (true) {
            System.out.print(bundle.getString("AccNum"));
            String accNum = sc.nextLine();
            //Check is Empty and length
            if (accNum.isEmpty() || accNum.length() < 10) {
                System.out.println(bundle.getString("ErrorAccNum"));
            } else {
                //Create Regex matches [0->9]
                Pattern pattern = Pattern.compile("^[0-9]+$");
                Matcher matcher = pattern.matcher(accNum);
                if (matcher.matches()) {
                    //Check account is exist
                    //If exist return accNum else display message;
                    if (checkAccountNumber(accNum) == true) {
                        return accNum;
                    } else {
                        System.out.println(bundle.getString("AccNotExist"));
                    }
                } else {
                    System.out.println(bundle.getString("ErrorAccNum"));
                }
            }
        }
    }

    private boolean checkAccountNumber(String accountNumber) {
        //Check account entered has exist in list account
        //If exist return true
        //Not exist return false
        ArrayList<Account> listAcc = listAccount();
        for (Account account : listAcc) {
            //Compare with account in list account
            if (accountNumber.equals(account.getAccountNumber())) {
                return true;
            }
        }
        return false;
    }

    private String enterPassword(ResourceBundle bundle, String accNum) {
        Scanner sc = new Scanner(System.in);
        //Loop until enter valid password 
        while (true) {
            System.out.print(bundle.getString("Pass"));
            String pass = sc.nextLine();
            //Check is Empty and length
            if (pass.isEmpty() || pass.length() < 8 && pass.length() > 31) {
                System.out.println(bundle.getString("ErrorPass"));
            } else {
                //Create Regex matches [0->9] and [a-z] and [A-Z]
                Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]+$");
                Matcher matches = pattern.matcher(pass);
                if (matches.matches()) {
                    //Check password is Correct
                    //If correct return Password else display message;
                    if (checkPassword(accNum, pass) == true) {
                        return pass;
                    } else {
                        System.out.println(bundle.getString("PassNotCorrect"));
                    }
                } else {
                    System.out.println(bundle.getString("ErrorPass"));
                }
            }
        }
    }

    private boolean checkPassword(String accNum, String pass) {
        //Match account entered with account in list
        ArrayList<Account> listAcc = listAccount();
        for (Account account : listAcc) {
            //Compare password with password in list account
            //If pass correct return true 
            //not correct return false
            if (accNum.equals(account.getAccountNumber())) {
                // Check password with account Number is valid
                return pass.equals(account.getPassword());
            }
        }
        return false;
    }

    public static ArrayList<Account> listAccount() {
        ArrayList<Account> listAccount = new ArrayList<>();
        listAccount.add(new Account("0123456789", "123456ab"));
        return listAccount;
    }

    private String generateCaptcha(ResourceBundle bundle) {
        String storeToGen = "qwertyuiopasdfghjklzxcvbnm1234567890".toUpperCase();
        Random random = new Random();
        String capTChar = "";
        //Traversal capTChar has length is 5
        for(int i=0; i < 5; i++){
            // Generate a random number between 65 and 90 (ASCII values for uppercase letters)
            int randomValue = random.nextInt(36);
            capTChar += storeToGen.charAt(randomValue);
        }
        System.out.println(bundle.getString("Captcha") + capTChar);
        return capTChar;
    }
    
    private void checkCaptcha(String captcha, ResourceBundle bundle) {
        Scanner sc = new Scanner(System.in);
        //Loop until enter any text
        while(true){
            System.out.print(bundle.getString("EnterACaptcha"));
            String aCaptcha = sc.nextLine();
            if(aCaptcha.isEmpty()){
                System.out.println(bundle.getString("WrongCaptcha"));
            }else{
                if(captcha.contains(aCaptcha)){
                    return;
                }else{
                    System.out.println(bundle.getString("WrongCaptcha"));
                }
            }
        }
    }
}
