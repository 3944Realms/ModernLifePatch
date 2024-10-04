package com.r3944realms.modernlifepatch.mixin.block.kitchen;

import com.dairymoose.modernlife.blocks.KitchenSinkBlock;
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

@Mixin(KitchenSinkBlock.class)
public class MixinKitchenSink extends StandardHorizontalBlock {

    //这个居然是反的（
    public MixinKitchenSink(Properties properties) {
        super(properties);
    }
    @Unique
    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(4, 3, 0, 12, 5, 3),
            Block.box(5, 5, 1, 6, 6, 2),
            Block.box(10, 5, 1, 11, 6, 2),
            Block.box(7, 5, 0, 9, 8, 2),
            Block.box(7, 8, 0, 9, 10, 7),
            Block.box(7, 7, 5, 9, 8, 7),
            Block.box(0, 0, 13, 16, 3, 16),
            Block.box(0, 0, 0, 16, 3, 3),
            Block.box(0, 0, 3, 3, 3, 13),
            Block.box(13, 0, 3, 16, 3, 13),
            Block.box(3, 0, 3, 13, 1, 13)
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
