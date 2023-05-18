package me.sweetpickleswine.mcbotit.commands.chatCommands;

import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import org.json.JSONObject;

public class registerCommand extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        Bin.instance.commands.put(job.getString("command"), job.getString("desc"));
    }
}
