package me.sweetpickleswine.mcbotit.commands.directInput;

import baritone.api.BaritoneAPI;
import baritone.api.utils.input.Input;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

public class buttonHold extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getInputOverrideHandler()
                .setInputForceState(
                        inp(job.getString("key")), true);
    }
    public static Input inp(String in){
        switch (in) {
            case "forward":
                return Input.MOVE_FORWARD;
            case "left":
                return Input.MOVE_LEFT;
            case "right":
                return Input.MOVE_RIGHT;
            case "back":
                return Input.MOVE_BACK;
            case "bu":
                return Input.CLICK_LEFT;
            case "right click":
                return Input.CLICK_RIGHT;
            case "sneak":
                return Input.SNEAK;
            default:
                return null;
        }
    }
}
