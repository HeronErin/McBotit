package me.sweetpickleswine.mcbotit.commands;


import me.sweetpickleswine.mcbotit.BlockRotateUtil;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.Rotation;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;

public class getRotationOfBlock extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        Rotation r = BlockRotateUtil.getRotationForBlock(new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z")));
        JSONObject jout = new JSONObject();
        jout.put("pitch", r.getPitch());
        jout.put("yaw", r.getYaw());
        try {
            c.writeJson(jout);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
