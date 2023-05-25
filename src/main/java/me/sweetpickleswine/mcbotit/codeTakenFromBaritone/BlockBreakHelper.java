package me.sweetpickleswine.mcbotit.codeTakenFromBaritone;


import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

import static me.sweetpickleswine.mcbotit.codeTakenFromBaritone.RayTraceUtils.rayTraceTowards;

public class BlockBreakHelper {

    private boolean didBreakLastTick;



    public void stopBreakingBlock() {

        if (MinecraftClient.getInstance().player != null && this.didBreakLastTick) {
            if (!PlayerController.instance.hasBrokenBlock()) {
                PlayerController.instance.setHittingBlock(true);
            }

            PlayerController.instance.resetBlockRemoving();
            this.didBreakLastTick = false;
        }

    }

    public void tick(boolean isLeftClick) {
        HitResult trace = rayTraceTowards(MinecraftClient.getInstance().player, new Rotation(MinecraftClient.getInstance().player.getYaw(), MinecraftClient.getInstance().player.getPitch()), 4.5);
        boolean isBlockTrace = trace != null && trace.getType() == HitResult.Type.BLOCK;
        if (isLeftClick && isBlockTrace) {
            if (!this.didBreakLastTick) {
                PlayerController.instance.syncHeldItem();
                PlayerController.instance.clickBlock(((BlockHitResult)trace).getBlockPos(), ((BlockHitResult)trace).getSide());
                MinecraftClient.getInstance().player.swingHand(Hand.MAIN_HAND);
            }

            if (PlayerController.instance.onPlayerDamageBlock(((BlockHitResult)trace).getBlockPos(), ((BlockHitResult)trace).getSide())) {
                MinecraftClient.getInstance().player.swingHand(Hand.MAIN_HAND);
            }

            PlayerController.instance.setHittingBlock(false);
            this.didBreakLastTick = true;
        } else if (this.didBreakLastTick) {
            this.stopBreakingBlock();
            this.didBreakLastTick = false;
        }

    }
}
