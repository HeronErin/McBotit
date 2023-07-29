package me.sweetpickleswine.mcbotit.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientPlayerInteractionManager.class)
public interface ClientPlayerInteractionManagerAccessor {

    @Accessor("breakingBlock")
    void setBreakingBlock(boolean var1);
    @Accessor("currentBreakingPos")
    BlockPos getCurrentBreakingPos();

    @Invoker("syncSelectedSlot")
    void invokeSyncSelectedSlot();
}
