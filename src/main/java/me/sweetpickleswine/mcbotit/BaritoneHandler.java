package me.sweetpickleswine.mcbotit;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.schematic.FillSchematic;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class BaritoneHandler {
    private static void baritoneBreakBlockHandlerUnder(BlockPos bp) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getBuilderProcess().build("break single block", new FillSchematic(1, 1, 1, Blocks.AIR.getDefaultState()), bp);
    }

    public static void baritoneBreakBlockHandler(BlockPos bp) {
        if (Bin.instance.hasBaritoneInstalled)
            baritoneBreakBlockHandlerUnder(bp);
        else
            System.err.println("Attempted to call baritone function without baritone installed");
    }
    private static void baritoneGotoUnder(int x, int y, int z){
        Bin.instance.registerAndStartThread(new Thread(() -> {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(
                    new GoalGetToBlock(
                            new BlockPos(x, y, z)));

            while (BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().isActive()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    return;
                }
            }

        }));
    }
    public static void baritoneGoto(int x, int y, int z){
        if (Bin.instance.hasBaritoneInstalled)
            baritoneGotoUnder(x, y, z);
        else
            System.err.println("Attempted to call baritone function without baritone installed");
    }

    private static void baritoneGotoWalkOnlyUnder(int x, int y, int z){
        Bin.instance.registerAndStartThread(new Thread(() -> {
            boolean allowBreak = BaritoneAPI.getSettings().allowBreak.value;
            boolean allowPlace = BaritoneAPI.getSettings().allowPlace.value;
            boolean allowSprint = BaritoneAPI.getSettings().allowSprint.value;
            BaritoneAPI.getSettings().allowBreak.value = false;
            BaritoneAPI.getSettings().allowSprint.value = false;
            BaritoneAPI.getSettings().allowPlace.value = false;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(
                    new GoalGetToBlock(
                            new BlockPos(x, y, z)));
            while (BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().isActive()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    BaritoneAPI.getSettings().allowBreak.value = allowBreak;
                    BaritoneAPI.getSettings().allowPlace.value = allowPlace;
                    BaritoneAPI.getSettings().allowSprint.value = allowSprint;
                    return;
                }
            }
            BaritoneAPI.getSettings().allowBreak.value = allowBreak;
            BaritoneAPI.getSettings().allowPlace.value = allowPlace;
            BaritoneAPI.getSettings().allowSprint.value = allowSprint;

        }));
    }
    public static void baritoneGotoWalkOnly(int x, int y, int z){
        if (Bin.instance.hasBaritoneInstalled)
            baritoneGotoWalkOnlyUnder(x, y, z);
        else
            System.err.println("Attempted to call baritone function without baritone installed");

    }

    private static void baritonePlaceBlockUnder(int x, int y, int z, String id){
        BlockPos bp = new BlockPos(x, y, z);

        Block b = Registries.BLOCK.getOrEmpty(new Identifier(id)).orElse(null);
        if (b != null) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getBuilderProcess().build("place single block", new FillSchematic(1, 1, 1, b.getDefaultState()), bp);
        }
    }
    public static void baritonePlaceBlock(int x, int y, int z, String id){
        if (Bin.instance.hasBaritoneInstalled)
            baritonePlaceBlockUnder(x, y, z, id);
        else
            System.err.println("Attempted to call baritone function without baritone installed");

    }
}
