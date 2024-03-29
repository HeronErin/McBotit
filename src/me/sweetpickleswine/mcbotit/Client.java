package me.sweetpickleswine.mcbotit;

import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import me.sweetpickleswine.mcbotit.mixin.DisconnectScreenAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.math.Vec3d;

import java.io.*;
import java.net.Socket;

import static me.sweetpickleswine.mcbotit.ChatUtil.sendMessage;

public class Client {
    public Socket socket;
    public Thread thread;
    public BufferedReader reader;
    public OutputStream output;

    public Client(Socket _socket) {
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

    private void onThread() {
        try {
            while (socket.isConnected()) {
                JSONObject job = readJson();
                PlayerEntity p = MinecraftClient.getInstance().player;


                if (job.getString("cmd").equalsIgnoreCase("get connection state")) {
                    JSONObject out = (new JSONObject("{'cmd': 'connection state'}")).put("Connected", p != null);
                    if (MinecraftClient.getInstance().currentScreen instanceof DisconnectedScreen ds) {
                        String reason = ((DisconnectScreenAccessor) ds).getReason().asTruncatedString(999999);
                        out.put("reason", reason);
                        out.put("title", ds.getTitle().asTruncatedString(999999));
                    }
                    writeJson(out);
                    continue;
                }

                if (p == null) {
                    socket.close();
                    return;
                }


                Vec3d vel = p.getVelocity();


                if (job.getString("cmd").equalsIgnoreCase("keep alive")) {
                    if (Bin.instance.usedClientCommands.size() == 0)
                        writeJson(
                                (new JSONObject("{'cmd': 'keep going'}"))
                                        .put("x", p.getX()).put("y", p.getY()).put("z", p.getZ())
                                        .put("pitch", p.getPitch()).put("yaw", p.getYaw())
                                        .put("velx", vel.x).put("vely", vel.y).put("velz", vel.z)
                                        .put("hunger", p.getHungerManager().getFoodLevel())
                                        .put("health", p.getHealth())
                        );
                    else {
                        writeJson(
                                (new JSONObject("{'cmd': 'alert'}"))
                                        .put("usedCommand",
                                                Bin.instance.usedClientCommands.remove(Bin.instance.usedClientCommands.size() - 1)));
                    }
                } else {
                    BaseCommand cmd = CommandRegistrar.instance.commandMap.get(job.getString("cmd"));
                    if (cmd != null) {
                        cmd.onExec(this, job);
                    } else {
                        sendMessage("Mc bot it error, can't find internal command: " + job);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException ignored) {

            try {
                socket.close();
            } catch (IOException ignoredd) {
            }
        }
//        socket.getOutputStream()
    }

    public JSONObject readJson() throws IOException {
        return new JSONObject(reader.readLine());


    }

    public void writeJson(JSONObject job) throws IOException {
        output.write((job.toString() + "\n").getBytes());
        output.flush();
    }

    public void writeNbt(NbtElement nbt) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(baos);
        NbtIo.write(nbt, dout);


        dout.writeUTF("This is the ending text of the nbt stream1234567899876543210");
        dout.writeByte(0);
        dout.writeByte(0);
        dout.writeByte(0);
        dout.writeByte(0);

        dout.flush();
        baos.flush();

        output.write(baos.toByteArray());
        output.flush();
//        System.out.println(baos.toByteArray().length);
    }

}
