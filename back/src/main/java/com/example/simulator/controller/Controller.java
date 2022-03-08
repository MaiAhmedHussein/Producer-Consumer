package com.example.simulator.controller;
import com.example.simulator.Server.Server;
import com.example.simulator.Update;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class Controller {

    public static ArrayList<Update> theUpdate = new ArrayList<>() ;
    Server server = Server.getServer();

    @GetMapping("/sendQ")
    public int sendQ (@RequestParam String id ) {
        server.addQ(Integer.parseInt(id));
        return Integer.parseInt(id);
    }

    @GetMapping("/sendM")
    public void sendM (@RequestParam String id) {
        server.addM(Integer.parseInt(id));
    }

    @GetMapping("/sendConnection")
    public boolean sendConnection (@RequestParam String from,@RequestParam String to) {
        if(from.charAt(0) == 'M'){
            return server.fromMToQ(from.substring(1) , to.substring(1));

        }
        return server.fromQToM(from.substring(1) , to.substring(1));
    }

    @GetMapping("/start")
    public boolean start (  ) {
        boolean check =server.Sim();
        server = Server.getServer();
        return check;
    }

    @GetMapping("/addProduct")
    public void addProduct (@RequestParam int numberOfProducts) {
        server.addProduct(numberOfProducts);
    }

    @GetMapping("/update")
    public Update update () {

        while(theUpdate.isEmpty()){//Waiting for updated products
            System.out.println("yarab");
        }

        return theUpdate.remove(0);
    }

    @GetMapping("/stop")
    public void stop () {
        server.stop();
        server = Server.getServer();
    }
    @GetMapping("/replay")
    public Update replay () {
        //replay process
        server.replay();
        while(theUpdate.isEmpty()){
            System.out.println("test");
        }
        return theUpdate.remove(0);
    }
    @GetMapping("/newSim")
    public boolean newSim () {
        return server.newSimulation();
    }


}
