package com.r3944realms.modernlifepatch.mixin.block.kitchen;

import com.dairymoose.modernlife.blocks.StandardHorizontalBlock;
import com.dairymoose.modernlife.blocks.StoveBlock;
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

@Mixin(StoveBlock.class)
public abstract class MixinStove extends StandardHorizontalBlock implements EntityBlock {
    public MixinStove(Properties properties) {
        super(properties);
    }
    @Unique
    private static final VoxelShape SHAPE_N = Stream.of(
            Stream.of(
                    Block.box(0, 0, 15, 16, 2, 16),
                    Block.box(0, 8, 15, 16, 10, 16),
                    Block.box(13, 2, 15, 16, 8, 16),
                    Block.box(0, 2, 15, 3, 8, 16),
                    Block.box(3, 2, 16, 13, 8, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(0, 0, 0, 16, 2, 15),
            Block.box(0, 9, 0, 16, 13, 15),
            Block.box(0, 13, 0, 16, 16, 2),
            Block.box(0, 10, 15, 16, 13, 16),
            Block.box(3, 2, 0, 13, 9, 3),
            Block.box(13, 2, 0, 16, 9, 15),
            Block.box(0, 2, 0, 3, 9, 15),
            Block.box(3, 6, 3, 13, 6, 14),
            Block.box(3, 3, 3, 13, 3, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), SHAPE_E, SHAPE_S, SHAPE_W,
            SHAPE_N_O = Stream.of(
                    Block.box(0, 0, 0, 16, 2, 15),
                    Block.box(0, 9, 0, 16, 13, 15),
                    Block.box(0, 13, 0, 16, 16, 2),
                    Block.box(0, 10, 15, 16, 13, 16),
                    Block.box(3, 2, 0, 13, 9, 3),
                    Block.box(13, 2, 0, 16, 9, 15),
                    Block.box(0, 2, 0, 3, 9, 15)
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
        boolean isOpen = bs.getValue(StoveBlock.OPEN_DOOR);
        switch (bs.getValue(FACING)) {
            case SOUTH -> cir.setReturnValue(isOpen ? SHAPE_S_O : SHAPE_S);
            case EAST -> cir.setReturnValue(isOpen ? SHAPE_E_O : SHAPE_E);
            case WEST -> cir.setReturnValue(isOpen ? SHAPE_W_O : SHAPE_W);
            default -> cir.setReturnValue(isOpen ? SHAPE_N_O : SHAPE_N);
        }
    }

}
