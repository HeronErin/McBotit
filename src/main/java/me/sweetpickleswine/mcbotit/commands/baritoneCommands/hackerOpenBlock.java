package me.sweetpickleswine.mcbotit.commands.baritoneCommands;

import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.BlockRotateUtil;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.Rotation;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.commands.baritoneCommands.hackerPlace;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class hackerOpenBlock extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        BlockPos bp = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));

        Rotation r = BlockRotateUtil.getRotationForBlock(bp);
        Vec3d hitVec = Vec3d.ofCenter(bp);
        Bin.instance.tickQueue.add(() -> {
            PlayerEntity player = MinecraftClient.getInstance().player;


            PlayerMoveC2SPacket packet = new PlayerMoveC2SPacket.Full(player.getX(), player.getY(), player.getZ(), r.getYaw(), r.getPitch(), player.isOnGround());

            MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
        });
        Bin.instance.tickQueue.add(() -> {
            PlayerEntity player = MinecraftClient.getInstance().player;
            MinecraftClient.getInstance().interactionManager.interactBlock((ClientPlayerEntity) player, Hand.MAIN_HAND, new BlockHitResult(hitVec, Direction.NORTH, bp, false));
            MinecraftClient.getInstance().interactionManager.interactItem(player, Hand.MAIN_HAND);


            MinecraftClient.getInstance().player.swingHand(Hand.MAIN_HAND);
        });


    }
}
