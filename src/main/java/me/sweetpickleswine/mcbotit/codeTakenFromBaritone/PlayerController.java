package me.sweetpickleswine.mcbotit.codeTakenFromBaritone;

import me.sweetpickleswine.mcbotit.mixin.ClientPlayerInteractionManagerAccessor;
import net.minecraft.client.MinecraftClient;
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

public class PlayerController implements IPlayerController {
    public static PlayerController instance = new PlayerController();


    public void syncHeldItem() {
        ((ClientPlayerInteractionManagerAccessor) MinecraftClient.getInstance().interactionManager).invokeSyncSelectedSlot();
    }

    public boolean hasBrokenBlock() {
        return ((ClientPlayerInteractionManagerAccessor)MinecraftClient.getInstance().interactionManager).getCurrentBreakingPos().getY() == -1;
    }

    public boolean onPlayerDamageBlock(BlockPos pos, Direction side) {
        return MinecraftClient.getInstance().interactionManager.updateBlockBreakingProgress(pos, side);
    }

    public void resetBlockRemoving() {
        MinecraftClient.getInstance().interactionManager.cancelBlockBreaking();
    }

    public void windowClick(int windowId, int slotId, int mouseButton, SlotActionType type, PlayerEntity player) {
        MinecraftClient.getInstance().interactionManager.clickSlot(windowId, slotId, mouseButton, type, player);
    }

    public GameMode getGameType() {
        return MinecraftClient.getInstance().interactionManager.getCurrentGameMode();
    }

    public ActionResult processRightClickBlock(ClientPlayerEntity player, World world, Hand hand, BlockHitResult result) {
        return MinecraftClient.getInstance().interactionManager.interactBlock(player, hand, result);
    }

    public ActionResult processRightClick(ClientPlayerEntity player, World world, Hand hand) {
        return MinecraftClient.getInstance().interactionManager.interactItem(player, hand);
    }

    public boolean clickBlock(BlockPos loc, Direction face) {
        return MinecraftClient.getInstance().interactionManager.attackBlock(loc, face);
    }

    public void setHittingBlock(boolean hittingBlock) {
        ((ClientPlayerInteractionManagerAccessor)MinecraftClient.getInstance().interactionManager).setBreakingBlock(hittingBlock);
    }
}
