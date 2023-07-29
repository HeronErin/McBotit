package me.sweetpickleswine.mcbotit.commands.info;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.*;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.io.DataOutputStream;
import java.io.IOException;

public class getBlock extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        BlockPos bp = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));
        BlockState b = MinecraftClient.getInstance().world.getBlockState(bp);



        NbtCompound ret = new NbtCompound();
        NbtList lt = new NbtList();
        ret.putString("id", Registries.BLOCK.getId(b.getBlock()).toString());

        if (b.getBlock() instanceof AbstractSignBlock){
            JsonArray jarr = new JsonArray();
            SignBlockEntity blockEntity = (SignBlockEntity) MinecraftClient.getInstance().world.getBlockEntity(bp);



            int i = 0;
            for (Text text : blockEntity.getText(false).getMessages(false)) {
                jarr.add(text.getString());
                i++;
            }

            ret.putString("sign json", jarr.toString());
        }

        for (Object op : b.getProperties().toArray()) {
            Property p = (Property) op;
            NbtCompound ct = new NbtCompound();
            String value = "undefined";
            if (p instanceof DirectionProperty prop) {
                value = b.get(prop).asString();
            } else if (p instanceof BooleanProperty prop) {
                value = String.valueOf(b.get(prop).booleanValue());
            } else if (p instanceof IntProperty prop) {
                value = String.valueOf(b.get(prop).intValue());
            } else if (p instanceof EnumProperty prop) {
                value = String.valueOf(b.get(prop).toString());
            }

            ct.putString("name", p.getName());
            ct.putString("type", p.getType().toString());
            ct.putString("values", value);
            lt.add(ct);
        }

        ret.put("properties", lt);
        try {
            c.writeNbt(ret);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
