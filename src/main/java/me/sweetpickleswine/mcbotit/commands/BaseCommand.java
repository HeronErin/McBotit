package me.sweetpickleswine.mcbotit.commands;

import me.sweetpickleswine.mcbotit.Client;
import org.json.JSONObject;

public abstract class BaseCommand {
    public abstract void onExec(Client c, JSONObject job);
}
