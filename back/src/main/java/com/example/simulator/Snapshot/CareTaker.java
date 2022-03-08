package com.example.simulator.Snapshot;

import java.util.ArrayList;

public class CareTaker {
    private long time;//define time
    private final ArrayList<Memento> mementoList ;//define memento list
    private final ArrayList<Long> timeOfmemento ;//define time list
    private static CareTaker careTaker;//care taker object
    private static long previousTime;//get previous time

    private CareTaker() {
        previousTime=0;
        mementoList = new ArrayList<Memento>();
        timeOfmemento =new ArrayList<Long>();
        time = System.currentTimeMillis();
    }
    public static CareTaker getInstance() {
        if (careTaker == null) careTaker = new CareTaker();//check if exist
        return careTaker;//return the object
    }
    //store the products and add to memento list
    public synchronized void addMemento(Memento state) {
        long currentTime=System.currentTimeMillis();
        if(previousTime==0)
            timeOfmemento.add(0L);
        else
            timeOfmemento.add(currentTime- previousTime);
        previousTime=currentTime;
        mementoList.add(state);
    }
    public void clear(){
        mementoList.clear();//clear memento list
        timeOfmemento.clear();//clear the time we save
        time = System.currentTimeMillis();
        previousTime = 0;
    }
    public ArrayList<Memento> getMementoList() {
        return mementoList;//get memento list
    }
    public ArrayList<Long> getMementoTime() {
        return timeOfmemento;//get time we save
    }

}
