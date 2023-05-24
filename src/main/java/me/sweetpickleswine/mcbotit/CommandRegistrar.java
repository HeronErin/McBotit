package me.sweetpickleswine.mcbotit;



import me.sweetpickleswine.mcbotit.commands.*;
import me.sweetpickleswine.mcbotit.commands.baritoneCommands.*;
import me.sweetpickleswine.mcbotit.commands.chatCommands.clearRegisteredCommands;
import me.sweetpickleswine.mcbotit.commands.chatCommands.listRegisteredCommands;
import me.sweetpickleswine.mcbotit.commands.chatCommands.registerCommand;
import me.sweetpickleswine.mcbotit.commands.chatCommands.removeCommand;
import me.sweetpickleswine.mcbotit.commands.directInput.*;
import me.sweetpickleswine.mcbotit.commands.info.*;
import me.sweetpickleswine.mcbotit.commands.inventory.*;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistrar {
    public static CommandRegistrar instance = new CommandRegistrar();
    Map<String, BaseCommand> commandMap = new HashMap<>();
    CommandRegistrar(){
        commandMap.put("get system time", new getSystemTime());
        commandMap.put("register game command", new registerCommand());
        commandMap.put("get registered game commands", new listRegisteredCommands());
        commandMap.put("remove registered game command", new removeCommand());
        commandMap.put("clear registered game commands", new clearRegisteredCommands());
        commandMap.put("print to chat", new printToPlayerChat());
        commandMap.put("send public chat message", new sendPublicChatMessage());
        commandMap.put("disconnect", new disconnect());


        commandMap.put("get block", new getBlock());
        commandMap.put("get player info", new getPlayerInfo());
        commandMap.put("set hotbar slot", new setHotbarSlot());
        commandMap.put("realistic rot", new realisticRotate());
        commandMap.put("get rotation to get to block", new getRotationOfBlock());
        commandMap.put("hold button", new buttonHold());
        commandMap.put("hold button for time", new holdForSetTime());
        commandMap.put("release all buttons", new releaseButtons());
        commandMap.put("release button", new buttonRelease());
        commandMap.put("jump", new jump());
        commandMap.put("start fall flying", new startFallFlying());
        commandMap.put("use item", new UseItem());
        commandMap.put("right click block", new rightClickBlock());
        commandMap.put("baritone goto normal", new baritoneGoto());
        commandMap.put("baritone goto only walk", new baritoneOnlyWalk());
        commandMap.put("normal break", new normalBreakBlock());
        commandMap.put("normal place", new normalPlaceBlock());
        commandMap.put("printer place", new printerPlace());
        commandMap.put("baritone break", new baritoneBreakBlock());
        commandMap.put("baritone place block", new baritonePlaceBlock());
        commandMap.put("hacker place block", new hackerPlace());

        commandMap.put("wait for workers", new waitForWorkers());
        commandMap.put("interact with entity", new EntityInteract());
        commandMap.put("get villager trade info", new GetVillagerTrades());


        commandMap.put("close current screen", new closeCurrentScreen());
        commandMap.put("get player inventory", new getPlayerInventory());
        commandMap.put("get open inventory", new getOpenInventory());
        commandMap.put("swap slots", new swapSlots());
        commandMap.put("set villager trade", new SetVillagerTrade());
        commandMap.put("throwout slot", new throwAwaySlot());
        commandMap.put("click special inventory button", new ClickSpecialInventoryButton());
    }

}
