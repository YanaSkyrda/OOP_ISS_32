package com.momotenko.lab1.model;

import com.momotenko.lab1.model.entity.Ammunition;
import com.momotenko.lab1.model.entity.Knight;
import com.momotenko.lab1.view.AmmunitionView;

import java.util.ArrayList;

public class AmmunitionModel {
    Knight knight = new Knight();
    AmmunitionView view = new AmmunitionView();

    public ArrayList<Ammunition> getAmmunition(){
        return knight.getAmmunition();
    }

    public ArrayList<Ammunition> sortByWeight(){
        return knight.sortAmmunitionByWeight();
    }

    public ArrayList<Ammunition> getAmmunitionByPriceRange(double from, double to){
        return knight.getAmmunitionByPriceRange(from, to);
    }

    public void equipAmmunition(Ammunition toEquip){
        if (!knight.addAmmunition(toEquip)){
            if (view.askIfSure("replace current ammunition with a new piece")){
                knight.replaceAmmunition(toEquip);
            }
        }
    }

    public double calculateCost(){
        return knight.calculateCost();
    }
}
