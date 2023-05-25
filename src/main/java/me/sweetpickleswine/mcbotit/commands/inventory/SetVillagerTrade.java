package me.sweetpickleswine.mcbotit.commands.inventory;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.screen.MerchantScreenHandler;

public class SetVillagerTrade extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {

        MinecraftClient.getInstance().execute(() -> {
            ((MerchantScreenHandler) MinecraftClient.getInstance().player.currentScreenHandler).setRecipeIndex(job.getInt("slot"));
            ((MerchantScreenHandler) MinecraftClient.getInstance().player.currentScreenHandler).switchTo(job.getInt("slot"));
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(
                    new SelectMerchantTradeC2SPacket(job.getInt("slot"))
            );
        });
    }
}
