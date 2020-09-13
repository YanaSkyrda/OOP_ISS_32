package com.momotenko;

import com.momotenko.controller.AmmunitionController;

import java.io.IOException;

public class Main {
    public static void main(String[] argc) throws IOException {
        AmmunitionController controller = new AmmunitionController();
        controller.run();
    }
}
