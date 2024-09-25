package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.TrashCanBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
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

@Mixin(TrashCanBlock.class)
public abstract class MixinTrashCan extends Block implements EntityBlock {
    @Unique
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(6, 17, 7.5, 10, 18, 8.5),
            Block.box(2, 13, 2, 14, 16, 14),
            Block.box(3, 0, 3, 13, 13, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public MixinTrashCan(Properties p_49795_) {
        super(p_49795_);
    }

    @Inject(method = {"getShape"}, at= @At("HEAD"), cancellable = true)
    public void getShape(BlockState bs, BlockGetter reader, BlockPos pos, CollisionContext sel, CallbackInfoReturnable<VoxelShape> cir) {
        cir.setReturnValue(SHAPE_N);
    }
}
