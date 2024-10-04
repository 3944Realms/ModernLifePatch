package com.r3944realms.modernlifepatch.mixin.block.redstone;

import com.dairymoose.modernlife.blocks.WinchBlock;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

@Mixin(WinchBlock.class)
public abstract class MixinWinch extends FaceAttachedHorizontalDirectionalBlock implements EntityBlock {
    public MixinWinch(Properties properties) {
        super(properties);
    }
    @Unique
    private static final VoxelShape __SHAPE_FLOOR_SOUTH ,_SHAPE_FLOOR_WEST, _SHAPE_FLOOR_NORTH, _SHAPE_FLOOR_EAST, _SHAPE_WALL_SOUTH, _SHAPE_WALL_WEST, _SHAPE_WALL_NORTH, _SHAPE_WALL_EAST, _SHAPE_CEILING_SOUTH, _SHAPE_CEILING_WEST, _SHAPE_CEILING_NORTH, _SHAPE_CEILING_EAST;
    static {
        __SHAPE_FLOOR_SOUTH = Stream.of(
                Block.box(2, 5, 5, 14, 11, 11),
                Block.box(0, 4, 4, 2, 12, 12),
                Block.box(0, 0, 6, 2, 4, 10),
                Block.box(14, 0, 6, 16, 4, 10),
                Block.box(14, 4, 4, 16, 12, 12)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        _SHAPE_FLOOR_WEST = ModernLifeUtil.RotateVoxelShapeClockwise(__SHAPE_FLOOR_SOUTH);
        _SHAPE_FLOOR_NORTH = ModernLifeUtil.RotateVoxelShapeClockwise(_SHAPE_FLOOR_WEST);
        _SHAPE_FLOOR_EAST = ModernLifeUtil.RotateVoxelShapeClockwise(_SHAPE_FLOOR_NORTH);
        _SHAPE_WALL_SOUTH = ModernLifeUtil.RotateVoxelShapeXAxis(__SHAPE_FLOOR_SOUTH);
        _SHAPE_WALL_WEST = ModernLifeUtil.RotateVoxelShapeClockwise(_SHAPE_WALL_SOUTH);
        _SHAPE_WALL_NORTH = ModernLifeUtil.RotateVoxelShapeClockwise(_SHAPE_WALL_WEST);
        _SHAPE_WALL_EAST = ModernLifeUtil.RotateVoxelShapeClockwise(_SHAPE_WALL_NORTH);
        _SHAPE_CEILING_SOUTH = ModernLifeUtil.RotateVoxelShapeXAxis(_SHAPE_WALL_SOUTH);
        _SHAPE_CEILING_WEST = ModernLifeUtil.RotateVoxelShapeClockwise(_SHAPE_CEILING_SOUTH);
        _SHAPE_CEILING_NORTH = ModernLifeUtil.RotateVoxelShapeClockwise(_SHAPE_CEILING_WEST);
        _SHAPE_CEILING_EAST = ModernLifeUtil.RotateVoxelShapeClockwise(_SHAPE_CEILING_NORTH);
    }

    @Inject(method = {"getShape"}, at= @At("HEAD"), cancellable = true)
    public void getShape(BlockState bs, BlockGetter reader, BlockPos pos, CollisionContext sel, CallbackInfoReturnable<VoxelShape> cir) {
        switch ((bs.getValue(FACE))) {
            case CEILING: {
                MH$SwitchInner(bs, cir, _SHAPE_CEILING_NORTH, _SHAPE_CEILING_EAST, _SHAPE_CEILING_WEST, _SHAPE_CEILING_SOUTH);
                break;
            }
            case WALL: {
                MH$SwitchInner(bs, cir, _SHAPE_WALL_NORTH, _SHAPE_WALL_EAST, _SHAPE_WALL_WEST, _SHAPE_WALL_SOUTH);
                break;
            }
            case FLOOR: {
                MH$SwitchInner(bs, cir, _SHAPE_FLOOR_NORTH, _SHAPE_FLOOR_EAST, _SHAPE_FLOOR_WEST, __SHAPE_FLOOR_SOUTH);
                break;
            }
            default:
                cir.setReturnValue(_SHAPE_FLOOR_NORTH);
        }
    }
    @Inject(method = {"appendHoverText"}, at = @At("HEAD"), cancellable = true)
    private void appendHoverText(ItemStack itemStack, BlockGetter blockReader, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        list.add(new TranslatableComponent(ModLangKeyValue.NEED_RED_STONE_POWER_HOVER.getKey()));
        ci.cancel();
    }

    @Unique
    private void MH$SwitchInner(BlockState bs, CallbackInfoReturnable<VoxelShape> cir, VoxelShape shapeFloorNorth, VoxelShape shapeFloorEast, VoxelShape shapeFloorWest, VoxelShape shapeFloorSouth) {
        switch (bs.getValue(FACING)) {
            case NORTH -> cir.setReturnValue(shapeFloorNorth);
            case EAST -> cir.setReturnValue(shapeFloorEast);
            case WEST -> cir.setReturnValue(shapeFloorWest);
            case SOUTH -> cir.setReturnValue(shapeFloorSouth);
        }
    }

}
