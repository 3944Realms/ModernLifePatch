package com.r3944realms.modernlifepatch;

import com.dairymoose.modernlife.core.CustomBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathResourcePack;

import java.nio.file.Path;


public abstract class EventHandler {
    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = ModernLifePatch.MOD_ID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD ,value = Dist.CLIENT)
    public static class ModClient extends EventHandler {
        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(CustomBlocks.BLOCK_ACACIA_CHAIR.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(CustomBlocks.BLOCK_BIRCH_CHAIR.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(CustomBlocks.BLOCK_DARK_OAK_CHAIR.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(CustomBlocks.BLOCK_JUNGLE_CHAIR.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(CustomBlocks.BLOCK_OAK_CHAIR.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(CustomBlocks.BLOCK_SPRUCE_CHAIR.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(CustomBlocks.BLOCK_CRIMSON_CHAIR.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(CustomBlocks.BLOCK_WARPED_CHAIR.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(CustomBlocks.BLOCK_POWER_RECEIVER.get(), RenderType.translucent());
            });
        }
    }
    @Mod.EventBusSubscriber(modid = ModernLifePatch.MOD_ID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBoth extends EventHandler {
        @SubscribeEvent
        public static void onRegisterResourcePack(AddPackFindersEvent event) {
                IModFile modFile = ModList.get().getModFileById(ModernLifePatch.MOD_ID).getFile();
                Path modFilePath = modFile.findResource("resourcepacks/new_modern_life");
                event.addRepositorySource((consumer, packConstructor) -> {
                    Pack pack = Pack.create(
                            new ResourceLocation(ModernLifePatch.MOD_ID, "new_modern_life").toString(),
                            true,
                            () -> new PathResourcePack(ModernLifePatch.MOD_ID, modFilePath),
                            packConstructor,
                            Pack.Position.TOP,
                            PackSource.BUILT_IN
                    );
                    if(pack != null) consumer.accept(pack);
                });
        }
    }
}
