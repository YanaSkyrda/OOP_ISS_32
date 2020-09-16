package com.momotenko.lab1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.momotenko.lab1.model.entity.Ammunition;
import com.momotenko.lab1.model.entity.clothes.Helmet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AmmunitionTest{
    @Test
    @DisplayName("Check getters via inherited classes")
    @ParameterizedTest(name = "Getters for {0} checked")
    @ValueSource(classes = {Helmet.class})
    void gettersCheckTest(Class<Ammunition> classes){
        Ammunition tester = new classes("stub", 9.3, 3.4);
        assertTrue(tester.getName().equals("stub"));
        assertTrue(tester.getWeight() == 9.3);
        assertTrue(tester.getPrice() == 3.4);
    }

}
