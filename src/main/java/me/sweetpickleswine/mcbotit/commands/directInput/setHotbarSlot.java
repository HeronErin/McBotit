package me.sweetpickleswine.mcbotit.commands.directInput;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;

public class setHotbarSlot extends BaseCommand   {
    @Override
    public void onExec(Client c, JSONObject job){
        int slot = job.getInt("slot");
        MinecraftClient.getInstance().player.getInventory().selectedSlot = slot;

    }
}
