package com.r3944realms.modernlifepatch.mixin.screen;

import com.dairymoose.modernlife.blocks.gui.PrinterScreen;
import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PrinterScreen.class)
public class MixinPrinterScreen extends Screen {
    protected MixinPrinterScreen(Component component) {
        super(component);
    }
    @ModifyArg(method = {"<init>(Lnet/minecraft/core/BlockPos;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;<init>(Lnet/minecraft/network/chat/Component;)V"), index = 0)
    private static Component onInit(Component component) {
        return new TranslatableComponent(ModLangKeyValue.PRINTER_SIZE_MENU_LABEL.getKey());
    }

}
