package me.sweetpickleswine.mcbotit.commands.info;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

import java.io.IOException;

public class chunckDump extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        JSONObject ret = new JSONObject();
        int bx = job.getInt("cx")*16;
        int bz = job.getInt("cz")*16;


        int startY = job.getInt("startY");
        int endY = job.getInt("endY");

        for (int x = bx; x < bx+16; ++x){
            for (int y = startY; y < endY; ++y){
                for (int z = bz; z < bz+16; ++z){
                    ret.put(x+"|"+y+"|"+z, Registries.BLOCK.getId(MinecraftClient.getInstance().world.getBlockState(new BlockPos(x, y, z)).getBlock()).toString());

                }
            }
        }
        try {
            c.writeJson(ret);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
