package me.sweetpickleswine.mcbotit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bin {
    public static Bin instance = new Bin();
    public MainServer currentServer;

    public boolean lockScreen = false;

    public List<Thread> workers = new ArrayList<>();

    public void removeFinishedWorkers(){
        workers = workers.stream().filter(Thread::isAlive).collect(Collectors.toList());
    }
    public void registerAndStartThread(Thread t){
        removeFinishedWorkers();
        t.start();
        workers.add(t);
    }
}
