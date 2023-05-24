package me.sweetpickleswine.mcbotit.mixin;


import me.sweetpickleswine.mcbotit.Bin;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class onLeaveMixin {

    @Inject(method = "disconnect", at = @At("HEAD"))
    public void onLeaveGameSession(CallbackInfo c){
        Bin.instance.currentServer.kill();

        try {
            Bin.instance.currentServer.thread.join();
        } catch (InterruptedException e) {throw new RuntimeException(e);}
        Bin.instance.currentServer = null;
    }
}
