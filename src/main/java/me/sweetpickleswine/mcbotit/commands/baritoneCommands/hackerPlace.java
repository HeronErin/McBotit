package me.sweetpickleswine.mcbotit.commands.baritoneCommands;

import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.json.JSONObject;

public class hackerPlace extends BaseCommand {
    public static Vec3d getEyesPos()
    {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        return new Vec3d(player.getX(),
                player.getY() + player.getEyeHeight(player.getPose()),
                player.getZ());
    }
    public static Rotation getNeededRotations(Vec3d vec)
    {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new Rotation(yaw, pitch);
    }


    @Override
    public void onExec(Client c, JSONObject job) {

        BlockPos pos = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));
        MinecraftClient.getInstance().player.getInventory().selectedSlot = job.getInt("slot");
        Vec3d eyesPos = new Vec3d(MinecraftClient.getInstance().player.getX(),
                MinecraftClient.getInstance().player.getY() + MinecraftClient.getInstance().player.getEyeHeight(MinecraftClient.getInstance().player.getPose()),
                MinecraftClient.getInstance().player.getZ());


        try {Thread.sleep(50);} catch (InterruptedException e) {throw new RuntimeException(e);}
        MinecraftClient.getInstance().execute(()-> {
            if (!(!MinecraftClient.getInstance().world.getBlockState(pos).getBlock().getTranslationKey().equals("block.minecraft.air") ||
                    MinecraftClient.getInstance().player.getInventory().getMainHandStack().getItem().getTranslationKey().equals("block.minecraft.air"))) {
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
                    Rotation toRot = getNeededRotations(hitVec);
                    PlayerMoveC2SPacket.LookAndOnGround packet =
                            new PlayerMoveC2SPacket.LookAndOnGround(toRot.getYaw(),
                                    toRot.getPitch(), MinecraftClient.getInstance().player.isOnGround());
                    MinecraftClient.getInstance().player.networkHandler.sendPacket(packet);

                    MinecraftClient.getInstance().interactionManager.interactBlock(MinecraftClient.getInstance().player, Hand.MAIN_HAND,
                            new BlockHitResult(hitVec, side, pos, false));

                    MinecraftClient.getInstance().interactionManager.interactItem(MinecraftClient.getInstance().player, Hand.MAIN_HAND);
                    MinecraftClient.getInstance().player.swingHand(Hand.MAIN_HAND);
                    return;

                }
            }


        });


    }
}
