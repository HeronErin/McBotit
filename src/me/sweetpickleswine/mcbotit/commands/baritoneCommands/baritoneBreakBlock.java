package me.sweetpickleswine.mcbotit.commands.baritoneCommands;

import me.sweetpickleswine.mcbotit.BaritoneHandler;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.util.math.BlockPos;

public class baritoneBreakBlock extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        BlockPos bp = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));
        BaritoneHandler.baritoneBreakBlockHandler(bp);
    }
}
