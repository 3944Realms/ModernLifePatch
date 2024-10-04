package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.GrateBlock;
import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GrateBlock.class)
public class MixinMetalGrate {
    @Inject(method = {"appendHoverText"}, at= @At("HEAD"), cancellable = true)
    private void appendHoverTextHead(ItemStack itemStack, BlockGetter blockReader, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        list.add(new TranslatableComponent(ModLangKeyValue.METAL_GRATE_HOVER.getKey()));
        ci.cancel();
    }
}
