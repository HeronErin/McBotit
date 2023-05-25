package me.sweetpickleswine.mcbotit.commands.info;

import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

import java.io.IOException;

public class isBaritoneInstalled extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        JSONObject out = new JSONObject();
        out.put("isInstalled", Bin.instance.hasBaritoneInstalled);
        try {
            c.writeJson(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
