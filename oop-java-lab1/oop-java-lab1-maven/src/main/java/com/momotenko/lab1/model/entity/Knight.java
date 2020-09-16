package com.momotenko.lab1.model.entity;

import com.momotenko.lab1.model.ComparatorWeight;

import java.util.ArrayList;
import java.util.Collections;

public class Knight {
    private ArrayList<Ammunition> ammunition = new ArrayList<>();

    public ArrayList<Ammunition> getAmmunition() {
        return ammunition;
    }

    public ArrayList<Ammunition> sortAmmunitionByWeight(){
        Collections.sort(ammunition, new ComparatorWeight());

        return ammunition;
    }

    public ArrayList<Ammunition> getAmmunitionByPriceRange(double from, double to){
        ArrayList<Ammunition> ans = new ArrayList<>();

        for (Ammunition i : ammunition){
            if (i.getPrice() <= to && i.getPrice() >= from){
                ans.add(i);
            }
        }

        return ans;
    }

    //return false if didn't add
    public boolean addAmmunition(Ammunition toAdd){
        if (!checkIfPresentAmmunition(toAdd.getType())){
            ammunition.add(toAdd);
            return true;
        }

        return false;
    }

    public void replaceAmmunition(Ammunition toReplace){
        String type = toReplace.getType();
        for (Ammunition i : ammunition){
            if (i.getType().equals(type)){
                i = toReplace;
                return;
            }
        }
    }

    private boolean checkIfPresentAmmunition(String toCheck){
        for (Ammunition i : ammunition){
            if (i.getType().equals(toCheck)){
                return true;
            }
        }

        return false;
    }

    public double calculateCost(){
        double ans = 0.0;

        for (Ammunition i : ammunition){
            ans += i.getPrice();
        }

        return ans;
    }
}
