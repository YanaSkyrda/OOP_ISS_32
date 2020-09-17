package com.momotenko.lab1.model;

import com.momotenko.lab1.model.entity.Ammunition;
import com.momotenko.lab1.model.entity.Knight;
import com.momotenko.lab1.view.AmmunitionView;

import java.util.ArrayList;

/**
 * Model for MVC pattern.
 * This class is the model which uses created classes.
 * It gears up the knight and do the things which knight class can do.
 *
 * @author Yurii Momotenko
 */
public class AmmunitionModel {
    Knight knight = new Knight();
    AmmunitionView view = new AmmunitionView();

    /** Getter for ammunition */
    public ArrayList<Ammunition> getAmmunition(){
        return knight.getAmmunition();
    }

    /** Sort ammunition by weight and return it */
    public ArrayList<Ammunition> sortByWeight(){
        return knight.sortAmmunitionByWeight();
    }

    /** Get ammunition by the price range */
    public ArrayList<Ammunition> getAmmunitionByPriceRange(double from, double to){
        return knight.getAmmunitionByPriceRange(from, to);
    }

    /** Gear up some piece of ammunition */
    public void equipAmmunition(Ammunition toEquip){
        if (!knight.addAmmunition(toEquip)){
            if (view.askIfSure("replace current ammunition with a new piece")){
                knight.replaceAmmunition(toEquip);
            }
        }
    }

    /**Calculate total cost of all ammunition */
    public double calculateCost(){
        return knight.calculateCost();
    }
}
