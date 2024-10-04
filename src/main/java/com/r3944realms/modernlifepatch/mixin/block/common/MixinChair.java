package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.ChairBlock;
import com.dairymoose.modernlife.blocks.StandardHorizontalBlock;
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

@Mixin(ChairBlock.class)
public class MixinChair extends StandardHorizontalBlock {
    public MixinChair(Properties properties) {
        super(properties);
    }
    @Unique
    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(2, 7, 2, 14, 10, 14),
            Block.box(4, 10, 13, 12, 25, 13),
            Block.box(2, 10, 12, 4, 24, 14),
            Block.box(12, 10, 12, 14, 24, 14),
            Block.box(4, 2, 3, 12, 4, 3),
            Block.box(4, 2, 13, 12, 4, 13),
            Block.box(13, 2, 4, 13, 4, 12),
            Block.box(3, 2, 4, 3, 4, 12),
            Block.box(12, 0, 2, 14, 7, 4),
            Block.box(2, 0, 2, 4, 7, 4),
            Block.box(2, 0, 12, 4, 7, 14),
            Block.box(12, 0, 12, 14, 7, 14)
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
