package me.sweetpickleswine.mcbotit.commands.inventory;

import me.sweetpickleswine.mcbotit.Client;
import me.sweetpickleswine.mcbotit.commands.BaseCommand;
import me.sweetpickleswine.mcbotit.jsonFix.JSONObject;
import me.sweetpickleswine.mcbotit.mixin.RecipeBookWidgetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.Registries;

public class quickCraft extends BaseCommand {
    @Override
    public void onExec(Client c, JSONObject job) {
        if (MinecraftClient.getInstance().currentScreen instanceof CraftingScreen){
            int id = MinecraftClient.getInstance().player.currentScreenHandler.syncId;

            for (RecipeResultCollection resultCollection : MinecraftClient.getInstance().player.getRecipeBook().getOrderedResults() ){
                for (Recipe<?> recipe : resultCollection.getAllRecipes()){
                    if (recipe.getId().toString().equalsIgnoreCase(job.getString("id"))){

                        MinecraftClient.getInstance().interactionManager.clickRecipe(id, recipe, job.job.get("full stack").getAsBoolean());

                        return;

                    }

                }
            }
        }


    }
}
