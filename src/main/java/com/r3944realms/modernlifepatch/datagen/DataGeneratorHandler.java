package com.r3944realms.modernlifepatch.datagen;

import com.r3944realms.modernlifepatch.datagen.provider.ModRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;


import static com.r3944realms.modernlifepatch.ModernLifePatch.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class DataGeneratorHandler {
    @SubscribeEvent
    public void generatorDataEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(new ModRecipeProvider(generator));
    }
}
