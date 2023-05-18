package me.sweetpickleswine.mcbotit.commands.baritoneCommands;

import baritone.api.BaritoneAPI;
import baritone.api.command.datatypes.BlockById;
import baritone.api.schematic.FillSchematic;
import baritone.api.utils.RayTraceUtils;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.pathing.movement.MovementHelper;
import baritone.pathing.movement.MovementState;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.json.JSONObject;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class baritonePlaceBlock extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        BlockPos bp = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));

        Block b = Registries.BLOCK.getOrEmpty(new Identifier(job.getString("id"))).orElse(null);
        if (b!=null) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getBuilderProcess().build("place single block", new FillSchematic(1, 1, 1, b.getDefaultState()), bp);
        }

    }



}
