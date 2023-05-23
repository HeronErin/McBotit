package me.sweetpickleswine.mcbotit;

import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.io.*;
import java.net.Socket;

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
            e.printStackTrace();
        }
        thread = new Thread(this::onThread);
        thread.start();

    }
    private void onThread(){
        try {
            while (socket.isConnected()){
                JSONObject job = readJson();
                PlayerEntity p = MinecraftClient.getInstance().player;
                Vec3d vel = p.getVelocity();

                if (job.getString("cmd").equalsIgnoreCase("keep alive")){
                    if (Bin.instance.usedClientCommands.size()==0)
                        writeJson(
                                (new JSONObject("{'cmd': 'keep going'}"))
                                        .put("x", p.getX()).put("y", p.getY()).put("z", p.getZ())
                                        .put("pitch", p.getPitch()).put("yaw", p.getYaw())
                                        .put("velx", vel.x).put("vely", vel.y).put("velz", vel.z)
                                        .put("hunger", p.getHungerManager().getFoodLevel())
                        );
                    else{
                        writeJson(
                                (new JSONObject("{'cmd': 'alert'}"))
                                        .put("usedCommand",
                                                Bin.instance.usedClientCommands.remove(Bin.instance.usedClientCommands.size()-1)));
                    }
                }else{
                    BaseCommand cmd =  CommandRegistrar.instance.commandMap.get(job.getString("cmd"));
                    if (cmd != null){
                        cmd.onExec(this, job);
                    }else{
                        sendMessage("Mc bot it error, can't find internal command: "+ job);
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

        output.write((job.toString()+"\n").getBytes());
        output.flush();
    }

}
