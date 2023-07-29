package me.sweetpickleswine.mcbotit.mixin;

import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(RecipeBookWidget.class)
public interface RecipeBookWidgetAccessor {

    @Accessor("recipesArea")
    public RecipeBookResults getRecipesArea();
}
