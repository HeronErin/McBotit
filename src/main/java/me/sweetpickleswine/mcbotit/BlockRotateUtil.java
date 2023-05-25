package me.sweetpickleswine.mcbotit;


import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.RayTraceUtils;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.Rotation;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.RotationUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class BlockRotateUtil {
    public static Rotation getRotationForBlock(BlockPos target) {
        return RotationUtils.calcRotationFromCoords(BlockPos.ofFloored(MinecraftClient.getInstance().player.getEyePos()), target);
    }

    public static double distanceOfRotations(Rotation r1, Rotation r2) {
        double pitchDiff = r1.getPitch() - r2.getPitch();
        double yawDiff = r1.getYaw() - r2.getYaw();
        double distance = Math.sqrt(pitchDiff * pitchDiff + yawDiff * yawDiff);
        return distance;
    }

    public static HitResult rayTrace(Rotation r) {
        double brd = 4.5;
        return RayTraceUtils.rayTraceTowards(MinecraftClient.getInstance().player, r, brd);
    }
}
