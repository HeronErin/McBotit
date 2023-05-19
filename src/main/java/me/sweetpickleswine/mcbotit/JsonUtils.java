package me.sweetpickleswine.mcbotit;

import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import net.minecraft.item.ItemStack;


public class JsonUtils {
    public static JSONObject itemToJson(ItemStack is){
        JSONObject ret = new JSONObject();
        ret.put("id", is.getItem().getRegistryEntry().value().toString());
        ret.put("count", is.getCount());
        if(is.hasCustomName()){

            ret.put("custom_name", is.getName().toString());
        }
        if(is.hasNbt()){

//            Base64.getEncoder().encode(is.getNbt().by)
        }

        return ret;
    }
}
