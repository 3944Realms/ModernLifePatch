package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.RadiatorBlock;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Stream;

import static com.dairymoose.modernlife.blocks.RadiatorBlock.FACING;

@Mixin(RadiatorBlock.class)
public class MixinRadiator {
    @Unique
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(1, 1, 4, 15, 3, 12),
            Block.box(1, 3, 4, 3, 13, 12),
            Block.box(4, 3, 4, 6, 13, 12),
            Block.box(7, 3, 4, 9, 13, 12),
            Block.box(10, 3, 4, 12, 13, 12),
            Block.box(13, 3, 4, 15, 13, 12),
            Block.box(1, 0, 10, 3, 1, 12),
            Block.box(1, 0, 4, 3, 1, 6),
            Block.box(13, 0, 10, 15, 1, 12),
            Block.box(13, 0, 4, 15, 1, 6),
            Block.box(3, 3, 5, 13, 12, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), SHAPE_E, SHAPE_S, SHAPE_W;
    static {
        SHAPE_E = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N);
        SHAPE_S = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_E);
        SHAPE_W = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S);
    }

    @Inject(method = {"getShape"}, at= @At("HEAD"), cancellable = true)
    public void getShape(BlockState bs, BlockGetter reader, BlockPos pos, CollisionContext sel, CallbackInfoReturnable<VoxelShape> cir) {
        switch (bs.getValue(FACING)) {
            case SOUTH -> cir.setReturnValue(SHAPE_S);
            case EAST -> cir.setReturnValue(SHAPE_E);
            case WEST -> cir.setReturnValue(SHAPE_W);
            default -> cir.setReturnValue(SHAPE_N);
        }
    }
    @Inject(method = {"appendHoverText"}, at= @At("HEAD"), cancellable = true)
    public void appendHoverText(ItemStack itemStack, BlockGetter blockReader, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        list.add(new TranslatableComponent(ModLangKeyValue.STEAM_RADIATOR_HOVER.getKey()));
        ci.cancel();
    }
}
