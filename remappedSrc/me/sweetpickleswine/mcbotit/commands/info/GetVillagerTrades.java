package me.sweetpickleswine.mcbotit.commands.info;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.MerchantScreenHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class GetVillagerTrades extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {

        MerchantScreenHandler ms = (MerchantScreenHandler) MinecraftClient.getInstance().player.currentScreenHandler;

        try {
            c.writeNbt(ms.getRecipes().toNbt());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        JSONObject returnInfo = new JSONObject();
//

    }


}
