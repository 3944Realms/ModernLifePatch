package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.MetalDuctBlock;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.dairymoose.modernlife.blocks.MetalDuctBlock.AXIS;

@Mixin(MetalDuctBlock.class)
public class MixinMetalDuct {
    @Unique
    private static final VoxelShape SHAPE_Y = Block.box(3, 0, 3, 13, 16, 13), SHAPE_X, SHAPE_Z;
    static {
        SHAPE_Z = ModernLifeUtil.RotateVoxelShapeXAxis(SHAPE_Y);
        SHAPE_X = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_Z);
    }

    @Inject(method = {"getShape"}, at = @At("HEAD"), cancellable = true)
    public void getShape(BlockState bs, BlockGetter reader, BlockPos pos, CollisionContext sel, CallbackInfoReturnable<VoxelShape> cir) {
        if (bs.getValue(AXIS) == Direction.Axis.X) {
            cir.setReturnValue(SHAPE_X);
        } else {
            cir.setReturnValue(bs.getValue(AXIS) == Direction.Axis.Z ? SHAPE_Z : SHAPE_Y);
        }
    }

}
