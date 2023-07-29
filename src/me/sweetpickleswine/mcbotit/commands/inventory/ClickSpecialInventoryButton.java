package me.sweetpickleswine.mcbotit.commands.inventory;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;

public class ClickSpecialInventoryButton extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        MinecraftClient.getInstance().interactionManager.clickButton(
                MinecraftClient.getInstance().player.currentScreenHandler.syncId,
                job.getInt("buttonId")
        );
    }
}
