package me.sweetpickleswine.mcbotit.commands;

import me.sweetpickleswine.mcbotit.ChatUtil;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

public class printToPlayerChat extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        ChatUtil.sendMessage(job.getString("msg"));
    }
}
