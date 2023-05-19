package me.sweetpickleswine.mcbotit.commands.baritoneCommands;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalGetToBlock;
import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.util.math.BlockPos;

public class baritoneGoto extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        Bin.instance.registerAndStartThread(new Thread(()->{
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(
                    new GoalGetToBlock(
                            new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"))));

            while (BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().isActive()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    return;
                }
            }

        }));
    }
}
