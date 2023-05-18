package me.sweetpickleswine.mcbotit;

import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static me.sweetpickleswine.mcbotit.ChatUtil.sendMessage;

public class Client {
    public Socket socket;
    public Thread thread;
    public BufferedReader reader;
    public OutputStream output;
    public Client(Socket _socket){
        socket = _socket;
        try {
            output = socket.getOutputStream();
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thread = new Thread(this::onThread);
        thread.start();

    }
    private void onThread(){
        try {
            while (socket.isConnected()){
                JSONObject job = readJson();
                if (job.getString("cmd").equalsIgnoreCase("keep alive")){
                    writeJson(new JSONObject("{'cmd': 'keep going'}"));
                }else{
                    BaseCommand cmd =  CommandRegistrar.instance.commandMap.get(job.getString("cmd"));
                    if (cmd != null){
                        cmd.onExec(this, job);
                    }else{
                        sendMessage("Mc bot it error, can't find internal command: "+ job.toString());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException ignored){

            try {socket.close();} catch (IOException ignoredd) {}
        }
//        socket.getOutputStream()
    }
    public JSONObject readJson() throws IOException {
        return new JSONObject(reader.readLine());


    }
    public void writeJson(JSONObject job) throws IOException {

        output.write(job.toString().getBytes());
    }

}
