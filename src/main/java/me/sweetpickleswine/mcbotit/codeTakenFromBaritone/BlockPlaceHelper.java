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
                Hand[] hand_enums = Hand.values();
                int hand_length = hand_enums.length;

                for(int hand_index = 0; hand_index < hand_length; ++hand_index) {
                    Hand hand = hand_enums[hand_index];
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
