package com.university;

import java.util.Random;

public class Utils {
    public static int generateTimeWait(){
        int upperbound = 15;
        Random random = new Random();
        return (random.nextInt(upperbound) + 10) * 100;
    }
}
