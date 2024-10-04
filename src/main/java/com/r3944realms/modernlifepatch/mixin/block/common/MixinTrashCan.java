package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.inventory.container.TrashCanContainer;
import com.dairymoose.modernlife.blocks.TrashCanBlock;
import com.dairymoose.modernlife.tileentities.TrashCanBlockEntity;
import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Stream;

@Mixin(TrashCanBlock.class)
public abstract class MixinTrashCan extends Block implements EntityBlock {
    public MixinTrashCan(Properties properties) {
        super(properties);
    }
    @Unique
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(6, 17, 7.5, 10, 18, 8.5),
            Block.box(2, 13, 2, 14, 16, 14),
            Block.box(3, 0, 3, 13, 13, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    @Inject(method = {"getShape"}, at= @At("HEAD"), cancellable = true)
    public void getShape(BlockState bs, BlockGetter reader, BlockPos pos, CollisionContext sel, CallbackInfoReturnable<VoxelShape> cir) {
        cir.setReturnValue(SHAPE_N);
    }
    @Inject(method = {"getMenuProvider"}, at = @At("HEAD"), cancellable = true)
    public void getMenuProvider(BlockState blockState, Level level, BlockPos blockPos, CallbackInfoReturnable<MenuProvider> cir) {
        cir.setReturnValue(new MenuProvider() {
            public AbstractContainerMenu createMenu(int paramInt, @NotNull Inventory paramInventory, @NotNull Player paramPlayer) {
                BlockEntity tileEntity = level.getBlockEntity(blockPos);
                if (tileEntity instanceof TrashCanBlockEntity trashCanBlockEntity) {
                    return new TrashCanContainer(paramInt, paramInventory, trashCanBlockEntity);
                } else {
                    return null;
                }
            }

            @Override
            public @NotNull Component getDisplayName() {
                return new TranslatableComponent(ModLangKeyValue.TRASH_CAN_MENU_LABEL.getKey());
            }
        });
    }
    @Inject(method = {"appendHoverText"}, at= @At("HEAD"), cancellable = true)
    public void appendHoverText(ItemStack itemStack, BlockGetter blockReader, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        list.add(new TranslatableComponent(ModLangKeyValue.TRASH_CAN_HOVER.getKey()));
        ci.cancel();
    }
}
