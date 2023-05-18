package me.sweetpickleswine.mcbotit.commands.directInput;

import baritone.api.BaritoneAPI;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import org.json.JSONObject;

public class releaseButtons extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getInputOverrideHandler()
                .clearAllKeys();
    }
}
