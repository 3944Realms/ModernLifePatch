package com.r3944realms.modernlifepatch.mixin.block.kitchen;

import com.dairymoose.modernlife.blocks.MicrowaveBlock;
import com.dairymoose.modernlife.blocks.StandardHorizontalBlock;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
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

@Mixin(MicrowaveBlock.class)
public abstract class MixinMicrowave extends StandardHorizontalBlock implements EntityBlock {
    @Unique
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(1, 8, 2, 15, 10, 14),
            Block.box(12, 2, 2, 15, 8, 14),
            Block.box(1, 0, 2, 15, 2, 14),
            Block.box(1, 2, 2, 3, 8, 14),
            Block.box(3, 2, 2, 12, 8, 5),
            Block.box(3, 2, 14, 12, 8, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), SHAPE_E, SHAPE_S, SHAPE_W,
    SHAPE_N_O = Stream.of(
            Block.box(3, 2, 2, 12, 8, 5),
            Block.box(1, 8, 2, 15, 10, 14),
            Block.box(12, 2, 2, 15, 8, 14),
            Block.box(1, 0, 2, 15, 2, 14),
            Block.box(1, 2, 2, 3, 8, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), SHAPE_E_O, SHAPE_S_O, SHAPE_W_O;
    static {
        SHAPE_E = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N);
        SHAPE_S = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_E);
        SHAPE_W = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S);
        SHAPE_E_O = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N_O);
        SHAPE_S_O = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_E_O);
        SHAPE_W_O = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S_O);
    }

    @Inject(method = {"getShape"}, at= @At("HEAD"), cancellable = true)
    public void getShape(BlockState bs, BlockGetter reader, BlockPos pos, CollisionContext sel, CallbackInfoReturnable<VoxelShape> cir) {
        boolean isOpen = bs.getValue(MicrowaveBlock.OPEN_DOOR);
        switch (bs.getValue(FACING)) {
            case SOUTH -> cir.setReturnValue(isOpen ? SHAPE_S_O :SHAPE_S);
            case EAST -> cir.setReturnValue(isOpen ? SHAPE_E_O : SHAPE_E);
            case WEST -> cir.setReturnValue(isOpen ? SHAPE_W_O : SHAPE_W);
            default -> cir.setReturnValue(isOpen? SHAPE_N_O : SHAPE_N);
        }
    }
    public MixinMicrowave(Properties properties) {
        super(properties);
    }
}
