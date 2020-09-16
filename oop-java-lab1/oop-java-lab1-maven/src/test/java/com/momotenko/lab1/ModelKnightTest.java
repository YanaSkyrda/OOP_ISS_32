package com.momotenko.lab1;

import com.momotenko.lab1.model.AmmunitionModel;
import com.momotenko.lab1.model.entity.Ammunition;
import com.momotenko.lab1.model.entity.Knight;
import com.momotenko.lab1.model.entity.clothes.*;
import com.momotenko.lab1.model.entity.weapons.Sword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModelKnightTest {
    @Test
    @DisplayName("Knight test for get/add ammunition")
    void knightGetAddTest() {
        Knight knight = new Knight();
        Ammunition helm =  new Helmet("helm", 3.6, 2.1);
        Ammunition helm1 = new Helmet("helm1",5.6,1.1);
        Ammunition sword = new Sword("strong", 3.5,21.4);
        Ammunition pants = new Pants("square pants",0.25,14.5);

        assertTrue(knight.getAmmunition().equals(new ArrayList<>()));

        assertTrue(knight.addAmmunition(helm) == true);

        assertTrue(knight.addAmmunition(helm) == false);

        knight.replaceAmmunition(helm1);

        assertTrue(knight.getAmmunition().size() == 1);
        assertTrue(knight.getAmmunition().get(0).equals(helm1));

        assertTrue(knight.calculateCost() == 1.1);

        knight.addAmmunition(sword);
        knight.addAmmunition(pants);

        assertTrue(knight.calculateCost()
                == (sword.getPrice() + pants.getPrice() + helm1.getPrice()));

        assertTrue(knight.getAmmunition().size() == 3);
    }

    @Test
    @DisplayName("Knight test for main functions")
    void knightMainFuncTest(){
        Ammunition helmet = new Helmet("helm", 5.6,1.3);
        Ammunition chestplate = new Chestplate("chestplate",3.6,23.4);
        Ammunition gloves = new Gloves("gloves",1.5,26000.0);
        Ammunition pants = new Pants("pants",1.2,13.4);
        Ammunition boots = new Boots("boots",89.9,252.3);
        Ammunition sword = new Sword("sword",635.2,13200.3);

        double cost = helmet.getPrice()
                + chestplate.getPrice()
                + gloves.getPrice()
                + pants.getPrice()
                + boots.getPrice()
                + sword.getPrice();

        AmmunitionModel model = new AmmunitionModel();
        model.equipAmmunition(helmet);
        model.equipAmmunition(chestplate);

        assertTrue(model.getAmmunition().size() == 2);

        model.equipAmmunition(gloves);
        model.equipAmmunition(pants);
        model.equipAmmunition(boots);
        model.equipAmmunition(sword);

        assertTrue(model.calculateCost() == cost);

        ArrayList<Ammunition> ammunition = model.sortByWeight();
        assertTrue(ammunition.get(0).equals(pants));
        assertTrue(ammunition.get(ammunition.size()-1).equals(sword));

        ArrayList<Ammunition> ammunitionByPriceRange
                = model.getAmmunitionByPriceRange(10.0,1000.0);

        assertTrue(ammunitionByPriceRange.size() == 3);
        assertTrue(ammunitionByPriceRange.contains(chestplate));
        assertTrue(ammunitionByPriceRange.contains(pants));
        assertTrue(ammunitionByPriceRange.contains(boots));
    }
}
