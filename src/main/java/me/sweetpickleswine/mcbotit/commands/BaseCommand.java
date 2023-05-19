package me.sweetpickleswine.mcbotit.commands;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

public abstract class BaseCommand {
    public abstract void onExec(Client c, JSONObject job);
}
