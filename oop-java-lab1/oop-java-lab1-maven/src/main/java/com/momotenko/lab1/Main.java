package com.momotenko.lab1;

import com.momotenko.lab1.controller.AmmunitionController;

import java.io.IOException;

public class Main {
    public static void main(String[] argc) throws IOException {
        AmmunitionController controller = new AmmunitionController();
        controller.run();
    }
}
