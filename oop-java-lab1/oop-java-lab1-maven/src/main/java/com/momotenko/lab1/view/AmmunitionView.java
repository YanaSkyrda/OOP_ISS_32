package com.momotenko.lab1.view;

import com.momotenko.lab1.model.entity.Ammunition;

import java.util.ArrayList;
import java.util.Scanner;

public class AmmunitionView {
    public void printAllCommands(){
        System.out.println("What would you like me to do?:\n" +
                "1. Show current ammunition\n" +
                "2. Add ammunition\n" +
                "3. Calculate cost\n" +
                "4. Sort according to the weight\n" +
                "5. Find ammunition elements by the provided price range\n" +
                "6. Exit");
    }

    public void printAddAmmunitionCommands(){
        System.out.println("Select the type of ammunition you would like to add:\n" +
                "Clothes:\n" +
                "\t1. Helmet\n" +
                "\t2. Chestplate\n" +
                "\t3. Gloves\n" +
                "\t4. Pants\n" +
                "\t5. Boots\n" +
                "Weapons:\n" +
                "\t6. Sword\n" +
                "\t7. Spear\n" +
                "\t8. Bow\n" +
                "\t9. Arrow\n" +
                "\t10. Shield\n");
    }

    public void printAmmunitionList(ArrayList<Ammunition> list){
        if (list.size() == 0){
            System.out.println("The list is empty");
            return;
        }

        for (Ammunition i : list) {
            System.out.println(i.toString() + '\n');
        }
    }

    public void printIfSure(String what){
        System.out.println("Are you sure you would like to " + what + "? 1/0");
    }

    public void printAskFor(String what){
        System.out.println("Enter " + what + ": ");
    }

    public void printCost(double cost){
        System.out.println("Ammunition all together costs: " + cost);
    }

    public void printSorted(ArrayList<Ammunition> list){
        System.out.println("Sorted list: ");
        printAmmunitionList(list);
    }

    public void printByPriceRange(ArrayList<Ammunition> list){
        System.out.printf("List by the given price range: ");
        printAmmunitionList(list);
    }

    public boolean askIfSure(String what){
        Scanner input = new Scanner(System.in);
        printIfSure(what);

        if (input.nextInt() == 0){
            return false;
        }

        return true;
    }
}
