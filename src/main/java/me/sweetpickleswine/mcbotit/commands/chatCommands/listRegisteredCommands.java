package me.sweetpickleswine.mcbotit.commands.chatCommands;

import com.google.gson.JsonArray;
import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

import java.io.IOException;

public class listRegisteredCommands extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        JsonArray commands = new JsonArray();
        Bin.instance.commands.forEach((command, desc)->{
            JSONObject outCommand = new JSONObject();
            outCommand.put("command", command);
            outCommand.put("desc", desc);
            commands.add(outCommand.job);
        });
        JSONObject output = new JSONObject();
        output.put("commands", commands);
        try {
            c.writeJson(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
