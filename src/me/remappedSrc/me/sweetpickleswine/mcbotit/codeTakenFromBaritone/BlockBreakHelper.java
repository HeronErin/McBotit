/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

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
