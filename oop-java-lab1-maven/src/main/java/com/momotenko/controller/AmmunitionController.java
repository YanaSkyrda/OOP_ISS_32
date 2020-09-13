package com.momotenko.controller;

import com.momotenko.model.AmmunitionModel;
import com.momotenko.view.AmmunitionView;

import java.util.Scanner;

public class AmmunitionController {
    AmmunitionModel model = new AmmunitionModel();
    AmmunitionView view = new AmmunitionView();

    public void run(){
        Scanner input = new Scanner(System.in);
        int command;
        while (true){
            view.printAllCommands();
        }
    }
}
