package me.sweetpickleswine.mcbotit.commands.chatCommands;

import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class listRegisteredCommands extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        JSONArray commands = new JSONArray();
        Bin.instance.commands.forEach((command, desc)->{
            JSONObject outCommand = new JSONObject();
            outCommand.put("command", command);
            outCommand.put("desc", desc);
            commands.put(outCommand);
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
