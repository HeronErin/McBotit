package me.sweetpickleswine.mcbotit;

import baritone.api.BaritoneAPI;
import baritone.api.utils.RayTraceUtils;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BlockRotateUtil {
    public static Rotation getRotationForBlock(BlockPos target){
        return RotationUtils.calcRotationFromCoords(BlockPos.ofFloored(MinecraftClient.getInstance().player.getEyePos()), target);
    }
    public static double distanceOfRotations(Rotation r1, Rotation r2){
        double pitchDiff = r1.getPitch() - r2.getPitch();
        double yawDiff = r1.getYaw() - r2.getYaw();
        double distance = Math.sqrt(pitchDiff * pitchDiff + yawDiff * yawDiff);
        return distance;
    }

    public static HitResult rayTrace(Rotation r){
        double brd = BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().playerController().getBlockReachDistance();
        return RayTraceUtils.rayTraceTowards(MinecraftClient.getInstance().player, r, brd);
    }
}
