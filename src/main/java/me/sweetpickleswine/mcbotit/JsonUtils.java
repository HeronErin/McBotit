package me.sweetpickleswine.mcbotit;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import org.json.JSONObject;

import java.util.Base64;


public class JsonUtils {
    public static JSONObject itemToJson(ItemStack is){
        JSONObject ret = new JSONObject();
        ret.put("id", is.getItem().getRegistryEntry().value());
        ret.put("count", is.getCount());
        if(is.hasCustomName()){
            ret.put("custom_name", is.getName());
        }
        if(is.hasNbt()){

//            Base64.getEncoder().encode(is.getNbt().by)
        }

        return ret;
    }
}
