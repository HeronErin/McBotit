package me.sweetpickleswine.mcbotit.commands.directInput;


import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.Input;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.InputOverideHandler;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

public class holdForSetTime extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        Input i = buttonHold.inp(job.getString("key"));
        Bin.instance.registerAndStartThread(new Thread(() -> {
            InputOverideHandler in = Bin.instance.inputOverideHandler;
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
