package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.CCTVCameraBlock;
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

import static com.dairymoose.modernlife.blocks.CCTVCameraBlock.FACING;

@Mixin(CCTVCameraBlock.class)
public class MixinCCTVCamera {
    @Unique
    private static final VoxelShape SHAPE_S = Stream.of(
            Stream.of(
                    Block.box(7, 7, 1.00138, 9, 8, 4.00138),
                    Block.box(7, 4, 1.00138, 7, 7, 5.00138),
                    Block.box(9, 4, 1.00138, 9, 7, 5.00138)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Block.box(6, 4, 0.00138, 10, 9, 1.00138),
                Stream.of(
                    Block.box(6, 7, 3.00138, 10, 11, 9.00138),
                    Block.box(6, 9, 9.00138, 6, 11, 11.00138),
                    Block.box(10, 9, 9.00138, 10, 11, 11.00138),
                    Block.box(6, 11, 9.00138, 10, 11, 11.00138)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), SHAPE_E, SHAPE_N, SHAPE_W;
    static {
        SHAPE_W = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S);
        SHAPE_N = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_W);
        SHAPE_E = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N);
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
}
