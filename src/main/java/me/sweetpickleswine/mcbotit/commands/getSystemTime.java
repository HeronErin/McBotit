package me.sweetpickleswine.mcbotit.commands;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

import java.io.IOException;

public class getSystemTime extends BaseCommand {

    @Override
    public void onExec(Client c, JSONObject job) {
        JSONObject ojob = new JSONObject();
        ojob.put("time", System.currentTimeMillis());
        try {
            c.writeJson(ojob);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
