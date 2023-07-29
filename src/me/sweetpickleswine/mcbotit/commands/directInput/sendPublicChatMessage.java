package me.sweetpickleswine.mcbotit.commands.directInput;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;

public class sendPublicChatMessage extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        String x = job.getString("msg");
        if (x.startsWith("/"))
            MinecraftClient.getInstance().getNetworkHandler().sendChatCommand(x.substring(1));
        else
            MinecraftClient.getInstance().getNetworkHandler().sendChatMessage(x);
    }
}
