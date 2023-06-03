package me.sweetpickleswine.mcbotit.mixin;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AnvilScreen.class)
public interface AnvilScreenAccessor {
    @Invoker("onRenamed")
    void onRenamedInvoker(String x);

    @Accessor("nameField")
    TextFieldWidget getNameField();
}
