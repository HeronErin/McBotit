package me.sweetpickleswine.mcbotit.commands;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;

public class disconnect extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        if (MinecraftClient.getInstance().world != null) {
            MinecraftClient.getInstance().world.disconnect();
        }
        MinecraftClient.getInstance().disconnect(new MessageScreen(Text.translatable("menu.savingLevel")));
        MinecraftClient.getInstance().setScreen(new TitleScreen());
    }
}
