package me.sweetpickleswine.mcbotit.commands.baritoneCommands;


import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.Rotation;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import oshi.util.tuples.Quartet;

import java.util.ArrayList;
import java.util.List;

public class printerPlace extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        final BlockPos pos = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));
        final int slot = job.getInt("slot");

        if (MinecraftClient.getInstance().world.getBlockState(pos).getBlock().getTranslationKey().equalsIgnoreCase("block.minecraft.air")) {

            Vec3d eyesPos = new Vec3d(MinecraftClient.getInstance().player.getX(),
                    MinecraftClient.getInstance().player.getY() + MinecraftClient.getInstance().player.getEyeHeight(MinecraftClient.getInstance().player.getPose()),
                    MinecraftClient.getInstance().player.getZ());


            if (MinecraftClient.getInstance().world.getBlockState(pos).getBlock().getTranslationKey().equals("block.minecraft.air")) {
                List<Quartet<Boolean, Vec3d, Direction, BlockPos>> options = new ArrayList<>();
                for (Direction side : Direction.values()) {
                    BlockPos neighbor = pos.offset(side);
                    Direction side2 = side.getOpposite();

                    // check if side is visible (facing away from player)
                    if (eyesPos.squaredDistanceTo(Vec3d.ofCenter(pos)) >= eyesPos
                            .squaredDistanceTo(Vec3d.ofCenter(neighbor)))
                        continue;


                    Vec3d hitVec = Vec3d.ofCenter(neighbor)
                            .add(Vec3d.of(side2.getVector()).multiply(0.5));

                    // check if hitVec is within range (4.25 blocks)
                    if (eyesPos.squaredDistanceTo(hitVec) > 18.0625)
                        continue;
                    BlockState b = MinecraftClient.getInstance().world.getBlockState(neighbor);
                    options.add(new Quartet<>(b.getBlock().getTranslationKey().equalsIgnoreCase("block.minecraft.air"), hitVec, side, pos));
                }

                for (Quartet<Boolean, Vec3d, Direction, BlockPos> option : options) {
                    if (!option.getA()) {
                        place(slot, option.getB(), option.getC(), option.getD());
                        return;
                    }
                }
                for (Quartet<Boolean, Vec3d, Direction, BlockPos> option : options) {
                    place(slot, option.getB(), option.getC(), option.getD());
                    return;
                }

            }

        }
    }

    public void place(int slot, Vec3d hitVec, Direction side, BlockPos pos) {
        Bin.instance.tickQueue.add(() -> {
            MinecraftClient.getInstance().player.getInventory().selectedSlot = slot;
        });
        Bin.instance.tickQueue.add(() -> {
            PlayerEntity player = MinecraftClient.getInstance().player;
            Rotation neededRot = hackerPlace.getNeededRotations(hitVec);
            player.setPitch(neededRot.getPitch());
            player.setYaw(neededRot.getYaw());
            PlayerMoveC2SPacket packet = new PlayerMoveC2SPacket.Full(player.getX(), player.getY(), player.getZ(), neededRot.getYaw(), neededRot.getPitch(), player.isOnGround());

            MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
        });
        Bin.instance.tickQueue.add(() -> {
            PlayerEntity player = MinecraftClient.getInstance().player;
            MinecraftClient.getInstance().interactionManager.interactBlock((ClientPlayerEntity) player, Hand.MAIN_HAND, new BlockHitResult(hitVec, side, pos, false));
            MinecraftClient.getInstance().interactionManager.interactItem(player, Hand.MAIN_HAND);


            MinecraftClient.getInstance().player.swingHand(Hand.MAIN_HAND);
        });

    }
}
