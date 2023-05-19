package me.sweetpickleswine.mcbotit.commands.baritoneCommands;

import baritone.api.BaritoneAPI;
import baritone.api.schematic.FillSchematic;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class baritoneBreakBlock extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        BlockPos bp = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));
        BaritoneAPI.getProvider().getPrimaryBaritone().getBuilderProcess().build("break single block", new FillSchematic(1, 1, 1, Blocks.AIR.getDefaultState()), bp);
    }
}
