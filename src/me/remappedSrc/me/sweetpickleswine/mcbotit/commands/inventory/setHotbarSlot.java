package me.sweetpickleswine.mcbotit.commands.inventory;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;

public class setHotbarSlot extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        int slot = job.getInt("slot");
        MinecraftClient.getInstance().player.getInventory().selectedSlot = slot;

    }
}
