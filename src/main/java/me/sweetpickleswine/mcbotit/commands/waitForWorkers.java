package me.sweetpickleswine.mcbotit.commands;

import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import org.json.JSONObject;

import java.io.IOException;

public class waitForWorkers extends BaseCommand{
    @Override
    public void onExec(Client c, JSONObject job) {

            while (Bin.instance.workers.size() !=0){
                Bin.instance.removeFinishedWorkers();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    try {c.writeJson(new JSONObject("{'job': 'finished'}"));} catch (IOException ignored) {}
                    return;
                }
            }
            try {c.writeJson(new JSONObject("{'job': 'finished'}"));} catch (IOException ignored) {}

    }
}
