package com.momotenko.lab1.controller;

import com.momotenko.lab1.model.AmmunitionModel;
import com.momotenko.lab1.model.entity.clothes.*;
import com.momotenko.lab1.model.entity.weapons.*;
import com.momotenko.lab1.view.AmmunitionView;

import java.util.Scanner;

public class AmmunitionController {
    AmmunitionModel model = new AmmunitionModel();
    AmmunitionView view = new AmmunitionView();

    public AmmunitionController(){
    }

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

    private void doCommand(int command){
        Scanner input = new Scanner(System.in);
        switch (command){
            case 1:
                //show current ammunition
                view.printAmmunitionList(model.getAmmunition());
                break;
            case 2:
                //add ammunition
                view.printAddAmmunitionCommands();
                handleAddCommand(input.nextInt());
                break;
            case 3:
                //calculate cost
                view.printCost(model.calculateCost());
                break;
            case 4:
                //sort according to the weight
                view.printSorted(model.sortByWeight());
                break;
            case 5:
                //find ammunition elements by the provided price range
                view.printAskFor("left bound");
                double leftBound = input.nextDouble();
                view.printAskFor("right bound");
                view.printByPriceRange(
                        model.getAmmunitionByPriceRange(
                                leftBound,
                                input.nextDouble()
                        )
                );
                break;
            default:
                return;
        }
    }

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
                model.equipAmmunition(new Helmet(name, weight, price));
                break;
            case 2:
                //chestplate
                model.equipAmmunition(new Chestplate(name, weight, price));
                break;
            case 3:
                //gloves
                model.equipAmmunition(new Gloves(name, weight, price));
                break;
            case 4:
                //pants
                model.equipAmmunition(new Pants(name, weight, price));
                break;
            case 5:
                //boots
                model.equipAmmunition(new Boots(name, weight, price));
                break;
            case 6:
                //sword
                model.equipAmmunition(new Sword(name, weight, price));
                break;
            case 7:
                //spear
                model.equipAmmunition(new Spear(name, weight, price));
                break;
            case 8:
                //bow
                model.equipAmmunition(new Bow(name, weight, price));
                break;
            case 9:
                //arrow
                model.equipAmmunition(new Arrow(name, weight, price));
                break;
            case 10:
                //shield
                model.equipAmmunition(new Shield(name, weight, price));
                break;
            default:
                return;
        }
    }
}
