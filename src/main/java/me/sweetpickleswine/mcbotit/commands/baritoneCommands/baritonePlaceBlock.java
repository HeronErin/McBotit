package me.sweetpickleswine.mcbotit.commands.baritoneCommands;

import baritone.api.BaritoneAPI;
import baritone.api.schematic.FillSchematic;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class baritonePlaceBlock extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        BlockPos bp = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));

        Block b = Registries.BLOCK.getOrEmpty(new Identifier(job.getString("id"))).orElse(null);
        if (b!=null) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getBuilderProcess().build("place single block", new FillSchematic(1, 1, 1, b.getDefaultState()), bp);
        }

    }



}
