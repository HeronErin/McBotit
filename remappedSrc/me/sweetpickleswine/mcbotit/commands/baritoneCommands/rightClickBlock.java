package me.sweetpickleswine.mcbotit.commands.baritoneCommands;


import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.Input;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.Rotation;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.RotationUtils;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.commands.realisticRotate;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;
import java.util.Optional;

import static me.sweetpickleswine.mcbotit.codeTakenFromBaritone.RayTraceUtils.isLookingAt;

public class rightClickBlock extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        Bin.instance.registerAndStartThread(new Thread(() -> {
            ClientPlayerEntity ctx = MinecraftClient.getInstance().player;
            BlockPos pos = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));
            Optional<Rotation> rot = RotationUtils.reachable(ctx, pos, 4.5);
            if (rot.isEmpty()) {
                try {
                    c.writeJson(new JSONObject("{'err': 'to find rot'}"));
                } catch (IOException e) {
                }
            }
            if (!isLookingAt(pos)) {
                JSONObject job_for_fake_rot_packet = new JSONObject();
                job_for_fake_rot_packet.put("pitch", rot.get().getPitch());
                job_for_fake_rot_packet.put("yaw", rot.get().getYaw());
                job_for_fake_rot_packet.put("time_per_90", job.getFloat("time_per_90"));
                job_for_fake_rot_packet.put("x", pos.getX());
                job_for_fake_rot_packet.put("y", pos.getY());
                job_for_fake_rot_packet.put("z", pos.getZ());
                (new realisticRotate()).onExec(c, job_for_fake_rot_packet);

                try {
                    Bin.instance.workers.get(Bin.instance.workers.size() - 1).join();
                } catch (InterruptedException e) {
                }
            }
            Bin.instance.inputOverideHandler.setInputForceState(Input.CLICK_RIGHT, true);
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Bin.instance.inputOverideHandler.setInputForceState(Input.CLICK_RIGHT, false);
        }));
    }
}
