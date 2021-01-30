package com.momotenko.task4.view;

import com.momotenko.task4.model.MyClassLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MyClassLoaderView {
    Class myClass;

    public MyClassLoaderView(String fileName) {
        MyClassLoader myClassLoader = new MyClassLoader();
        this.myClass = myClassLoader.findClass(fileName);
    }

    public void printInfo() {
        printGeneralInfo();
        printInterfaces();
        printClasses();
        printFields();
    }

    private void printGeneralInfo() {
        print("Class name: ", myClass.getName());

        Class temp = myClass.getSuperclass();
        if (temp == null) {
            print("Parent class: ", temp.getName());
        } else {
            print("Doesn't extend any class");
        }

        print("Class is annotation: ", getYesNo(myClass.isAnnotation()));
        print("Class is anonymous class: ", getYesNo(myClass.isAnonymousClass()));
        print("Class is interface: ", getYesNo(myClass.isInterface()));
        print("Class is enum: ", getYesNo(myClass.isEnum()));
    }

    private void printClasses(){
        Class[] classes = myClass.getClasses();

        if (classes.length == 0){
            print("Nested classes: ", getYesNo(false));
        }
        else{
            print("Nested classes: ");
            for (Class c : classes){
                print(c.getName());
            }
        }
    }


    private void printInterfaces(){
        Class[] classes = myClass.getInterfaces();

        if (classes.length == 0){
            print("Implements interfaces: ", getYesNo(false));
        }
        else{
            print("Implemented interfaces: ");
            for (Class c : classes){
                print(c.getName());
            }
        }
    }

    private void printFields(){
        Field[] fields = myClass.getDeclaredFields();

        if (fields.length == 0){
            print("Fields: ", getYesNo(false));
        }
        else{
            print("Fields: ");

            for (Field f : fields){
                print(f.getName(), ", access modifier: " + Modifier.toString(f.getModifiers()));
            }
        }
    }

    private void print(String string1) {
        System.out.println(string1);
    }

    private void print(String string1, String string2) {
        System.out.println(string1 + string2);
    }

    private String getYesNo(boolean bool) {
        if (bool) {
            return "Yes";
        }

        return "No";
    }
}
