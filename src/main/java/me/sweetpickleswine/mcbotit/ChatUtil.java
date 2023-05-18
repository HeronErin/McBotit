package me.sweetpickleswine.mcbotit;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtil {
    public static void sendMessage(String in){
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(in));

    }


}
