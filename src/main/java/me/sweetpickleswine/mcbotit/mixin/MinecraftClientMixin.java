package me.sweetpickleswine.mcbotit.mixin;

import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.MainServer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static me.sweetpickleswine.mcbotit.ChatUtil.sendMessage;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(at = @At("TAIL"), method = "joinWorld")
    public void onJoin(CallbackInfo ci) {

        if (Bin.instance.currentServer != null) {
            Bin.instance.currentServer.thread.interrupt();
        } else {
            int port = (new Random()).nextInt(5000, 26000);

            Bin.instance.currentServer = new MainServer(port);
        }
        sendMessage("Connected on port " + Bin.instance.currentServer.port);

        FabricLoader.getInstance().getAllMods().forEach((modContainer) -> {
            if (modContainer.toString().contains("baritone")) {
                Bin.instance.hasBaritoneInstalled = true;
            }
        });


    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {



        if (Bin.instance.lockScreen && MinecraftClient.getInstance().currentScreen == null) {
            MinecraftClient.getInstance().setScreen(new ChatScreen(""));
        }
        if (Bin.instance.tickQueue.size() != 0) {
            Bin.instance.tickQueue.remove(0).run();
        }

        Bin.instance.inputOverideHandler.onTick();

    }

}
