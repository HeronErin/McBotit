package me.sweetpickleswine.mcbotit.commands.baritoneCommands;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalGetToBlock;
import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.util.math.BlockPos;

public class baritoneOnlyWalk extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        Bin.instance.registerAndStartThread(new Thread(()->{
            boolean allowBreak = BaritoneAPI.getSettings().allowBreak.value;
            boolean allowPlace = BaritoneAPI.getSettings().allowPlace.value;
            boolean allowSprint = BaritoneAPI.getSettings().allowSprint.value;
            BaritoneAPI.getSettings().allowBreak.value = false;
            BaritoneAPI.getSettings().allowSprint.value = false;
            BaritoneAPI.getSettings().allowPlace.value = false;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(
                    new GoalGetToBlock(
                            new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"))));
            while (BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().isActive()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    BaritoneAPI.getSettings().allowBreak.value = allowBreak;
                    BaritoneAPI.getSettings().allowPlace.value = allowPlace;
                    BaritoneAPI.getSettings().allowSprint.value = allowSprint;
                    return;
                }
            }
            BaritoneAPI.getSettings().allowBreak.value = allowBreak;
            BaritoneAPI.getSettings().allowPlace.value = allowPlace;
            BaritoneAPI.getSettings().allowSprint.value = allowSprint;

    }));
    }
}
