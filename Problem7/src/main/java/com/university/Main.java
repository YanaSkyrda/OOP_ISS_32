package com.university;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        Rocket rocket1 = new Rocket();
        Runnable barrierAction1 = rocket1::printDetails;
        MyCyclicBarrier myCyclicBarrier = new MyCyclicBarrier(5, barrierAction1);
        rocket1.setMyCyclicBarrier(myCyclicBarrier);
        rocket1.launchRocket();

        Rocket rocket2 = new Rocket();
        Runnable barrierAction2 = rocket2::printDetails;
        myCyclicBarrier = new MyCyclicBarrier(5, barrierAction2);
        rocket2.setMyCyclicBarrier(myCyclicBarrier);
        rocket2.launchRocket();

    }
}
