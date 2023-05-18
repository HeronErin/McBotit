package me.sweetpickleswine.mcbotit.commands.directInput;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.json.JSONObject;

public class EntityInteract extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        EntityHitResult eh = (EntityHitResult) MinecraftClient.getInstance().crosshairTarget;
        if (eh.getType() == HitResult.Type.ENTITY) {
            Entity e = eh.getEntity();
            if (job.getString("type").equalsIgnoreCase("open")){
                MinecraftClient.getInstance().getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.interact(e, MinecraftClient.getInstance().player.isSneaking(), Hand.MAIN_HAND));
            }else{
                MinecraftClient.getInstance().getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(e, MinecraftClient.getInstance().player.isSneaking()));
            }
        }
    }
}
