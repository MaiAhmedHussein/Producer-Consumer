package com.example.simulator.Snapshot;

import com.example.simulator.Model.Machine;
import com.example.simulator.Model.Product;
import com.example.simulator.Model.Queue;
import com.example.simulator.Server.Server;
import com.example.simulator.Update;

import java.util.ArrayList;

public class Originator {
    private Machine stateM;//define M
    private Queue stateQ;//define Q



    public void setState(Machine state , Queue stateQ) {
        this.stateM =state !=null ? new Machine(state.getID(),state.getColor()) : null;//initialize M
        this.stateQ = new Queue(stateQ.getID(),stateQ.getNumberOfProduct());//initialize Q
        ArrayList<Product> y = stateQ.getProducts();
        ArrayList<Product> x = new ArrayList<>(y);
        this.stateQ.setProducts(x);//swt the product of M

    }


    public Memento saveStateToMemento()
    {
        ///////////////////////set update to send it to front end //////////
        Server.getServer().setUpdate(new Update(stateQ.getID() , stateM != null ?stateM.getID() : -1  , stateQ.getProducts().size() , stateM!=null?stateM.getColor():null));
        System.out.print("hhhhhhhhhhhhhhhhhhhhhhh");
        return new Memento(stateM , stateQ);
    }

    public void getStateFromMemento(Memento memento) {
        stateM = memento.getStateM();
    }
}
