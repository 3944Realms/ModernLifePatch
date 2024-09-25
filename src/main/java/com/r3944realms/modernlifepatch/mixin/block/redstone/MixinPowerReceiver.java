package com.r3944realms.modernlifepatch.mixin.block.redstone;

import com.dairymoose.modernlife.blocks.PowerReceiverBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PowerReceiverBlock.class)
public class MixinPowerReceiver {

    @Inject(method = {"getShape"}, at= @At("HEAD"), cancellable = true)
    public void getShape(BlockState bs, BlockGetter reader, BlockPos pos, CollisionContext sel, CallbackInfoReturnable<VoxelShape> cir) {
        VoxelShape combinedShape =
                Shapes.join(
                        Block.box(5, 10, 5, 11, 16, 11),
                        Block.box(0, 0, 0, 16, 5, 16),
                        BooleanOp.OR);
        cir.setReturnValue(combinedShape);
    }
}
