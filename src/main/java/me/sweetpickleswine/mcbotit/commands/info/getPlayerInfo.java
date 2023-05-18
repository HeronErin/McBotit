package me.sweetpickleswine.mcbotit.commands.info;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.json.JSONObject;

import java.io.IOException;

public class getPlayerInfo extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        PlayerEntity p = MinecraftClient.getInstance().player;
        JSONObject returnData = new JSONObject();
        returnData.put("x", p.getX());
        returnData.put("y", p.getY());
        returnData.put("z", p.getZ());
        returnData.put("selectedHotbarSlot", p.getInventory().selectedSlot+1);
        returnData.put("pitch", p.getPitch());
        returnData.put("yaw", p.getYaw());
        returnData.put("isElytraFlying", p.isFallFlying());
        returnData.put("isCreative", p.isCreative());
        returnData.put("isSpectator", p.isSpectator());
        returnData.put("UUID", p.getGameProfile().getId().toString());
        returnData.put("id", p.getId());
        returnData.put("isOnGround", p.isOnGround());
        returnData.put("isSleeping", p.isSleeping());
        returnData.put("isDead", p.isDead());
        returnData.put("isSwimming", p.isSwimming());
        returnData.put("isClimbing", p.isClimbing());
        returnData.put("isCrawling", p.isCrawling());
        returnData.put("isOnFire", p.isOnFire());
        returnData.put("isSneaking", p.isSneaking());
        returnData.put("isInLava", p.isInLava());
        returnData.put("isFrozen", p.isFrozen());
        returnData.put("isInsideWall", p.isInsideWall());

        try {
            c.writeJson(returnData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
