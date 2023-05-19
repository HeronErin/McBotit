package me.sweetpickleswine.mcbotit.commands.info;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.screen.*;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;

public class getOpenInventory extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        DataOutputStream dout = new DataOutputStream(c.output);
        NbtCompound ret = new NbtCompound();
        NbtList list = new NbtList();
        ScreenHandler sc = MinecraftClient.getInstance().player.currentScreenHandler;
        Screen screen = MinecraftClient.getInstance().currentScreen;
        System.out.println(sc);
        System.out.println(screen);
        ret.putString("container type", shtype(sc));

        if (screen != null)
            ret.putString("name", screen.getTitle().getString());


        for (int i = 0; i < sc.slots.size(); i++) {
            ItemStack is = sc.slots.get(i).getStack();
            NbtCompound ct =  is.hasNbt() ? is.getNbt().copy() : new NbtCompound();

            ct.putString("type", Registries.ITEM.getId(is.getItem()).toString());
            ct.putInt("count", is.getCount());
            ct.putInt("durability", is.getMaxDamage() - is.getDamage());
            ct.putInt("maxdurability", is.getMaxDamage());
            ct.putInt("id", i);
            list.add(ct);
        }
        ret.put("items", list);

        try {
            System.out.println(ret);
            NbtIo.write(ret, dout);
            GetVillagerTrades.endNbt(dout);
            dout.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static String shtype(ScreenHandler sc){
        if (sc instanceof ShulkerBoxScreenHandler)
            return "shulker";
        else if (sc instanceof CraftingScreenHandler)
            return "crafting table";
        else if (sc instanceof EnchantmentScreenHandler)
            return "enchantment table";
        else if (sc instanceof FurnaceScreenHandler)
            return "furnace";
        else if (sc instanceof HopperScreenHandler)
            return "hopper";
        else if (sc instanceof MerchantScreenHandler)
            return "villager";
        else if (sc instanceof AnvilScreenHandler)
            return "anvil";
        else if (sc instanceof BrewingStandScreenHandler)
            return "brewing stand";
        else if (sc instanceof BeaconScreenHandler)
            return "beacon";
        else if (sc instanceof BlastFurnaceScreenHandler)
            return "blast furnace";
        else if (sc instanceof GrindstoneScreenHandler)
            return "grindstone";
        else if (sc instanceof LoomScreenHandler)
            return "loom";
        else if (sc instanceof SmithingScreenHandler)
            return "smithing table";
        else if (sc instanceof SmokerScreenHandler)
            return "smoker table";
        else if (sc instanceof CartographyTableScreenHandler)
            return "cartography Table";
        else if (sc instanceof StonecutterScreenHandler)
            return "stonecutter";
        else if (sc instanceof LecternScreenHandler)
            return "lectern";
        else if (sc instanceof Generic3x3ContainerScreenHandler)
            return "3x3";
        else if (sc instanceof GenericContainerScreenHandler gc){
            return "chest-"+gc.getRows();
        }else
            return "other";
    }
}
