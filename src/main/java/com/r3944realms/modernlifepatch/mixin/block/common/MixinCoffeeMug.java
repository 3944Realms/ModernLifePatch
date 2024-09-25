package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.CoffeeMugBlock;
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

@Mixin(CoffeeMugBlock.class)
public class MixinCoffeeMug extends StandardHorizontalBlock {
    public MixinCoffeeMug(Properties p_i48377_1_) {
        super(p_i48377_1_);
    }
    @Unique
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(7, 0, 9, 9, 5, 10),
            Block.box(7, 0, 6, 9, 5, 7),
            Block.box(7, 0, 7, 9, 1, 9),
            Block.box(6, 0, 6, 7, 5, 10),
            Block.box(9, 0, 6, 10, 5, 10)
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
}
