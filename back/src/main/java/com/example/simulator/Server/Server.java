package com.example.simulator.Server;

import com.example.simulator.Model.Machine;
import com.example.simulator.Model.Product;
import com.example.simulator.Model.Queue;
import com.example.simulator.Snapshot.CareTaker;
import com.example.simulator.Snapshot.Memento;
import com.example.simulator.Snapshot.Originator;
import com.example.simulator.Update;
import com.example.simulator.controller.Controller;

import java.util.ArrayList;
import java.util.Random;

public class Server implements IServer {
    private final ArrayList<Queue> Qs;
    private final ArrayList<Machine> Ms;
    private static Server server ;
    private  int numberOfProducts;
    private final ArrayList<Queue> lastQs;
    private Update update;
    private CareTaker careTaker;
    private static ArrayList<String> colors = new ArrayList<String>();
    private static char[] colourInitiate= {'0','1' , '2' , '3' , '4','5','6','7','8','9','A','B','C','D','E','F'};


    private Server() {
        careTaker=CareTaker.getInstance();
        Ms = new ArrayList<>();
        Qs = new ArrayList<>();
        numberOfProducts = 0;
        lastQs = new ArrayList<>();
    }


    public static Server getServer() {
        if(server == null) {
            server = new Server();
        }
        return server;
    }

    @Override
    public void addQ(int ID) {
        Queue newQ = new Queue(ID);
        Qs.add(newQ);
        lastQs.add(newQ);
    }

    @Override
    public void addM(int ID) {
        Machine newM = new Machine(ID);
        Ms.add(newM);
    }

@Override
public boolean fromMToQ(String m, String q) {
    Machine M = Ms.get(Integer.parseInt(m));
    Queue Q = Qs.get(Integer.parseInt(q));
    M.setFollowingQ(Q);
    return true;
}
@Override
    public boolean fromQToM(String q, String m) {
        Machine M = Ms.get(Integer.parseInt(m));
        Queue Q = Qs.get(Integer.parseInt(q));
        lastQs.remove(Q);
        Q.addMachines(M);
        M.setPreQueues(Q);
        int i = 0;
        while (M.getPreQueues().size() != i){
            System.out.println(M.getPreQueues().get(i));
            i++;
        }
        System.out.println(Q.getMachines().size());
        System.out.println(M.getPreQueues().size());
        return true;
    }
//Simulation
@Override
public boolean Sim() {
    Queue Q;
    Machine M;
    for(int i=0; i<Qs.size()-1;i++) {
        Q=Qs.get(i);
        if(Q.getFollowingM()==null) {
            return false;
        }
    }
    for(Queue q :lastQs) {
        if(!q.getFollowingM().isEmpty()) {
            return false ;
        }
    }

    for(int i=0; i<Ms.size();i++) {
        M=Ms.get(i);
        if(M.getPreQueues().isEmpty()) {
            return false;
        }
        if(M.getFollowingQ()==null) {
            return false;
        }
    }
     CareTaker.getInstance().clear();
     Simulation();
    return true;
}


    public void Simulation() {

        for (Machine threadM : Ms) {
            threadM.beginThread();
        }

        new Thread(() -> {

            Qs.get(0).addProduct(new Product(RandomColour()));

            Originator origin = new Originator();
            origin.setState(null , Qs.get(0));
            careTaker.addMemento(origin.saveStateToMemento());

            for(int i = 0 ;i<numberOfProducts - 1;i++){
                try{
                    long time  = new Random().nextInt(3*1000);
                    Thread.sleep(time);

                }catch (InterruptedException e){
                    e.printStackTrace();
                    continue;
                }

                Qs.get(0).addProduct(new Product(RandomColour()));

                origin = new Originator();
                origin.setState(null , Qs.get(0));
                careTaker.addMemento(origin.saveStateToMemento());

            }


            int prev = 0;
            while(prev < numberOfProducts){
                int sum = 0;
                for(Queue i : lastQs)
                    sum += i.getProducts().size();
                if(sum != prev){
                    prev = sum;//check the sum of product
                }
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            server.stop();
        }).start();
    }

    @Override
    public void stop() {
        for(Machine i :Ms) {
            i.interrupt();
        }
       setUpdate(new Update(-2, -2 , -2 , null));
    }

    //////color
    public static String RandomColour(){;
        boolean flag=true;
        String colour="null";
        while(flag) {
            colour = "#";
            for(int i = 0 ;i<6 ; i++) {
                colour += colourInitiate[new Random().nextInt(colourInitiate.length)];
            }
            if (!colors.contains(colour) && !(colour.equals("#e6b2bc"))) {
                colors.add(colour);
                flag=false;

            }
        }

        return colour;
    }

    /////////////////start simulation////////////////////////


    public void setUpdate(Update update) {
        this.update = update;
        Controller.theUpdate.add(update);
    }

    @Override
    public void addProduct(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;//set number of products
    }

//////////////////////////////////////////////////////////////////////////////////////////////////
@Override
public void replay() {
    for(Queue q:Qs) {
        q.setProducts(null); //set number of products to each Q
    }
    new Thread(){
        @Override
        public void run() {
            ArrayList<Memento> mementoList=careTaker.getMementoList() ;//get memento list from care taker
            ArrayList<Long> timeOfmemento =careTaker.getMementoTime();//get time list from care taker
            for(int i=0 ;i<mementoList.size();i++) {
                Machine m= mementoList.get(i).getStateM();//get m

                Queue q= mementoList.get(i).getStateQ();//get Q

                Update update=new Update(q.getID(),m == null ?-1:m.getID(),q.getProducts().size(),m == null ?null:m.getColor());//set update to send to front end

                setUpdate(update);//set update function
                try {

                    Thread.sleep(timeOfmemento.get(i));//sleep for the time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            setUpdate(new Update(-2 , -2 , -2 , null));
        }}.start();
}
///////////////////////////////////////////
    public boolean newSimulation(){
    CareTaker.getInstance().clear();
    Qs.clear();
    Ms.clear();
    lastQs.clear();
    return true;
  }







}
