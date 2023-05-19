package me.sweetpickleswine.mcbotit.commands.directInput;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import net.minecraft.client.MinecraftClient;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

public class jump extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        if (MinecraftClient.getInstance().player.isOnGround())
            MinecraftClient.getInstance().player.jump();
    }
}
