package me.sweetpickleswine.mcbotit.commands;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;


public class closeCurrentScreen extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        MinecraftClient.getInstance().execute(() -> {
            int idd = MinecraftClient.getInstance().player.currentScreenHandler.syncId;

            MinecraftClient.getInstance().player.closeScreen();
            MinecraftClient.getInstance().player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(idd));
            if (!MinecraftClient.getInstance().isWindowFocused())
                MinecraftClient.getInstance().
                        setScreen(new ChatScreen(""));
        });
    }
}
