package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.LightBulbBlock;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LightBulbBlock.class)
public class MixinLightBulb extends FaceAttachedHorizontalDirectionalBlock {
    public MixinLightBulb(Properties properties) {
        super(properties);
    }
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
    @Inject(method = {"appendHoverText"}, at= @At("HEAD"), cancellable = true)
    public void appendHoverText(ItemStack itemStack, BlockGetter blockReader, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        list.add(new TranslatableComponent(ModLangKeyValue.NEED_RED_STONE_POWER_HOVER.getKey()));
        ci.cancel();
    }
}
