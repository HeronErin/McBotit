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


import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

import java.util.Optional;

import static me.sweetpickleswine.mcbotit.codeTakenFromBaritone.RayTraceUtils.isLookingAt;

public final class RotationUtils {
    public static final double DEG_TO_RAD = 0.017453292519943295;
    public static final double RAD_TO_DEG = 57.29577951308232;
    private static final Vec3d[] BLOCK_SIDE_MULTIPLIERS = new Vec3d[]{new Vec3d(0.5, 0.0, 0.5), new Vec3d(0.5, 1.0, 0.5), new Vec3d(0.5, 0.5, 0.0), new Vec3d(0.5, 0.5, 1.0), new Vec3d(0.0, 0.5, 0.5), new Vec3d(1.0, 0.5, 0.5)};

    private RotationUtils() {
    }

    public static Rotation calcRotationFromCoords(BlockPos orig, BlockPos dest) {
        return calcRotationFromVec3d(new Vec3d(orig.getX(), orig.getY(), orig.getZ()), new Vec3d(dest.getX(), dest.getY(), dest.getZ()));
    }

    public static Rotation wrapAnglesToRelative(Rotation current, Rotation target) {
        return current.yawIsReallyClose(target) ? new Rotation(current.getYaw(), target.getPitch()) : target.subtract(current).normalize().add(current);
    }

    public static Rotation calcRotationFromVec3d(Vec3d orig, Vec3d dest, Rotation current) {
        return wrapAnglesToRelative(current, calcRotationFromVec3d(orig, dest));
    }

    private static Rotation calcRotationFromVec3d(Vec3d orig, Vec3d dest) {
        double[] delta = new double[]{orig.x - dest.x, orig.y - dest.y, orig.z - dest.z};
        double yaw = MathHelper.atan2(delta[0], -delta[2]);
        double dist = Math.sqrt(delta[0] * delta[0] + delta[2] * delta[2]);
        double pitch = MathHelper.atan2(delta[1], dist);
        return new Rotation((float) (yaw * 57.29577951308232), (float) (pitch * 57.29577951308232));
    }

    public static Vec3d calcVector3dFromRotation(Rotation rotation) {
        float f = MathHelper.cos(-rotation.getYaw() * 0.017453292F - 3.1415927F);
        float f1 = MathHelper.sin(-rotation.getYaw() * 0.017453292F - 3.1415927F);
        float f2 = -MathHelper.cos(-rotation.getPitch() * 0.017453292F);
        float f3 = MathHelper.sin(-rotation.getPitch() * 0.017453292F);
        return new Vec3d(f1 * f2, f3, f * f2);
    }


    public static Optional<Rotation> reachable(ClientPlayerEntity entity, BlockPos pos, double blockReachDistance) {
        return reachable(entity, pos, blockReachDistance, false);
    }

    public static Optional<Rotation> reachable(ClientPlayerEntity entity, BlockPos pos, double blockReachDistance, boolean wouldSneak) {

        if (isLookingAt(pos)) {
            Rotation hypothetical = new Rotation(entity.getYaw(), entity.getPitch() + 1.0E-4F);
            if (!wouldSneak) {
                return Optional.of(hypothetical);
            }

            HitResult result = RayTraceUtils.rayTraceTowards(entity, hypothetical, blockReachDistance, true);
            if (result != null && result.getType() == HitResult.Type.BLOCK && ((BlockHitResult) result).getBlockPos().equals(pos)) {
                return Optional.of(hypothetical);
            }
        }

        Optional<Rotation> possibleRotation = reachableCenter(entity, pos, blockReachDistance, wouldSneak);
        if (possibleRotation.isPresent()) {
            return possibleRotation;
        } else {
            BlockState state = entity.getWorld().getBlockState(pos);
            VoxelShape shape = state.getOutlineShape(entity.getWorld(), pos);
            if (shape.isEmpty()) {
                shape = VoxelShapes.fullCube();
            }

            Vec3d[] var9 = BLOCK_SIDE_MULTIPLIERS;
            int var10 = var9.length;

            for (int var11 = 0; var11 < var10; ++var11) {
                Vec3d sideOffset = var9[var11];
                double xDiff = shape.getMin(Direction.Axis.X) * sideOffset.x + shape.getMax(Direction.Axis.X) * (1.0 - sideOffset.x);
                double yDiff = shape.getMin(Direction.Axis.Y) * sideOffset.y + shape.getMax(Direction.Axis.Y) * (1.0 - sideOffset.y);
                double zDiff = shape.getMin(Direction.Axis.Z) * sideOffset.z + shape.getMax(Direction.Axis.Z) * (1.0 - sideOffset.z);
                possibleRotation = reachableOffset(entity, pos, (new Vec3d(pos.getX(), pos.getY(), pos.getZ())).add(xDiff, yDiff, zDiff), blockReachDistance, wouldSneak);
                if (possibleRotation.isPresent()) {
                    return possibleRotation;
                }
            }

            return Optional.empty();
        }
    }

    public static Optional<Rotation> reachableOffset(Entity entity, BlockPos pos, Vec3d offsetPos, double blockReachDistance, boolean wouldSneak) {
        Vec3d eyes = wouldSneak ? RayTraceUtils.inferSneakingEyePosition(entity) : entity.getCameraPosVec(1.0F);
        Rotation rotation = calcRotationFromVec3d(eyes, offsetPos, new Rotation(entity.getYaw(), entity.getPitch()));
        HitResult result = RayTraceUtils.rayTraceTowards(entity, rotation, blockReachDistance, wouldSneak);
        if (result != null && result.getType() == HitResult.Type.BLOCK) {
            if (((BlockHitResult) result).getBlockPos().equals(pos)) {
                return Optional.of(rotation);
            }

            if (entity.getWorld().getBlockState(pos).getBlock() instanceof AbstractFireBlock && ((BlockHitResult) result).getBlockPos().equals(pos.down())) {
                return Optional.of(rotation);
            }
        }

        return Optional.empty();
    }

    public static Optional<Rotation> reachableCenter(Entity entity, BlockPos pos, double blockReachDistance, boolean wouldSneak) {
        return reachableOffset(entity, pos, calculateBlockCenter(entity.getWorld(), pos), blockReachDistance, wouldSneak);
    }

    public static Vec3d calculateBlockCenter(World world, BlockPos pos) {
        BlockState b = world.getBlockState(pos);
        VoxelShape shape = b.getCollisionShape(world, pos);
        if (shape.isEmpty()) {
            return getBlockPosCenter(pos);
        } else {
            double xDiff = (shape.getMin(Direction.Axis.X) + shape.getMax(Direction.Axis.X)) / 2.0;
            double yDiff = (shape.getMin(Direction.Axis.Y) + shape.getMax(Direction.Axis.Y)) / 2.0;
            double zDiff = (shape.getMin(Direction.Axis.Z) + shape.getMax(Direction.Axis.Z)) / 2.0;
            if (!Double.isNaN(xDiff) && !Double.isNaN(yDiff) && !Double.isNaN(zDiff)) {
                if (b.getBlock() instanceof AbstractFireBlock) {
                    yDiff = 0.0;
                }

                return new Vec3d((double) pos.getX() + xDiff, (double) pos.getY() + yDiff, (double) pos.getZ() + zDiff);
            } else {
                throw new IllegalStateException("" + b + " " + pos + " " + shape);
            }
        }
    }

    public static Vec3d getBlockPosCenter(BlockPos pos) {
        return new Vec3d((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5);
    }
}

