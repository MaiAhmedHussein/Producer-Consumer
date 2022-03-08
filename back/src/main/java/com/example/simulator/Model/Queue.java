package com.example.simulator.Model;

import java.util.ArrayList;


public class Queue implements Observer {
    private int ID;
    private ArrayList<Product> products;
    private int numberOfProduct;
    private ArrayList<Machine> followingM;

    public void setFollowingM(ArrayList<Machine> followingM) {
        this.followingM = followingM;
    }

    public ArrayList<Machine> getFollowingM() {
        return followingM;
    }

    public Queue( int ID) {
        this.ID = ID;
        products = new ArrayList<>();
        followingM = new ArrayList<>();
    }

    public Queue( int ID,int numberOfProduct) {
        this.ID = ID;
        this.numberOfProduct=numberOfProduct;

    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    @Override
    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


    public int getNumberOfProduct(){
        return numberOfProduct;
    }

    @Override
    public void setNumberOfProduct(){
        this.numberOfProduct=products.size();
    }

    public void addMachines(Machine newM) {
        this.followingM.add(newM);
    }
    public ArrayList<Machine> getMachines() {
        return this.followingM;
    }

    public synchronized void addProduct(Product product) {
        if(product!= null) {
            products.add(product);
        }
    }

    @Override
    public synchronized void update(Object m) {
        if( m instanceof Machine) {
            Machine theM = (Machine)m;
            int indexOfM = followingM.indexOf(theM);

            if (indexOfM == -1) {
                followingM.add(theM);
            }
            if (theM.getState() == true && theM.getProduct() == null) {

                if (products.isEmpty()) {
                    return;
                }
                if (indexOfM == 0) {
                    theM.setProduct(products.remove(0), this);
                    followingM.remove(0);

                }
            }
        }
        else{
            addProduct((Product) m);
        }
    }

    @Override
    public Object getObserverInstance() {
        return this;
    }


}
