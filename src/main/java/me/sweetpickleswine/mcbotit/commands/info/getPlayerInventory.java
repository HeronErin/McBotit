package me.sweetpickleswine.mcbotit.commands.info;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;

import java.io.DataOutputStream;
import java.io.IOException;

public class getPlayerInventory extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        NbtList nbt = MinecraftClient.getInstance().player.getInventory().writeNbt(new NbtList());
        DataOutputStream dout = new DataOutputStream(c.output);
        try {
            NbtIo.write(nbt, dout);
            GetVillagerTrades.endNbt(dout);
            dout.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
