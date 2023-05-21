package me.sweetpickleswine.mcbotit.commands.directInput;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class startFallFlying extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        MinecraftClient.getInstance().execute(()->{;
            ItemStack itemStack;
            PlayerEntity p = MinecraftClient.getInstance().player;

            if (!p.getAbilities().flying && !p.hasVehicle() && !p.isClimbing() && (itemStack = p.getEquippedStack(EquipmentSlot.CHEST)).isOf(Items.ELYTRA) && ElytraItem.isUsable(itemStack) && p.checkFallFlying()) {
                MinecraftClient.getInstance().getNetworkHandler().sendPacket(new ClientCommandC2SPacket(p, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
            }
        });
    }
}
