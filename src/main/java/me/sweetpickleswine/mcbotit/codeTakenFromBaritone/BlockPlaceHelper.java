package me.sweetpickleswine.mcbotit.codeTakenFromBaritone;


import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

import static me.sweetpickleswine.mcbotit.codeTakenFromBaritone.RayTraceUtils.rayTraceTowards;

public class BlockPlaceHelper {

    private int rightClickTimer;

    BlockPlaceHelper() {

    }

    public void tick(boolean rightClickRequested) {
        if (this.rightClickTimer > 0) {
            --this.rightClickTimer;
        } else {
            HitResult mouseOver = rayTraceTowards(MinecraftClient.getInstance().player, new Rotation(MinecraftClient.getInstance().player.getYaw(), MinecraftClient.getInstance().player.getPitch()), 4.5);
            if (rightClickRequested && !MinecraftClient.getInstance().player.isRiding() && mouseOver != null && mouseOver.getType() == HitResult.Type.BLOCK) {
                this.rightClickTimer = 4;
                Hand[] var3 = Hand.values();
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Hand hand = var3[var5];
                    if (PlayerController.instance.processRightClickBlock(MinecraftClient.getInstance().player, MinecraftClient.getInstance().world, hand, (BlockHitResult)mouseOver) == ActionResult.SUCCESS) {
                        MinecraftClient.getInstance().player.swingHand(hand);
                        return;
                    }

                    if (!MinecraftClient.getInstance().player.getStackInHand(hand).isEmpty() && PlayerController.instance.processRightClick(MinecraftClient.getInstance().player, MinecraftClient.getInstance().world, hand) == ActionResult.SUCCESS) {
                        return;
                    }
                }

            }
        }
    }
}
