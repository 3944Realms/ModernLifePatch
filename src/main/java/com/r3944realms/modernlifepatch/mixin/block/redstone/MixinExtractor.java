package com.r3944realms.modernlifepatch.mixin.block.redstone;

import com.dairymoose.modernlife.blocks.ExtractorBlock;
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

@Mixin(ExtractorBlock.class)
public class MixinExtractor {
    @Unique
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(5, 4, 2, 11, 10, 14),
            Block.box(3, 2, 14, 13, 12, 17),
            Block.box(3, 2, -1, 13, 12, 2),
            Block.box(4, 3, 4, 12, 11, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), SHAPE_E;
    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    public void getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_, CallbackInfoReturnable<VoxelShape> cir) {
        switch (p_220053_1_.getValue(ExtractorBlock.FACING)) {
            case EAST,WEST -> cir.setReturnValue(SHAPE_E);
            default -> cir.setReturnValue(SHAPE_N);
        }
    }
    static {
        SHAPE_E = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N);
    }
}
