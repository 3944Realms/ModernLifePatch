package com.r3944realms.modernlifepatch.mixin.block.redstone;

import com.dairymoose.modernlife.blocks.PowerTransmitterBlock;
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

@Mixin(PowerTransmitterBlock.class)
public class MixinPowerTransmitter {
    @Inject(method = {"appendHoverText"}, at = @At("HEAD"), cancellable = true)
    private void appendHoverText(ItemStack itemStack, BlockGetter blockReader, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        list.add(new TranslatableComponent(ModLangKeyValue.POWER_TRANSMITTER_FIRST.getKey()));
        list.add(new TranslatableComponent(ModLangKeyValue.POWER_RECEIVER_AND_TRANSMITTER_HOVER_SECOND.getKey()));
        ci.cancel();
    }
}
