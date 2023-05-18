package me.sweetpickleswine.mcbotit.commands.info;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.JsonUtils;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.screen.MerchantScreenHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;

import static me.sweetpickleswine.mcbotit.JsonUtils.itemToJson;

public class GetVillagerTrades extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {

        MerchantScreenHandler ms = (MerchantScreenHandler)MinecraftClient.getInstance().player.currentScreenHandler;
        DataOutputStream dout = new DataOutputStream(c.output);
        try {
            ms.getRecipes().toNbt().write(dout);
            endNbt(dout);
            dout.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        JSONObject returnInfo = new JSONObject();
//

    }
    public static void endNbt(DataOutputStream dout) throws IOException {
        dout.writeUTF("This is the ending text of the nbt stream1234567899876543210");
        dout.writeByte(0);
        dout.writeByte(0);
        dout.writeByte(0);
        dout.writeByte(0);
    }
}
