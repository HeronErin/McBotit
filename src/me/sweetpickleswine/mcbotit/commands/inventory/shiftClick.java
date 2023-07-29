package me.sweetpickleswine.mcbotit.commands.inventory;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public class shiftClick extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        int id = MinecraftClient.getInstance().player.currentScreenHandler.syncId;
        MinecraftClient.getInstance().interactionManager.clickSlot(id, (job.getInt("slot")), 0, SlotActionType.QUICK_MOVE, MinecraftClient.getInstance().player);


    }

}
