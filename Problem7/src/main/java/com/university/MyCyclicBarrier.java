package com.university;

import java.util.concurrent.BrokenBarrierException;

public class MyCyclicBarrier {

    private boolean broken;
    private int waitingParties;
    private final int parties;
    private final Runnable barrierAction;



    MyCyclicBarrier(int parties){
        this.parties = parties;
        this.waitingParties = 0;
        this.broken = false;
        this.barrierAction = null;
    }

    MyCyclicBarrier(int parties, Runnable barrierAction){
        this.parties = parties;
        this.waitingParties = 0;
        this.broken = false;
        this.barrierAction = barrierAction;
    }

    synchronized public void await() throws InterruptedException, BrokenBarrierException {
        if(this.broken)
            throw new BrokenBarrierException();

        waitingParties++;
        if(waitingParties == parties){
            if(this.barrierAction != null){
                this.barrierAction.run();
            }
            waitingParties = 0;
            broken = false;
            notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e){
                this.broken = true;
                throw e;
            }

        }
    }

    public void reset(){
        broken = true;
        waitingParties = 0;
        notifyAll();
        broken = false;
    }
}
