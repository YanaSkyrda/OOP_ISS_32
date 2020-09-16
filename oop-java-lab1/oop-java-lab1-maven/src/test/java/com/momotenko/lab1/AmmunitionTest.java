package com.momotenko.lab1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.momotenko.lab1.model.entity.Ammunition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.reflect.InvocationTargetException;

public class AmmunitionTest {
    @DisplayName("Check getters via inherited classes")
    @ParameterizedTest(name = "Getters for {0} checked")
    @ArgumentsSource(AmmunitionArgumentProvider.class)
    void gettersCheckTest(Class<? extends Ammunition> ammunitionClass) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Ammunition ammunition = ammunitionClass.getConstructor(String.class, double.class, double.class)
                .newInstance("4234", 34.2, 43.2);
        
        assertTrue(ammunitionClass.getSimpleName().equals(ammunition.getType()));
    }


}
