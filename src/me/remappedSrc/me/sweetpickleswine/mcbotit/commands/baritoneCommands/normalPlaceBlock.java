package me.sweetpickleswine.mcbotit.commands.baritoneCommands;


import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.Input;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.RayTraceUtils;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.Rotation;
import me.sweetpickleswine.mcbotit.codeTakenFromBaritone.RotationUtils;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.commands.realisticRotate;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class normalPlaceBlock extends BaseCommand {
    public double angleDistance(Rotation rotation1, Rotation rotation2) {
        // Convert degrees to radians
        double pitch1Rad = Math.toRadians(rotation1.getPitch());
        double yaw1Rad = Math.toRadians(rotation1.getYaw());
        double pitch2Rad = Math.toRadians(rotation2.getPitch());
        double yaw2Rad = Math.toRadians(rotation2.getYaw());

        // Convert angles to 3D coordinates
        double x1 = Math.sin(pitch1Rad) * Math.cos(yaw1Rad);
        double y1 = Math.sin(pitch1Rad) * Math.sin(yaw1Rad);
        double z1 = Math.cos(pitch1Rad);

        double x2 = Math.sin(pitch2Rad) * Math.cos(yaw2Rad);
        double y2 = Math.sin(pitch2Rad) * Math.sin(yaw2Rad);
        double z2 = Math.cos(pitch2Rad);

        // Calculate distance using Euclidean distance formula
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }

    @Override
    public void onExec(Client c, JSONObject job) {
        Bin.instance.registerAndStartThread(new Thread(() -> {
            BlockPos pos = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));
            Direction userDir = null;
            if (job.has("direction"))
                switch (job.getString("direction")) {
                    case "east" -> userDir = Direction.EAST;
                    case "west" -> userDir = Direction.WEST;
                    case "south" -> userDir = Direction.SOUTH;
                    case "north" -> userDir = Direction.NORTH;
                    case "up" -> userDir = Direction.UP;
                    case "down" -> userDir = Direction.DOWN;
                }

            Direction d = canPlace(pos, userDir);
            int i = 0;
            while (d == null || (d != userDir && userDir != null)) {
                if (i > 25) break;
                Rotation r = getPlaceable(pos, userDir);

                JSONObject job_for_fake_rot_packet = new JSONObject();
                job_for_fake_rot_packet.put("pitch", r.getPitch());
                job_for_fake_rot_packet.put("yaw", r.getYaw());
                job_for_fake_rot_packet.put("time_per_90", job.getFloat("time_per_90"));

                (new realisticRotate()).onExec(c, job_for_fake_rot_packet);
                try {
                    Bin.instance.workers.get(Bin.instance.workers.size() - 1).join();
                } catch (InterruptedException e) {
                }
                i += 1;
                d = canPlace(pos, userDir);
                System.out.println(d);
            }
            if (d != null) {
                MinecraftClient.getInstance().player.getInventory().selectedSlot = job.getInt("slot");
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    return;
                }

                Bin.instance.inputOverideHandler.setInputForceState(Input.CLICK_RIGHT, true);
                String tt = "block.minecraft.air";
                long f = System.currentTimeMillis();
                while (tt.equalsIgnoreCase("block.minecraft.air") && System.currentTimeMillis() - f < 2500L) {
                    tt = MinecraftClient.getInstance().world.getBlockState(pos).getBlock().getTranslationKey();
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        Bin.instance.inputOverideHandler
                                .setInputForceState(Input.CLICK_RIGHT, false);
                        return;
                    }
                }

                Bin.instance.inputOverideHandler.setInputForceState(Input.CLICK_RIGHT, false);
            }

        }));

    }

    public Rotation getPlaceable(BlockPos blockPos, Direction dir) {
        List<BlockPos> bps = new ArrayList<>();
        PlayerEntity p = MinecraftClient.getInstance().player;
        bps.add(new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ()));
        bps.add(new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ()));
        bps.add(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() - 1));
        bps.add(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 1));
        bps.add(new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ()));
        bps.add(new BlockPos(blockPos.getX() - 1, blockPos.getY(), blockPos.getZ()));
        Vec3d cp = new Vec3d(p.getPos().x, p.getPos().y + (double) p.getStandingEyeHeight(), p.getPos().z);
        double brd = 4.5;

        List<Rotation> knownworks = new ArrayList<>();
        List<Thread> thrss = new ArrayList<>();
        for (BlockPos b : bps) {
            thrss.add(new Thread(() -> {
                if (!MinecraftClient.getInstance().world.getBlockState(b).getBlock().getTranslationKey().equals("block.minecraft.air")) {
//                    System.out.println(b);
                    Rotation r = RotationUtils.calcRotationFromCoords(new BlockPos((int) cp.getX(), (int) cp.getY(), (int) cp.getZ()), b);

                    for (float ty = r.getYaw() - 20f; ty <= r.getYaw() + 20f; ty += 0.35f) {
                        for (float tp = r.getPitch() - 20f; tp <= r.getPitch() + 20f; tp += 0.35f) {
                            Rotation trot = new Rotation(ty, tp);

                            HitResult hr = RayTraceUtils.rayTraceTowards(p, trot, brd);
                            BlockHitResult blockHit = (BlockHitResult) hr;

                            if (blockHit.getBlockPos().equals(b))
                                if (blockHit.getType() == HitResult.Type.BLOCK)
                                    if (blockPos.equals(sideToBlock(blockHit.getSide(), b)) && (dir == null || dir == blockHit.getSide())) {
                                        knownworks.add(trot);
                                    }
                        }

                    }
                }
            }));
            for (Thread t : thrss) {
                try {
                    t.start();
                } catch (java.lang.IllegalThreadStateException ignored) {
                }
            }
            for (Thread t : thrss) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (knownworks.size() != 0) {
                Random random = new Random();
                knownworks.sort((o1, o2) -> {
                    Rotation prot = new Rotation(p.getYaw(), p.getPitch());
                    double lnn = angleDistance(prot, o1);
                    double lnn2 = angleDistance(prot, o2);
                    return Double.compare(lnn, lnn2);
                });
                if (knownworks.size() < 5) return knownworks.get(knownworks.size() - 1);
                return knownworks.get(random.nextInt(0, Math.min(50, knownworks.size())));
            }
        }


        return null;
    }

    public Direction canPlace(BlockPos bp, Direction dir) {
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos blockPos = blockHit.getBlockPos();

            BlockPos airnxt = sideToBlock(blockHit.getSide(), blockPos);
            if (airnxt == null) return null;

            if (client.world.getBlockState(airnxt).getBlock().getTranslationKey().equalsIgnoreCase("block.minecraft.air") &&
                    airnxt.equals(bp)) {

                if (dir == null)
                    return blockHit.getSide();
                if (dir == blockHit.getSide())
                    return dir;
            }
        }
        return null;
    }

    public BlockPos sideToBlock(Direction d, BlockPos blockPos) {
        switch (d) {
            case UP:
                return new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
            case DOWN:
                return new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ());
            case NORTH:
                return new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() - 1);
            case SOUTH:
                return new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 1);
            case EAST:
                return new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ());
            case WEST:
                return new BlockPos(blockPos.getX() - 1, blockPos.getY(), blockPos.getZ());

            default:
                return null;
        }
    }
}
