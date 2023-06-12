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
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.Optional;

public final class RayTraceUtils {
    private RayTraceUtils() {
    }

    public static HitResult rayTraceTowards(Entity entity, Rotation rotation, double blockReachDistance) {
        return rayTraceTowards(entity, rotation, blockReachDistance, false);
    }

    public static HitResult rayTraceTowards(Entity entity, Rotation rotation, double blockReachDistance, boolean wouldSneak) {
        Vec3d start;
        if (wouldSneak) {
            start = inferSneakingEyePosition(entity);
        } else {
            start = entity.getCameraPosVec(1.0F);
        }

        Vec3d direction = RotationUtils.calcVector3dFromRotation(rotation);
        Vec3d end = start.add(direction.x * blockReachDistance, direction.y * blockReachDistance, direction.z * blockReachDistance);
        return entity.getWorld().raycast(new RaycastContext(start, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, entity));
    }

    public static Vec3d inferSneakingEyePosition(Entity entity) {

        return new Vec3d(entity.getX(), entity.getY() + MinecraftClient.getInstance().player.getEyeHeight(MinecraftClient.getInstance().player.getPose()), entity.getZ());
    }

    public static Optional<BlockPos> getSelectedBlock() {
        HitResult result = rayTraceTowards(MinecraftClient.getInstance().player, new Rotation(MinecraftClient.getInstance().player.getYaw(), MinecraftClient.getInstance().player.getPitch()), 4.5);
        return result != null && result.getType() == HitResult.Type.BLOCK ? Optional.of(((BlockHitResult) result).getBlockPos()) : Optional.empty();
    }

    public static boolean isLookingAt(BlockPos pos) {
        return getSelectedBlock().equals(Optional.of(pos));
    }

}