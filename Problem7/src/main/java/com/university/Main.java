package com.university;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Rocket rocket1 = new Rocket();
        Runnable barrierAction1 = rocket1::printDetails;
        MyCyclicBarrier myCyclicBarrier = new MyCyclicBarrier(5, barrierAction1);
        rocket1.setMyCyclicBarrier(myCyclicBarrier);
        rocket1.launchRocket();

        Thread.sleep(4000);
        System.out.println();

        Rocket rocket2 = new Rocket();
        Runnable barrierAction2 = rocket2::printDetails;
        myCyclicBarrier.setBarrierAction(barrierAction2);
        rocket2.setMyCyclicBarrier(myCyclicBarrier);
        rocket2.launchRocket();


    }
}
