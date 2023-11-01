package changebase;

/**
 *
 * @author phamt
 */
import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ChangeBasee {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Step1: Enter base number input
        int baseInput = enterBaseNumberInput();
        //Step2: Enter base number out
        int baseOutput = enterBaseNumberOutput();
        //Step3: Enter the input value
        String inputValue = enterValueInput(baseInput);
        //Step4: Process and display output value
        processAndDisplayValue(baseInput, baseOutput, inputValue);
    }

    private static int enterBaseNumberInput() {
        Scanner sc = new Scanner(System.in);
        //Loop until get right base
        while (true) {
            try {
                System.out.print("Enter base number input (2/10/16): ");
                int base = Integer.parseInt(sc.nextLine());
                if (base == 2 || base == 10 || base == 16) {
                    return base;
                } else {
                    System.out.println("Pls, enter true base system !!!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Pls, enter numberic data only !!!");
            }
        }
    }

    private static int enterBaseNumberOutput() {
        Scanner sc = new Scanner(System.in);
        //Loop until get right base
        while (true) {
            try {
                System.out.print("Enter base number output (2/10/16): ");
                int base = Integer.parseInt(sc.nextLine());
                if (base == 2 || base == 10 || base == 16) {
                    return base;
                } else {
                    System.out.println("Pls, enter true base system !!!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Pls, enter numberic data only !!!");
            }
        }
    }

    private static String enterValueInput(int baseInput) {
        Scanner sc = new Scanner(System.in);
        //Loop until enter right format number within base number input
        while (true) {
            String number;
            switch (baseInput) {
                case 2: //If base number = 2 --> enter Binary number only
                    number = enterBinaryNumber();
                    break;
                case 10: //If base number = 10 --> enter Dec number only
                    number = enterDecimalNumber();
                    break;
                default: //If base number = 16 --> enter Hex number only
                    number = enterHexNumber();
                    break;
            }
            return number.toLowerCase();
        }
    }

    private static String enterBinaryNumber() {
        Scanner sc = new Scanner(System.in);
        String binaryNum;
        //Loop until enter right Binary Format Number
        while (true) {
            System.out.print("Enter the input value: ");
            binaryNum = sc.nextLine();
            if (binaryNum.isEmpty()) {
                System.out.println("You must be enter Binary Format Number!!!");
            } else {
                if (checkBinaryNumber(binaryNum) == true) {
                    return binaryNum;
                } else {
                    System.out.println("Your number entered not right format");
                }
            }
        }
    }

    private static boolean checkBinaryNumber(String binaryNum) {
        //Use Regex to check String contains 0 and 1.
        Pattern pattern = Pattern.compile("^[01]+$");
        Matcher matcher = pattern.matcher(binaryNum);
        return matcher.matches();
    }

    private static String enterDecimalNumber() {
        Scanner sc = new Scanner(System.in);
        String decimalNum;
        //Loop until enter right Binary Format Number
        while (true) {
            System.out.print("Enter the input value: ");
            decimalNum = sc.nextLine();
            if (decimalNum.isEmpty()) {
                System.out.println("You must be enter Decimal Format Number!!!");
            } else {
                if (checkDecNumber(decimalNum) == true) {
                    return decimalNum;
                } else {
                    System.out.println("Your number entered not right format");
                }
            }
        }
    }

    private static boolean checkDecNumber(String decimalNum) {
        //Use Regex to check String contains 0 to 9
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(decimalNum);
        return matcher.matches();
    }

    private static String enterHexNumber() {
        Scanner sc = new Scanner(System.in);
        String hexNum;
        //Loop until enter right Binary Format Number
        while (true) {
            System.out.print("Enter the input value: ");
            hexNum = sc.nextLine();
            if (hexNum.isEmpty()) {
                System.out.println("You must be enter Hexadecimal Format Number!!!");
            } else {
                if (checkHexNumber(hexNum) == true) {
                    return hexNum;
                } else {
                    System.out.println("Your number entered not right format");
                }
            }
        }
    }

    private static boolean checkHexNumber(String hexNum) {
        Pattern pattern = Pattern.compile("^[0-9a-fA-F]+$");
        Matcher matcher = pattern.matcher(hexNum);
        return matcher.matches();
    }

    private static void processAndDisplayValue(int baseInput, int baseOutput, String inputValue) {
        System.out.println("");
        System.out.println("Base INPUT: " + baseInput);
        System.out.println("Base OUTPUT: " + baseOutput);
        if (baseInput == 16) {
            System.out.println("Input value: 0x" + inputValue.toUpperCase());
        } else {
            System.out.println("Input value: " + inputValue.toUpperCase());
        }
        String outputValue = null;
        if (baseInput == baseOutput) {
            outputValue = inputValue;
        } else if (baseInput == 2) {
            switch (baseOutput) {
                case 10: {
                    outputValue = binaryToDecimal(inputValue);
                    break;
                }
                case 16: {
                    outputValue = binaryToHex(inputValue);
                    break;
                }
            }
        } else if (baseInput == 10) {
            switch (baseOutput) {
                case 2: {
                    outputValue = decimalToBinary(inputValue);
                    break;
                }
                case 16: {
                    outputValue = decimalToHex(inputValue);
                    break;
                }
            }
        } else if (baseInput == 16) {
            switch (baseOutput) {
                case 2: {
                    outputValue = hexToBinary(inputValue);
                    break;
                }
                case 10: {
                    outputValue = hexToDecimal(inputValue);
                    break;
                }
            }
        }
        if (baseOutput == 16) {
            System.out.println("Output value: 0x" + outputValue.toUpperCase());
        } else {
            System.out.println("Output value: " + outputValue.toUpperCase());
        }
    }

    private static String binaryToDecimal(String inputValue) {
        BigInteger value = BigInteger.ZERO; //To save value after convert
        //Take element multi with reverse index
        int startIdx = inputValue.length() - 1; //Start idx is length --> 0
        //Traversal inputValue string to multi each element
        for (int i = 0; i < inputValue.length(); i++) {
            //If matches '1' --> calc and add to value
            if (inputValue.charAt(i) == '1') {
                value = value.add((BigInteger.valueOf(2).pow(startIdx)));
            }
            startIdx--; //Decrease index
        }
        System.out.println("Value = " + value);
        return value.toString();
    }

    private static String decimalToHex(String inputValue) {
        String valueHex = "";
        BigInteger inputDec = new BigInteger(inputValue);
        // Div until divisor = 0;
        // Take remainder into String from last --> 0
        while (inputDec.compareTo(new BigInteger("0")) != 0) {
            BigInteger[] divAndRemain = inputDec.divideAndRemainder(new BigInteger("16"));
            inputDec = divAndRemain[0];
            BigInteger remainder = divAndRemain[1];
            valueHex = insertRemainderIntoHexValue(remainder, valueHex);
        }
        return valueHex;
    }

    private static String insertRemainderIntoHexValue(BigInteger remainder, String value) {
        // If remainder between [0, 9] --> insert directly 
        if (remainder.compareTo(BigInteger.ZERO) >= 0 && remainder.compareTo(new BigInteger("9")) <= 0) {
            value = remainder.toString() + value;
            return value;
        }
        //If remainder between [10, 15] --> convert to right format and insert
        if (remainder.compareTo(new BigInteger("10")) >= 0 && remainder.compareTo(new BigInteger("15")) <= 0) {
            char hexChar = (char) ('a' + remainder.intValue() - 10);
            value = hexChar + value;
        }
        return value;
    }

    private static String binaryToHex(String inputValue) {
        String decimalNum = binaryToDecimal(inputValue);
        return decimalToHex(decimalNum);
    }

    private static String decimalToBinary(String inputValue) {
        String valueBinary = "";
        BigInteger inputDec = new BigInteger(inputValue);
        // Div until divisor = 0;
        // Take remainder into String from last --> 0
        while (inputDec.compareTo(new BigInteger("0")) != 0) {
            BigInteger[] divAndRemain = inputDec.divideAndRemainder(new BigInteger("2"));
            inputDec = divAndRemain[0];
            BigInteger remainder = divAndRemain[1];
            valueBinary = remainder + valueBinary;
        }
        return valueBinary;
    }

    private static String hexToDecimal(String inputValue) {
        BigInteger value = BigInteger.ZERO; //To save value after convert
        //Take element multi with reverse index
        int startIdx = inputValue.length() - 1; //Start idx is length --> 0
        //Traversal inputValue string to multi each element with 16
        for (int i = 0; i < inputValue.length(); i++) {
            int element = inputValue.charAt(i) - '0';
            // process [0, 9] first and [a,f] last
            // if [0, 9] --> calc directly
            // else convert element to numeric data [10, 15] to calc
            BigInteger powerOfIdx = BigInteger.valueOf(16).pow(startIdx);
            if (element >= 0 && element <= 9) {
                value = value.add(BigInteger.valueOf(element).multiply(powerOfIdx));
            } else {
                value = value.add(BigInteger.valueOf((10 + (inputValue.charAt(i) - 'a'))).multiply(powerOfIdx));
            }
            startIdx--; //Decrease index
        }
        return value.toString();
    }

    private static String hexToBinary(String inputValue) {
        String numDec = hexToDecimal(inputValue);
        return decimalToBinary(numDec);
    }
}
