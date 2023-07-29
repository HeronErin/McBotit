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


import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

public interface IPlayerController {
    void syncHeldItem();

    boolean hasBrokenBlock();

    boolean onPlayerDamageBlock(BlockPos var1, Direction var2);

    void resetBlockRemoving();

    void windowClick(int var1, int var2, int var3, SlotActionType var4, PlayerEntity var5);

    GameMode getGameType();

    ActionResult processRightClickBlock(ClientPlayerEntity var1, World var2, Hand var3, BlockHitResult var4);

    ActionResult processRightClick(ClientPlayerEntity var1, World var2, Hand var3);

    boolean clickBlock(BlockPos var1, Direction var2);

    void setHittingBlock(boolean var1);

    default double getBlockReachDistance() {
        return this.getGameType().isCreative() ? 5.0 : (double) 4.5f;
    }
}