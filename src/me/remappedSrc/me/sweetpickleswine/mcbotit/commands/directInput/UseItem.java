package me.sweetpickleswine.mcbotit.commands.directInput;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class UseItem extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        ActionResult a = MinecraftClient.getInstance().interactionManager.interactItem(MinecraftClient.getInstance().player, Hand.MAIN_HAND);

    }
}
