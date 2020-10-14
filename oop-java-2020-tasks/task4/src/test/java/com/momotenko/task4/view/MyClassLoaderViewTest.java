package com.momotenko.task4.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyClassLoaderViewTest {
    MyClassLoaderView view;
    ByteArrayOutputStream outputStream;

    private void resetOutput() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    //helper for output check
    private boolean checkOutputViaFilename(String filename, String output)  {
        resetOutput();

        view = new MyClassLoaderView(filename);
        view.printInfo();

        return outputStream.toString().equals(output);
    }

    @Test
    @DisplayName("Check output")
    void checkOutputForEntityPackage()  {
        assertTrue(checkOutputViaFilename("entity.Delivery",
                "Class name: com.momotenko.task4.model.entity.Delivery\n" +
                        "Doesn't extend any class\n" +
                        "Class is annotation: No\n" +
                        "Class is anonymous class: No\n" +
                        "Class is interface: No\n" +
                        "Class is enum: No\n" +
                        "Implemented interfaces: \n" +
                        "java.io.Serializable\n" +
                        "Nested classes: No\n" +
                        "Fields: \n" +
                        "serialVersionUID, access modifier: private static final\n" +
                        "city, access modifier: private\n" +
                        "sender, access modifier: private\n" +
                        "receiver, access modifier: private\n" +
                        "cost, access modifier: private\n")
        );

        assertTrue(checkOutputViaFilename("entity.ComparatorWeight",
                "Class name: com.momotenko.task4.model.entity.ComparatorWeight\n" +
                        "Doesn't extend any class\n" +
                        "Class is annotation: No\n" +
                        "Class is anonymous class: No\n" +
                        "Class is interface: No\n" +
                        "Class is enum: No\n" +
                        "Implemented interfaces: \n" +
                        "java.util.Comparator\n" +
                        "Nested classes: No\n" +
                        "Fields: No\n")
        );

        assertTrue(checkOutputViaFilename("entity.Ammunition",
                "Class name: com.momotenko.task4.model.entity.Ammunition\n" +
                        "Doesn't extend any class\n" +
                        "Class is annotation: No\n" +
                        "Class is anonymous class: No\n" +
                        "Class is interface: No\n" +
                        "Class is enum: No\n" +
                        "Implements interfaces: No\n" +
                        "Nested classes: No\n" +
                        "Fields: \n" +
                        "weight, access modifier: private\n" +
                        "price, access modifier: private\n" +
                        "name, access modifier: private\n")
        );


    }
}
