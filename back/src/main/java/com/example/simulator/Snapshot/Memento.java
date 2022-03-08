package com.example.simulator.Snapshot;

import com.example.simulator.Model.Machine;
import com.example.simulator.Model.Queue;

public class Memento {
    private Machine stateM;//define state M
    private Queue stateQ;//define state Q

    public Memento(Machine stateM, Queue stateQ) {
        this.stateM = stateM;//set M
        this.stateQ = stateQ;//set Q
    }
    public Machine getStateM() {
        return stateM;//get M

    }

    public Queue getStateQ() {
        return stateQ;//get Q

    }
}
