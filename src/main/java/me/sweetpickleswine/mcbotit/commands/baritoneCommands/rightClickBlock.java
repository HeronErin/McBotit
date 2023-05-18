package me.sweetpickleswine.mcbotit.commands.baritoneCommands;

import baritone.api.BaritoneAPI;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.input.Input;
import baritone.pathing.movement.MovementHelper;
import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.commands.realisticRotate;
import net.minecraft.util.math.BlockPos;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Optional;

public class rightClickBlock extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        Bin.instance.registerAndStartThread(new Thread(()-> {
            IPlayerContext ctx = BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext();
            BlockPos pos = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));
            Optional<Rotation> rot = RotationUtils.reachable(ctx, pos);
            if (rot.isEmpty()) {
                try {
                    c.writeJson(new JSONObject("{'err': 'to find rot'}"));
                } catch (IOException e) {
                }
            }
            if (!ctx.isLookingAt(pos)) {
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
            BaritoneAPI.getProvider().getPrimaryBaritone().getInputOverrideHandler().setInputForceState(Input.CLICK_RIGHT, true);
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            BaritoneAPI.getProvider().getPrimaryBaritone().getInputOverrideHandler().setInputForceState(Input.CLICK_RIGHT, false);
        }));
    }
}
