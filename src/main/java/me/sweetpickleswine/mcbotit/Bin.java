package me.sweetpickleswine.mcbotit;

import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.InputOverideHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Bin {
    public static Bin instance = new Bin();
    public MainServer currentServer;

    public boolean hasBaritoneInstalled = false;
    public boolean lockScreen = false;

    public InputOverideHandler inputOverideHandler = new InputOverideHandler();
    public List<Thread> workers = new ArrayList<>();

    public List<Runnable> tickQueue = new ArrayList<>();

    public List<String> usedClientCommands = new ArrayList<>();

    public Map<String, String> commands = new HashMap<>();

    public void removeFinishedWorkers() {
        workers = workers.stream().filter(Thread::isAlive).collect(Collectors.toList());
    }

    public void registerAndStartThread(Thread t) {
        removeFinishedWorkers();
        t.start();
        workers.add(t);
    }
}
