package com.momotenko.task4;

import com.momotenko.task4.view.MyClassLoaderView;

public class Main {
    public static void main(String[] args) {
        MyClassLoaderView view = new MyClassLoaderView("entity.Ammunition");
        view.printInfo();
        view = new MyClassLoaderView("entity.ComparatorWeight");
        view.printInfo();
        view = new MyClassLoaderView("entity.Delivery");
        view.printInfo();
    }
}
