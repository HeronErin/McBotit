package me.sweetpickleswine.mcbotit.mixin;


import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.ChatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatMixin {
    @Inject(at = @At("HEAD"), method = "sendMessage", cancellable = true)
    private void sendMessage(String msg, boolean addToHistory, CallbackInfoReturnable info) {
        if (msg.startsWith("$")){
            String submsg = msg.substring(1);
            if (submsg.equalsIgnoreCase("help")){
                ChatUtil.sendMessage("§6McBotIt commands:");
                ChatUtil.sendMessage("> $help: Displays this text");
                Bin.instance.commands.forEach((key, desc)->{
                    ChatUtil.sendMessage("> $"+key+": "+desc);
                });

            }else if(Bin.instance.commands.containsKey(submsg.split(" ")[0])){
                Bin.instance.usedClientCommands.add(submsg);
            }
            else{
                ChatUtil.sendMessage("§4Command not found");
            }

            MinecraftClient.getInstance().inGameHud.getChatHud().addToMessageHistory(msg);
            info.setReturnValue(true);
            info.cancel();
        }


    }
}
