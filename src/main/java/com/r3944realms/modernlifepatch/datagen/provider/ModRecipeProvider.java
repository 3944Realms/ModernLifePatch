package com.r3944realms.modernlifepatch.datagen.provider;

import com.dairymoose.modernlife.core.CustomBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(CustomBlocks.ITEM_BULLET.get(), 4)
                .requires(Items.COPPER_INGOT, 1)
                .requires(Items.GUNPOWDER, 1)
                .unlockedBy("has_gunpowder", has(Items.GUNPOWDER))
                .save(consumer);
    }
}
