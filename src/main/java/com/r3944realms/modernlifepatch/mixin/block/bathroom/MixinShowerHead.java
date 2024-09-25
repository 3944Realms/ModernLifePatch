package com.r3944realms.modernlifepatch.mixin.block.bathroom;

import com.dairymoose.modernlife.blocks.AbstractWallBlock;
import com.dairymoose.modernlife.blocks.ShowerHeadBlock;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(ShowerHeadBlock.class)
public abstract class MixinShowerHead extends AbstractWallBlock implements EntityBlock {
    @Unique
    private static final VoxelShape SHAPE_N = Block.box(6, 8.5, 10, 10, 12.5, 16), SHAPE_E, SHAPE_S, SHAPE_W;
    static {
        SHAPE_E = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N);
        SHAPE_S = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_E);
        SHAPE_W = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S);
    }

    public MixinShowerHead(Properties p_i241196_1_) {
        super(p_i241196_1_);
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
