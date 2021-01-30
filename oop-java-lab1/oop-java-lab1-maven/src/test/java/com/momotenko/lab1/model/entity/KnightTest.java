package com.momotenko.lab1.model.entity;

import com.momotenko.lab1.model.Knight;
import com.momotenko.lab1.model.entity.clothes.*;
import com.momotenko.lab1.model.entity.weapons.Sword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for model and knight
 */
public class KnightTest {
    /** Adding and getting back ammunition of the knight test */
    @Test
    @DisplayName("Knight test for get/add ammunition")
    void knightGetAddTest() {
        Knight knight = new Knight();
        Ammunition helm =  new Helmet("helm", 3.6, 2.1);
        Ammunition helm1 = new Helmet("helm1",5.6,1.1);
        Ammunition sword = new Sword("strong", 3.5,21.4);
        Ammunition pants = new Pants("square pants",0.25,14.5);

        assertEquals(new ArrayList<>(), knight.getAmmunition());

        assertTrue(knight.addAmmunition(helm));

        assertFalse(knight.addAmmunition(helm));

        knight.replaceAmmunition(helm1);

        assertEquals(knight.getAmmunition().size(), 1);
        assertEquals(helm1, knight.getAmmunition().get(0));

        assertEquals(knight.calculateCost(), 1.1);

        knight.addAmmunition(sword);
        knight.addAmmunition(pants);

        assertEquals((sword.getPrice() + pants.getPrice() + helm1.getPrice()), knight.calculateCost());

        assertEquals(knight.getAmmunition().size(), 3);
    }

    /** Testing function which are not getter nor setters.
     * Model class reflects interface of the Knight, so the Model class tested only.
     */
    @Test
    @DisplayName("Ammunition test for main functions")
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

        Knight model = new Knight();
        model.equipAmmunition(helmet,false);
        model.equipAmmunition(chestplate,false);

        assertEquals(model.getAmmunition().size(), 2);

        model.equipAmmunition(gloves,false);
        model.equipAmmunition(pants,false);
        model.equipAmmunition(boots,false);
        model.equipAmmunition(sword,false);

        assertEquals(cost, model.calculateCost());

        ArrayList<Ammunition> ammunition = model.sortAmmunitionByWeight();
        assertEquals(pants, ammunition.get(0));
        assertEquals(sword, ammunition.get(ammunition.size() - 1));

        ArrayList<Ammunition> ammunitionByPriceRange
                = model.getAmmunitionByPriceRange(10.0,1000.0);

        assertEquals(ammunitionByPriceRange.size(), 3);
        assertTrue(ammunitionByPriceRange.contains(chestplate));
        assertTrue(ammunitionByPriceRange.contains(pants));
        assertTrue(ammunitionByPriceRange.contains(boots));
    }
}
