package com.momotenko.lab1.controller;

import com.momotenko.lab1.model.Knight;
import com.momotenko.lab1.model.entity.clothes.*;
import com.momotenko.lab1.model.entity.weapons.*;
import com.momotenko.lab1.view.AmmunitionView;

import java.util.Scanner;

/**
 * Controller for MVC pattern.
 * This class requires Model and View to work with them.
 * All the communication between user and program controls here.
 *
 * @author Yurii Momotenko
 */
public class AmmunitionController {
    private Knight knight = new Knight();
    private AmmunitionView view = new AmmunitionView();

    /** Main command to run the program */
    public void run(){
        Scanner input = new Scanner(System.in);
        int command;
        while (true){
            view.printAllCommands();
            command = input.nextInt();
            doCommand(command);
            if (command == 6){
                return;
            }
        }
    }

    /** Helper function for the different commands */
    private void doCommand(int command){
        Scanner input = new Scanner(System.in);
        switch (command){
            case 1:
                //show current ammunition
                view.printAmmunitionList(knight.getAmmunition());
                break;
            case 2:
                //add ammunition
                view.printAddAmmunitionCommands();
                handleAddCommand(input.nextInt());
                break;
            case 3:
                //calculate cost
                view.printCost(knight.calculateCost());
                break;
            case 4:
                //sort according to the weight
                view.printSorted(knight.sortAmmunitionByWeight());
                break;
            case 5:
                //find ammunition elements by the provided price range
                view.printAskFor("left bound");
                double leftBound = input.nextDouble();
                view.printAskFor("right bound");
                view.printByPriceRange(
                        knight.getAmmunitionByPriceRange(
                                leftBound,
                                input.nextDouble()
                        )
                );
                break;
            default:
                return;
        }
    }

    /** Helper function for add new ammunition command */
    private void handleAddCommand(int command){
        Scanner input = new Scanner(System.in);
        view.printAskFor("name");
        String name = input.nextLine();
        view.printAskFor("weight");
        double weight = input.nextDouble();
        view.printAskFor("price");
        double price = input.nextDouble();

        switch (command){
            case 1:
                //helmet
                knight.equipAmmunition(new Helmet(name, weight, price),true);
                break;
            case 2:
                //chestplate
                knight.equipAmmunition(new Chestplate(name, weight, price),true);
                break;
            case 3:
                //gloves
                knight.equipAmmunition(new Gloves(name, weight, price),true);
                break;
            case 4:
                //pants
                knight.equipAmmunition(new Pants(name, weight, price),true);
                break;
            case 5:
                //boots
                knight.equipAmmunition(new Boots(name, weight, price),true);
                break;
            case 6:
                //sword
                knight.equipAmmunition(new Sword(name, weight, price),true);
                break;
            case 7:
                //spear
                knight.equipAmmunition(new Spear(name, weight, price),true);
                break;
            case 8:
                //bow
                knight.equipAmmunition(new Bow(name, weight, price),true);
                break;
            case 9:
                //arrow
                knight.equipAmmunition(new Arrow(name, weight, price),true);
                break;
            case 10:
                //shield
                knight.equipAmmunition(new Shield(name, weight, price),true);
                break;
            default:
                return;
        }
    }
}
