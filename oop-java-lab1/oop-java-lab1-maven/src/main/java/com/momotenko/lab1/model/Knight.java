package com.momotenko.lab1.model;

import com.momotenko.lab1.model.ComparatorWeight;
import com.momotenko.lab1.model.entity.Ammunition;
import com.momotenko.lab1.view.AmmunitionView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is my subject area.
 * The knight has ammunition and it's possible to get some info on it.
 */
public class Knight {
    private ArrayList<Ammunition> ammunition = new ArrayList<>(); //List of unique pieces of ammunition
    private AmmunitionView view = new AmmunitionView();

    /** Getter for ammunition*/
    public ArrayList<Ammunition> getAmmunition() {
        return ammunition;
    }

    /** Sort ammunition by weight and get it */
    public ArrayList<Ammunition> sortAmmunitionByWeight(){
        Collections.sort(ammunition, new ComparatorWeight());

        return ammunition;
    }

    /** Get ammunition by the price range */
    public ArrayList<Ammunition> getAmmunitionByPriceRange(double from, double to){
        ArrayList<Ammunition> ans = new ArrayList<>();

        for (Ammunition i : ammunition){
            if (i.getPrice() <= to && i.getPrice() >= from){
                ans.add(i);
            }
        }

        return ans;
    }

    /** The function returns true if addition was successful and false vise versa */
    private boolean addAmmunition(Ammunition toAdd){
        if (!checkIfPresentAmmunition(toAdd.getType())){
            ammunition.add(toAdd);
            return true;
        }

        return false;
    }

    /** The function replaces ammunition if such piece already exists */
    private void replaceAmmunition(Ammunition toReplace){
        String type = toReplace.getType();

        for (int i = 0, size = ammunition.size(); i < size; ++i){
            if (ammunition.get(i).getType().equals(type)){
                ammunition.set(i, toReplace);
                return;
            }
        }
    }

    /** Gear up some piece of ammunition */
    public void equipAmmunition(Ammunition toEquip){
        if (addAmmunition(toEquip)){
            if (view.askIfSure("replace current ammunition with a new piece")){
                replaceAmmunition(toEquip);
            }
        }
    }

    /** Get total cost of all pieces of ammunition */
    public double calculateCost(){
        double ans = 0.0;

        for (Ammunition i : ammunition){
            ans += i.getPrice();
        }

        return ans;
    }

    /** Helper for checking if same type of ammunition already equipped */
    private boolean checkIfPresentAmmunition(String toCheck){
        for (Ammunition i : ammunition){
            if (i.getType().equals(toCheck)){
                return true;
            }
        }

        return false;
    }
}
