package com.r3944realms.modernlifepatch.datagen;

import com.r3944realms.modernlifepatch.datagen.provider.ModLanguageProvider;
import com.r3944realms.modernlifepatch.utils.Enum.LanguageEnum;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;


import static com.r3944realms.modernlifepatch.ModernLifePatch.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneratorHandler {
    @SubscribeEvent
    public static void generatorDataEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new ModLanguageProvider(generator, MOD_ID, LanguageEnum.English));
        generator.addProvider(new ModLanguageProvider(generator, MOD_ID, LanguageEnum.SimpleChinese));
    }
}
