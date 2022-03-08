package com.example.simulator.Model;

import java.util.ArrayList;

public interface Observer {
    public  void update(Object subject);
    public Object getObserverInstance();
    public void setNumberOfProduct();
    public ArrayList<Product> getProducts();
}
