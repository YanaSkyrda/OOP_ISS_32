package com.university;

import java.util.concurrent.BrokenBarrierException;

public class MyCyclicBarrier {

    private boolean broken;
    private boolean released;
    private int waitingParties;
    private final int parties;
    private Runnable barrierAction;

    public MyCyclicBarrier(int parties){
        this.parties = parties;
        this.waitingParties = 0;
        this.broken = false;
        this.barrierAction = null;
        this.released = false;
    }

    public MyCyclicBarrier(int parties, Runnable barrierAction){
        this.parties = parties;
        this.waitingParties = 0;
        this.broken = false;
        this.barrierAction = barrierAction;
        this.released = false;
    }

    public boolean isBroken() {
        return broken;
    }

    public int getWaitingParties() {
        return waitingParties;
    }

    public int getParties() {
        return parties;
    }

    public void setBarrierAction(Runnable barrierAction) {
        this.barrierAction = barrierAction;
    }

    /*synchronized public void await() throws InterruptedException, BrokenBarrierException {
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
                wait();//while  no if
            } catch (InterruptedException e) {
                this.broken = true;
                throw e;
            }
        }
    }*/

    synchronized public void await() throws InterruptedException, BrokenBarrierException {

        if(this.broken) {
            throw new BrokenBarrierException();
        }

        if(waitingParties + 1 == parties) {

            if(this.barrierAction != null){
                this.barrierAction.run();
            }

            broken = false;
            released = true;
            notifyAll();
        }

        waitingParties++;

        while(waitingParties != parties && !released){
            try {
                wait();
            } catch (InterruptedException e){
                this.broken = true;
                throw e;
            }
        }

        if(waitingParties - 1 == 0){
            this.released = false;
        }

        waitingParties--;
    }
}
