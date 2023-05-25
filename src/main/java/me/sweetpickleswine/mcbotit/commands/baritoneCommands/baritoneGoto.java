package me.sweetpickleswine.mcbotit.commands.baritoneCommands;


import me.sweetpickleswine.mcbotit.BaritoneHandler;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

public class baritoneGoto extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        BaritoneHandler.baritoneGoto(job.getInt("x"), job.getInt("y"), job.getInt("z"));
    }
}
