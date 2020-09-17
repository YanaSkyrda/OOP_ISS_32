package com.momotenko.lab1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.momotenko.lab1.model.entity.Ammunition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.reflect.InvocationTargetException;

/**
 * Test class for ammunition
 */
public class AmmunitionTest {
    /** Parametrized test for checking the correctness for getters and setters */
    @DisplayName("Check getters via inherited classes")
    @ParameterizedTest(name = "Getters for {0} checked")
    @ArgumentsSource(AmmunitionArgumentProvider.class)
    void gettersCheckTest(Class<? extends Ammunition> ammunitionClass) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Ammunition ammunition = ammunitionClass.getConstructor(String.class, double.class, double.class)
                .newInstance("ammunition", 34.2, 43.2);

        assertTrue(ammunitionClass.getSimpleName().equals(ammunition.getType()));
        assertTrue(ammunition.setName("ammunition1").getName().equals("ammunition1"));
        assertTrue(ammunition.setPrice(45.2).getPrice() == 45.2);
        assertTrue(ammunition.setWeight(2.3).getWeight() == 2.3);
    }
}
