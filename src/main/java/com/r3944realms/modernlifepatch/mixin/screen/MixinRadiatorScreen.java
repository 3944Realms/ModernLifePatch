package com.r3944realms.modernlifepatch.mixin.screen;

import com.dairymoose.modernlife.blocks.gui.RadiatorScreen;
import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RadiatorScreen.class)
public class MixinRadiatorScreen extends Screen {
    protected MixinRadiatorScreen(Component component) {
        super(component);
    }

    @ModifyArg(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;<init>(Lnet/minecraft/network/chat/Component;)V"), index = 0)
    private static Component onInit(Component component) {
        return new TranslatableComponent(ModLangKeyValue.STEAM_RADIATOR_MENU_LABEL.getKey());
    }
}
