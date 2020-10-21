package com.momotenko.lab1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class AmmunitionControllerTest {
    @Test
    @DisplayName("Handling add command")
    void handleAddOneItemCommandTest() {
        AmmunitionController controller = Mockito.mock(AmmunitionController.class);
        Scanner input = Mockito.mock(Scanner.class);
        when(input.nextInt()).thenReturn(2)
                .thenReturn(1);
        when(input.nextLine()).thenReturn("Helmet");
        when(input.nextDouble()).thenReturn(32.9)
                .thenReturn(64.1);
        controller.run();
        assertTrue(controller)

    }
}
