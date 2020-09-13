package com.momotenko.controller;

import com.momotenko.model.AmmunitionModel;
import com.momotenko.model.entity.Ammunition;
import com.momotenko.view.AmmunitionView;

import java.util.ArrayList;
import java.util.Scanner;

public class AmmunitionController {
    AmmunitionModel model = new AmmunitionModel();
    AmmunitionView view = new AmmunitionView();

    public void run(){
        Scanner input = new Scanner(System.in);
        int command;
        while (true){
            view.printAllCommands();
            command = input.nextInt();
            doCommand(command);
        }
    }

    private void doCommand(int command){
        switch (command){
            case 1:
                //show current ammunition
            case 2:
                //change ammunition
            case 3:
                //calculate cost
            case 4:
                //sort according to the weight
            case 5:
                //find ammunition elements by the provided price range
            case 6:
                //exit
                return;
        }
    }
}
