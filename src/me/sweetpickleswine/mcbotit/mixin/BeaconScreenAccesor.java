package me.sweetpickleswine.mcbotit.mixin;

import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BeaconScreen.class)
public interface BeaconScreenAccesor {
    @Accessor("primaryEffect")
    StatusEffect getPrimaryEffect();
    @Accessor("secondaryEffect")
    StatusEffect getSecondaryEffect();

    @Accessor("primaryEffect")
    void setPrimaryEffect(StatusEffect ignored);
    @Accessor("secondaryEffect")
    void setSecondaryEffect(StatusEffect ignored);
}
