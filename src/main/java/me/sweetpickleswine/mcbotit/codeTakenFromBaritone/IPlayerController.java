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