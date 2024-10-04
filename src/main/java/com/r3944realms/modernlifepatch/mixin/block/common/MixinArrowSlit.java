package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.ArrowSlitBlock;
import com.dairymoose.modernlife.blocks.WallSocketBlock;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import net.minecraft.core.BlockPos;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(ArrowSlitBlock.class)
public class MixinArrowSlit {
    @Unique
    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(0, 0, 12, 16, 6, 16),
            Block.box(10, 6, 12, 16, 14, 16),
            Block.box(0, 14, 12, 16, 16, 16),
            Block.box(0, 6, 12, 6, 14, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), SHAPE_E, SHAPE_N, SHAPE_W;
    static {
        SHAPE_W = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S);
        SHAPE_N = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_W);
        SHAPE_E = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N);
    }

    @Inject(method = {"getShape"}, at= @At("HEAD"), cancellable = true)
    public void getShape(BlockState bs, BlockGetter reader, BlockPos pos, CollisionContext sel, CallbackInfoReturnable<VoxelShape> cir) {
        switch (bs.getValue(WallSocketBlock.FACING)) {
            case SOUTH -> cir.setReturnValue(SHAPE_S);
            case EAST -> cir.setReturnValue(SHAPE_E);
            case WEST -> cir.setReturnValue(SHAPE_W);
            default -> cir.setReturnValue(SHAPE_N);
        }
    }
}
