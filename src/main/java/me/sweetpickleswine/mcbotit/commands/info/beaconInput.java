package me.sweetpickleswine.mcbotit.commands.info;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import me.sweetpickleswine.mcbotit.mixin.BeaconScreenAccesor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.network.packet.c2s.play.UpdateBeaconC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class beaconInput extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        if (MinecraftClient.getInstance().currentScreen instanceof BeaconScreen){

            if (job.has("done")){
                BeaconScreenAccesor bse =  ((BeaconScreenAccesor)MinecraftClient.getInstance().currentScreen);
                MinecraftClient.getInstance().execute(()->{
                    MinecraftClient.getInstance().getNetworkHandler().sendPacket(new UpdateBeaconC2SPacket(Optional.ofNullable(bse.getPrimaryEffect()), Optional.ofNullable(bse.getSecondaryEffect())));
                    MinecraftClient.getInstance().player.closeHandledScreen();
                });


                return;
            }

            StatusEffect b = Registries.STATUS_EFFECT.getOrEmpty(new Identifier(job.getString("id"))).orElse(null);
            if (job.getString("effectType").equalsIgnoreCase("primary"))
                ((BeaconScreenAccesor)MinecraftClient.getInstance().currentScreen).setPrimaryEffect(b);
            else if (job.getString("effectType").equalsIgnoreCase("secondary"))
                ((BeaconScreenAccesor)MinecraftClient.getInstance().currentScreen).setSecondaryEffect(b);
        }
    }
}
