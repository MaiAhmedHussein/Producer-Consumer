package com.example.simulator.Model;

import com.example.simulator.Snapshot.CareTaker;
import com.example.simulator.Snapshot.Originator;

import java.util.ArrayList;
import java.util.Random;

public class Machine implements Runnable,Subject {

    private boolean stopThread = false;
    private int ID;
    private Product product;
    private Observer followingQ;
    private  ArrayList<Observer> preQueues;
    private String color;
    private boolean state ;
    private int time ;


    private Queue sendingQ;

    private Thread thread;


    public Machine(int ID) {
        color = "#e6b2bc";
        this.ID=ID;
        preQueues = new ArrayList<>();

    }

    public Machine(int ID,String color) {
        this.ID=ID;
        this.color=color;
    }


    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product , Queue sendingQ) {
        if(product == null){
            this.color = "#e6b2bc";
            return;
        }

        this.sendingQ = sendingQ;
        this.product = product;
        this.color = product.getColor();
    }


    public boolean getState() {
        return state;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public void setFollowingQ(Queue Q){
        this.followingQ=Q;
    }

    public Observer getFollowingQ(){
        return this.followingQ;
    }

    public void setPreQueues(Queue Q){
        this.preQueues.add(Q);
    }

    public ArrayList<Observer> getPreQueues(){
        return this.preQueues;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setDefaultColor() {
        this.color = "#e6b2bc";
    }


  //Threaddddddddddddddddddddddd

public void beginThread() {
    time =  new Random().nextInt(5*1000);
    thread = new Thread( this, "M "+ID);
    thread.start();
}

    @Override
    public void run() {
        while(!stopThread) {
            setState(true);
            setState(!true);

            while(!stopThread) {
                try {
                    Thread.sleep(time);
                    CareTaker careTaker = CareTaker.getInstance(); //get care taker object
                    Thread.sleep(time);//sleep of processing

                    Originator originator = new Originator();//get originator class

                    originator.setState(this ,(Queue)followingQ.getObserverInstance() );//set state
                    careTaker.addMemento(originator.saveStateToMemento());//save the state
                    break;
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

    }


    public void interrupt() {
        stopThread = true;
        while(thread.isAlive()){}
        System.out.println("Thread "+ ID +"Successfully stopped");
    }

    @Override
    public void setState(boolean state)  {
        this.state = state;

        if (state == true) {
            this.color = "#e6b2bc";
            if(product != null){
                followingQ.update(product);
                followingQ.setNumberOfProduct();
                CareTaker careTaker = CareTaker.getInstance();
                this.setDefaultColor();
                Originator originator = new Originator();
                originator.setState(this ,(Queue)followingQ.getObserverInstance() );
                careTaker.addMemento(originator.saveStateToMemento());
            }

            product = null;
            while(product == null) {
                if(stopThread) {
                    return;
                }
                notifyMethod();
            }
        }
        else{
            Originator originator = new Originator();
            CareTaker careTaker = CareTaker.getInstance();
            originator.setState(this , sendingQ);
            careTaker.addMemento(originator.saveStateToMemento());
        }
    }

    @Override
    public void notifyMethod() {
        for(Observer observer : preQueues)
            observer.update(this);
    }



}
