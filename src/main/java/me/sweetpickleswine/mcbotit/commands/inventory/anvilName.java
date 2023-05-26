package me.sweetpickleswine.mcbotit.commands.inventory;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import me.sweetpickleswine.mcbotit.mixin.AnvilScreenAccessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.EnchantmentScreenHandler;

import java.io.IOException;

public class anvilName extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        if (MinecraftClient.getInstance().player.currentScreenHandler instanceof AnvilScreenHandler) {
            AnvilScreenHandler ash = (AnvilScreenHandler)MinecraftClient.getInstance().player.currentScreenHandler;
            if (job.has("get")) {
                JSONObject out = new JSONObject();
                out.put("name", ((AnvilScreenAccessor)MinecraftClient.getInstance().currentScreen).getNameField().getText().toString());
                out.put("cost", ash.getLevelCost());
                out.put("can take",
                        (
                                ash.getLevelCost() < 40 // "too expensive"
                                && MinecraftClient.getInstance().player.experienceLevel >= ash.getLevelCost()) // Does player have enough xp?
                                || MinecraftClient.getInstance().player.getAbilities().creativeMode); // OR in gmc
                try {
                    c.writeJson(out);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (job.has("rename")){
                assert MinecraftClient.getInstance().currentScreen != null;
                ((AnvilScreenAccessor)MinecraftClient.getInstance().currentScreen).onRenamedInvoker(job.getString("name"));
                ((AnvilScreenAccessor)MinecraftClient.getInstance().currentScreen).getNameField().setText(job.getString("name"));
            }
        }
    }
}
