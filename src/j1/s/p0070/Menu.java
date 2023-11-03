/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j1.s.p0070;

/**
 *
 * @author dangl
 */
public class Menu {
    public static int mainMenu(){
        int choice;
        System.out.println("-------Login Program-------");
        System.out.println("1. Vietnamese");
        System.out.println("2. English");
        System.out.println("3. Exit");
        choice = GetInput.checkLimit("Please choice one option: ", 1, 3);
        return choice;
    }
}
