package me.sweetpickleswine.mcbotit.commands.inventory;

import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

import static me.sweetpickleswine.mcbotit.inventoryUtils.swapSlots;

public class swapSlots extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        Bin.instance.registerAndStartThread(new Thread(() -> {
            swapSlots(job.getInt("slot1"), job.getInt("slot2"), job.getLong("delay"));
        }));

    }
}
