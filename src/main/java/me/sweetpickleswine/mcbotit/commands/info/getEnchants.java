package me.sweetpickleswine.mcbotit.commands.info;

import com.google.gson.JsonArray;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.EnchantmentScreenHandler;

import java.io.IOException;

public class getEnchants extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        if (MinecraftClient.getInstance().player.currentScreenHandler instanceof EnchantmentScreenHandler){
            EnchantmentScreenHandler esh = (EnchantmentScreenHandler) MinecraftClient.getInstance().player.currentScreenHandler;
            ItemStack itemStack = MinecraftClient.getInstance().player.getInventory().getStack(0);


            JsonArray output = new JsonArray();


            if (!itemStack.isEmpty()) {
                for (int i = 0; i < 3; ++i) {
                    if (esh.enchantmentId[i] != -1) {
                        JSONObject enchantment = new JSONObject();

                        enchantment.put("id", Registries.ENCHANTMENT.getId(Registries.ENCHANTMENT.getEntry(esh.enchantmentId[i]).get().value()).toString());
                        enchantment.put("level", esh.enchantmentLevel[i]);
                        enchantment.put("cost", esh.enchantmentPower[i]);

                        output.add(enchantment.job);
                    }
                }
            }
            JSONObject ret = new JSONObject();
            ret.put("data", output);

            try {
                c.writeJson(ret);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
