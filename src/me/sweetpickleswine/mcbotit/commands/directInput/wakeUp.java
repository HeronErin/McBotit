package me.sweetpickleswine.mcbotit.commands.directInput;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SleepingChatScreen;

public class wakeUp extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        if (MinecraftClient.getInstance().currentScreen instanceof SleepingChatScreen){
            (MinecraftClient.getInstance().currentScreen).close();
        }
    }
}
