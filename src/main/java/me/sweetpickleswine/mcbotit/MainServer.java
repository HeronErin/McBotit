package me.sweetpickleswine.mcbotit;
import java.net.*;
import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class MainServer {
    public Thread thread;
    private ServerSocket    server;
    public int port;
    private boolean running = true;

    public List<Client> clients = new ArrayList<>();
    public MainServer(int _port){
        port = _port;
        thread = new Thread(this::onThread);
        thread.start();
    }
    private void onThread(){
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(250);
            while (running) {
                try {
                    Socket socket = server.accept();
                    clients.add(new Client(socket));
                }catch (java.net.SocketTimeoutException ignored){}
            }

        }  catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        clients.forEach((client -> client.thread.interrupt()));
    }
    public void kill(){
        running=false;
    }

}
