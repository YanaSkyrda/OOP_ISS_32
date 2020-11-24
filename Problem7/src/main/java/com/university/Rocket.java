package com.university;

import java.util.ArrayList;

public class Rocket {

    private final ArrayList<Detail> details;
    private MyCyclicBarrier myCyclicBarrier;

    public Rocket(){
        this.myCyclicBarrier = null;
        details = new ArrayList<>();
    }

    public Rocket(MyCyclicBarrier myCyclicBarrier){
        this.myCyclicBarrier = myCyclicBarrier;
        details = new ArrayList<>();
    }

    public ArrayList<Detail> getDetails() {
        return details;
    }

    public void setMyCyclicBarrier(MyCyclicBarrier myCyclicBarrier) {
        this.myCyclicBarrier = myCyclicBarrier;
    }

    private void setUpDetails(){
        String[] names = {"Engine", "Wings", "Propeller", "Jet", "Turbine"};
        int detailsNum = 5;
        for(int i = 0; i < detailsNum; i++){
            Detail detail = new Detail(myCyclicBarrier, names[i]);
            details.add(detail);
        }
    }

    public synchronized void launchRocket() throws InterruptedException {
        setUpDetails();

        for(Detail detail : details){
            wait(Utils.generateTimeWait());     //random time
            Thread tempThread = new Thread(detail);
            tempThread.start();
        }
    }

    public void printDetails(){
        System.out.println();
        for(Detail detail : details){
            System.out.println(detail.toString());
        }
        System.out.println();
    }

}
