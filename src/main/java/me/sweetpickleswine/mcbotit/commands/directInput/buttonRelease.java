package me.sweetpickleswine.mcbotit.commands.directInput;


import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

import static me.sweetpickleswine.mcbotit.commands.directInput.buttonHold.inp;

public class buttonRelease extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {

        Bin.instance.inputOverideHandler
                .setInputForceState(
                        inp(job.getString("key")), false);
    }
}
