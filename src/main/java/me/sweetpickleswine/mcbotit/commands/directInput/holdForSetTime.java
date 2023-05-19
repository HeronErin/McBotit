package me.sweetpickleswine.mcbotit.commands.directInput;

import baritone.api.BaritoneAPI;
import baritone.api.utils.IInputOverrideHandler;
import baritone.api.utils.input.Input;
import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

public class holdForSetTime extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        Input i = buttonHold.inp(job.getString("key"));
        Bin.instance.registerAndStartThread(new Thread(()->{
            IInputOverrideHandler in = BaritoneAPI.getProvider().getPrimaryBaritone().getInputOverrideHandler();
            try {
                in.setInputForceState(i, true);
                Thread.sleep(job.getLong("delay"));
                in.setInputForceState(i, false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}
