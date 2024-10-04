package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.PrinterBlock;
import com.dairymoose.modernlife.tileentities.PrinterBlockEntity;
import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PrinterBlock.class)
public class MixinPrinter {
    @Inject(method = {"getMenuProvider"}, at = @At("HEAD"), cancellable = true)
    public void getMenuProvider(BlockState blockState, Level level, BlockPos blockPos, CallbackInfoReturnable<MenuProvider> cir) {
        cir.setReturnValue(new MenuProvider() {
            public AbstractContainerMenu createMenu(int paramInt, @NotNull Inventory paramInventory, @NotNull Player paramPlayer) {
                BlockEntity tileEntity = level.getBlockEntity(blockPos);
                if (tileEntity instanceof PrinterBlockEntity printerBlockEntity) {
                    return new ChestMenu(MenuType.GENERIC_9x3, paramInt, paramInventory, printerBlockEntity, 3);
                } else {
                    return null;
                }
            }
            @Override
            public @NotNull Component getDisplayName() {
                return new TranslatableComponent(ModLangKeyValue.PRINTER_MENU_LABEL.getKey());
            }
        });

    }
}
