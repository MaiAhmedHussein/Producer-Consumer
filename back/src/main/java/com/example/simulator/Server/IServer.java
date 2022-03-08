package com.example.simulator.Server;

public interface IServer {
    public void addQ(int ID);
    public void addM(int ID);
    public void addProduct(int num);
    public void stop();
    public boolean fromMToQ(String m, String q);
    public boolean fromQToM(String q, String m);
    public boolean Sim();
    public void replay();
}
