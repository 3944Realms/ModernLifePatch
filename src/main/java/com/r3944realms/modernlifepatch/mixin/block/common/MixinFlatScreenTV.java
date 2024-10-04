package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.FlatScreenTvBlock;
import com.dairymoose.modernlife.blocks.StandardHorizontalBlock;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlatScreenTvBlock.class)
public class MixinFlatScreenTV extends StandardHorizontalBlock {
    public MixinFlatScreenTV(Properties properties) {
        super(properties);
    }
    @Unique
    private static final VoxelShape SHAPE_N = Block.box(-11, 0, 6, 27, 23, 9), SHAPE_E, SHAPE_S, SHAPE_W;
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
