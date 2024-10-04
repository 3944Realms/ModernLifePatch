package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.CanvasBlock;
import com.dairymoose.modernlife.core.ModernLifeClient;
import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.List;

@Mixin(CanvasBlock.class)
public class MixinCanvas {

    @OnlyIn(Dist.CLIENT)
    @Inject(method = {"appendHoverText"}, at= @At("HEAD"), cancellable = true)
    public void appendHoverText(ItemStack itemStack, BlockGetter blockReader, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        Long canvasUniqueId = null;
        if (itemStack.getTag() != null && itemStack.getTag().contains("UniqueId")) {
            list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_DESC_COMPLETED.getKey()));
            long uniqueId = itemStack.getTag().getLong("UniqueId");
            list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_UNIQUE_ID.getKey(), uniqueId));
            canvasUniqueId = uniqueId;
        } else {
            list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_DESC_EMPTY.getKey()));
        }

        int size;
        if (canvasUniqueId != null) {
            size = ModernLifeClient.getCanvasWidth(canvasUniqueId);
            int height = ModernLifeClient.getCanvasHeight(canvasUniqueId);
            list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_PIXELS_SIZE.getKey() , size, height));
        }

        if (itemStack.getTag() != null && itemStack.getTag().contains("Size")) {
            size = itemStack.getTag().getInt("Size");
            list.add(new TextComponent(""));
            if (size == 1) {
                list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_DESC_SIZE_NORMAL.getKey()));
            } else if (size == 2) {
                list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_DESC_SIZE_LARGE.getKey()));
            } else if (size == 3) {
                list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_DESC_SIZE_EXTRA_LARGE.getKey()));
            } else if (size == 4) {
                list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_DESC_SIZE_MASSIVE.getKey()));
            } else if (size == 5) {
                list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_DESC_SIZE_LARGE_SQUARE.getKey()));
            } else if (size == 6) {
                list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_DESC_SIZE_EXTRA_LARGE_SQUARE.getKey()));
            } else if (size == 7) {
                list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_DESC_SIZE_MASSIVE_SQUARE.getKey()));
            } else if (size == 0) {
                list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_DESC_SIZE_CUSTOM.getKey()));
                CompoundTag nbt = itemStack.getTag();
                DecimalFormat df = new DecimalFormat("#.##");
                String var10003;
                float xOffset;
                float yOffset;
                if (nbt.contains("BlockWidth") && nbt.contains("BlockHeight")) {
                    xOffset = nbt.getFloat("BlockWidth");
                    yOffset = nbt.getFloat("BlockHeight");
                    var10003 = df.format(xOffset);
                    list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_BLOCK_SIZE.getKey(), var10003, df.format(yOffset)));
                }

                if (nbt.contains("xOffset") && nbt.contains("yOffset")) {
                    xOffset = nbt.getFloat("xOffset");
                    yOffset = nbt.getFloat("yOffset");
                    if (xOffset != 0.0F || yOffset != 0.0F) {
                        var10003 = df.format(xOffset);
                        list.add(new TranslatableComponent(ModLangKeyValue.CANVAS_HOVER_OFFSET.getKey(), var10003, df.format(yOffset)));
                    }
                }
            }
        }
        ci.cancel();
    }
}
