package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.LightBulbBlock;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
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

@Mixin(LightBulbBlock.class)
public class MixinLightBulb extends FaceAttachedHorizontalDirectionalBlock {
    @Unique
    private static final VoxelShape _SHAPE_CEILING = Shapes.join(Block.box(5, 8, 5, 11, 14, 11), Block.box(7, 13, 7, 9, 16, 9), BooleanOp.OR)
            , _SHAPE_WALL_NORTH, _SHAPE_WALL_EAST, _SHAPE_WALL_SOUTH, _SHAPE_WALL_WEST, _SHAPE_FLOOR;
    static {
        _SHAPE_WALL_NORTH = ModernLifeUtil.RotateVoxelShapeXAxis(_SHAPE_CEILING);
        _SHAPE_WALL_EAST = ModernLifeUtil.RotateVoxelShapeClockwise(_SHAPE_WALL_NORTH);
        _SHAPE_WALL_SOUTH = ModernLifeUtil.RotateVoxelShapeClockwise(_SHAPE_WALL_EAST);
        _SHAPE_WALL_WEST = ModernLifeUtil.RotateVoxelShapeClockwise(_SHAPE_WALL_SOUTH);
        _SHAPE_FLOOR = ModernLifeUtil.RotateVoxelShapeXAxis(_SHAPE_WALL_NORTH);
    }

    public MixinLightBulb(Properties p_53182_) {
        super(p_53182_);
    }

    @Inject(method = {"getShape"}, at= @At("HEAD"), cancellable = true)
    public void getShape(BlockState bs, BlockGetter reader, BlockPos pos, CollisionContext sel, CallbackInfoReturnable<VoxelShape> cir) {
        switch (bs.getValue(FACE)) {
            case WALL:
                switch (bs.getValue(FACING)) {
                    case NORTH -> cir.setReturnValue(_SHAPE_WALL_NORTH);
                    case EAST -> cir.setReturnValue(_SHAPE_WALL_EAST);
                    case WEST -> cir.setReturnValue(_SHAPE_WALL_WEST);
                    case SOUTH -> cir.setReturnValue(_SHAPE_WALL_SOUTH);
                }
                break;
            case FLOOR:
                cir.setReturnValue(_SHAPE_FLOOR);
                break;
            default:
                cir.setReturnValue(_SHAPE_CEILING);
        }
    }
}
