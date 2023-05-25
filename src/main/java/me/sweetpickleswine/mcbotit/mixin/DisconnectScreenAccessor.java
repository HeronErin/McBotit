package me.sweetpickleswine.mcbotit.mixin;

import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DisconnectedScreen.class)
public interface DisconnectScreenAccessor {
    @Accessor("reason")
    public Text getReason();
}
