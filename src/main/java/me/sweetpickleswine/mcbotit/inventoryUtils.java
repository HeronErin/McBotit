package me.sweetpickleswine.mcbotit;

import baritone.api.BaritoneAPI;
import baritone.api.utils.IPlayerController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.slot.SlotActionType;

public class inventoryUtils {

    public static void swapSlots(int slot1, int slot2, long delay) {
        int id = MinecraftClient.getInstance().player.currentScreenHandler.syncId;
        try {
            boolean nxt = MinecraftClient.getInstance().player.currentScreenHandler.getSlot(slot2).hasStack();
            MinecraftClient.getInstance().interactionManager.clickSlot(id, (slot1), 0, SlotActionType.PICKUP, MinecraftClient.getInstance().player);
            Thread.sleep(delay);

            MinecraftClient.getInstance().interactionManager.clickSlot(id, (slot2), 0, SlotActionType.PICKUP, MinecraftClient.getInstance().player);


            if (nxt) {
                Thread.sleep(delay);
                MinecraftClient.getInstance().interactionManager.clickSlot(id, (slot1), 0, SlotActionType.PICKUP, MinecraftClient.getInstance().player);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
