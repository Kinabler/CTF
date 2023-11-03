/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j1.s.p0070;

import static j1.s.p0070.EBank.loginWithEnglish;
import static j1.s.p0070.EBank.loginWithVietNamese;
import static j1.s.p0070.GetInput.displayExit;


/**
 *
 * @author dangl
 */
public class J1SP0070 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        OUTER:
        while (true) {
            int choose = Menu.mainMenu();
            //Perform opinition base use on the choice
            switch (choose) {
                case 1:
                    //Step 2: : Switch the interface language to Vietnamese and perform “check login” function.
                    loginWithVietNamese();
                    break;
                case 2:
                    //Step 3: Keep the English interface and perform “check login” function.
                    loginWithEnglish();
                    break;
                default:
                    //Step 4: Exit program
                    displayExit();
                    break OUTER;
            }
        }

    }

}
