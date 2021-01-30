package com.momotenko.task4.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyClassLoaderTest {
    //helper function for tests
    private String testLoaderViaClassName(String className){
        MyClassLoader myClassLoader = new MyClassLoader();
        Class loadedClass = myClassLoader.findClass(className);
        return loadedClass.getSimpleName();
    }

    @Test
    @DisplayName("Test for classes in entity package")
    void testEntityPackage(){
        assertTrue(testLoaderViaClassName("entity.Delivery").equals("Delivery"));
        assertTrue(testLoaderViaClassName("entity.ComparatorWeight").equals("ComparatorWeight"));
        assertTrue(testLoaderViaClassName("entity.Ammunition").equals("Ammunition"));
    }
}
