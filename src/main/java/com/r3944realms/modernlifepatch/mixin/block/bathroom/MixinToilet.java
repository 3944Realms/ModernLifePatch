package com.r3944realms.modernlifepatch.mixin.block.bathroom;

import com.dairymoose.modernlife.blocks.StandardHorizontalBlock;
import com.dairymoose.modernlife.blocks.ToiletBlock;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
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

@Mixin(ToiletBlock.class)
public abstract class MixinToilet extends StandardHorizontalBlock implements SimpleWaterloggedBlock, EntityBlock {
    @Unique
    private static final VoxelShape SHAPE_S, SHAPE_W, SHAPE_N, SHAPE_E;
    static {
        SHAPE_S = Stream.of(
                Block.box(3, 4, 0, 13, 16, 5),
                Block.box(3, 3.99, 5, 5, 7.99, 15),
                Block.box(11, 3.99, 5, 13, 7.99, 15),
                Block.box(5, 3.99, 5, 11, 7.99, 7),
                Block.box(5, 3.99, 13, 11, 7.99, 15),
                Block.box(4, -0.01, 2, 12, 3.99, 13)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        SHAPE_W = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S);
        SHAPE_N = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_W);
        SHAPE_E = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N);
    }
    public MixinToilet(Properties p_i48377_1_) {
        super(p_i48377_1_);
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
