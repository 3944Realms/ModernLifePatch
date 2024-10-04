package com.r3944realms.modernlifepatch.mixin.block.lounge;

import com.dairymoose.modernlife.blocks.SofaBlock;
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

import static com.dairymoose.modernlife.blocks.SofaBlock.FACING;
import static com.dairymoose.modernlife.blocks.SofaBlock.TYPE;

@Mixin(SofaBlock.class)
public class MixinSofa {
    @Unique
    private static final VoxelShape SHAPE_N_SINGLE = Stream.of(
            Block.box(0, 2, 13, 16, 17, 16),
            Block.box(0, 2, 0, 3, 11, 13),
            Block.box(13, 2, 0, 16, 11, 13),
            Block.box(3, 2, 0, 13, 6, 13),
            Block.box(3, 6, 3, 13, 8, 13),
            Block.box(0, 0, 0, 16, 2, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), SHAPE_W_SINGLE, SHAPE_S_SINGLE, SHAPE_E_SINGLE, SHAPE_S_MIDDLE, SHAPE_W_MIDDLE, SHAPE_N_MIDDLE, SHAPE_E_MIDDLE, SHAPE_S_LEFT, SHAPE_W_LEFT, SHAPE_N_LEFT, SHAPE_E_LEFT, SHAPE_S_RIGHT, SHAPE_W_RIGHT, SHAPE_N_RIGHT, SHAPE_E_RIGHT, SHAPE_S_CORNER, SHAPE_W_CORNER, SHAPE_N_CORNER, SHAPE_E_CORNER;
    static {
        SHAPE_E_SINGLE = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N_SINGLE);
        SHAPE_S_SINGLE = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N_SINGLE);
        SHAPE_W_SINGLE = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S_SINGLE);
        SHAPE_N_MIDDLE = Stream.of(
                Block.box(0, 2, 13, 16, 17, 16),
                Block.box(0, 2, 0, 16, 6, 13),
                Block.box(0, 6, 3, 16, 8, 13),
                Block.box(0, 0, 0, 16, 2, 16)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        SHAPE_E_MIDDLE = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N_MIDDLE);
        SHAPE_S_MIDDLE = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_E_MIDDLE);
        SHAPE_W_MIDDLE = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S_MIDDLE);
        SHAPE_N_LEFT = Stream.of(
                Block.box(0, 2, 13, 16, 17, 16),
                Block.box(13, 2, 0, 16, 11, 13),
                Block.box(0, 2, 0, 13, 6, 13),
                Block.box(0, 6, 3, 13, 8, 13),
                Block.box(0, 0, 0, 16, 2, 16)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        SHAPE_E_LEFT = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N_LEFT);
        SHAPE_S_LEFT = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_E_LEFT);
        SHAPE_W_LEFT = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S_LEFT);
        SHAPE_N_RIGHT = Stream.of(
                Block.box(0, 2, 13, 16, 17, 16),
                Block.box(0, 2, 0, 3, 11, 13),
                Block.box(3, 2, 0, 16, 6, 13),
                Block.box(3, 6, 3, 16, 8, 13),
                Block.box(0, 0, 0, 16, 2, 16)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        SHAPE_E_RIGHT = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N_RIGHT);
        SHAPE_S_RIGHT = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_E_RIGHT);
        SHAPE_W_RIGHT = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S_RIGHT);
        SHAPE_N_CORNER = Stream.of(
                Block.box(0, 2, 13, 16, 17, 16),
                Block.box(13, 2, 0, 16, 17, 13),
                Block.box(0, 2, 0, 13, 6, 13),
                Block.box(0, 6, 3, 13, 8, 13),
                Block.box(3, 6, 0, 13, 8, 3),
                Block.box(0, 0, 0, 16, 2, 16)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        SHAPE_E_CORNER = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N_CORNER);
        SHAPE_S_CORNER = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_E_CORNER);
        SHAPE_W_CORNER = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S_CORNER);
    }
    @Inject(method = {"getShape"}, at = @At("HEAD"), cancellable = true)
    public void getShape(BlockState bs, BlockGetter reader, BlockPos pos, CollisionContext sel, CallbackInfoReturnable<VoxelShape> cir) {
        if (bs.getValue(TYPE) == SofaBlock.SofaType.single) {
            if (MLP$SwitchInner(bs, cir, SHAPE_N_SINGLE, SHAPE_S_SINGLE, SHAPE_E_SINGLE, SHAPE_W_SINGLE)) return;
        } else if (bs.getValue(TYPE) == SofaBlock.SofaType.middle) {
            if (MLP$SwitchInner(bs, cir, SHAPE_N_MIDDLE, SHAPE_S_MIDDLE, SHAPE_E_MIDDLE, SHAPE_W_MIDDLE)) return;
        } else if (bs.getValue(TYPE) == SofaBlock.SofaType.left) {
            if (MLP$SwitchInner(bs, cir, SHAPE_N_LEFT, SHAPE_S_LEFT, SHAPE_E_LEFT, SHAPE_W_LEFT)) return;
        } else if (bs.getValue(TYPE) == SofaBlock.SofaType.right) {
            if (MLP$SwitchInner(bs, cir, SHAPE_N_RIGHT, SHAPE_S_RIGHT, SHAPE_E_RIGHT, SHAPE_W_RIGHT)) return;
        } else if (bs.getValue(TYPE) == SofaBlock.SofaType.corner) {
            if (MLP$SwitchInner(bs, cir, SHAPE_N_CORNER, SHAPE_S_CORNER, SHAPE_E_CORNER, SHAPE_W_CORNER)) return;
        }
        cir.setReturnValue(SHAPE_N_SINGLE);
    }

    @Unique
    private boolean MLP$SwitchInner(BlockState bs, CallbackInfoReturnable<VoxelShape> cir, VoxelShape shapeNRight, VoxelShape shapeSRight, VoxelShape shapeERight, VoxelShape shapeWRight) {
        switch (bs.getValue(FACING)) {
            case NORTH -> {
                cir.setReturnValue(shapeNRight);
                return true;
            }
            case SOUTH -> {
                cir.setReturnValue(shapeSRight);
                return true;
            }
            case EAST -> {
                cir.setReturnValue(shapeERight);
                return true;
            }
            case WEST -> {
                cir.setReturnValue(shapeWRight);
                return true;
            }
        }
        return false;
    }
    //咕咕咕~
}
