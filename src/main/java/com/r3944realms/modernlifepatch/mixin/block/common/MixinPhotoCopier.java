package com.r3944realms.modernlifepatch.mixin.block.common;

import com.dairymoose.modernlife.blocks.PhotocopierBlock;
import com.dairymoose.modernlife.blocks.StandardHorizontalBlock;
import com.dairymoose.modernlife.blocks.WallSocketBlock;
import com.dairymoose.modernlife.tileentities.NightStandBlockEntity;
import com.dairymoose.modernlife.tileentities.PhotocopierBlockEntity;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import com.r3944realms.modernlifepatch.modInterface.IContainMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
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

@Mixin(PhotocopierBlock.class)
public abstract class MixinPhotoCopier extends StandardHorizontalBlock implements EntityBlock {
    public MixinPhotoCopier(Properties properties) {
        super(properties);
    }
    @Unique
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(4, 15.999, 2, 10, 15.999, 16),
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(0, 11, 0, 16, 14, 16),
            Block.box(0, 8, 13, 16, 11, 16),
            Block.box(0, 8, 2, 4, 11, 13),
            Block.box(10, 14, 2, 16, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), SHAPE_E, SHAPE_S, SHAPE_W;
    static {
        SHAPE_E = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N);
        SHAPE_S = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_E);
        SHAPE_W = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S);
    }

    @Inject(method = {"getShape"}, at= @At("HEAD"), cancellable = true)
    public void getShape(BlockState bs, BlockGetter reader, BlockPos pos, CollisionContext sel, CallbackInfoReturnable<VoxelShape> cir) {
        switch (bs.getValue(WallSocketBlock.FACING)) {
            case SOUTH -> cir.setReturnValue(SHAPE_S);
            case EAST -> cir.setReturnValue(SHAPE_E);
            case WEST -> cir.setReturnValue(SHAPE_W);
            default -> cir.setReturnValue(SHAPE_N);
        }
    }
    @Inject(method = {"getMenuProvider"}, at = @At("HEAD"), cancellable = true)
    public void getMenuProvider(BlockState blockState, Level level, BlockPos blockPos, CallbackInfoReturnable<MenuProvider> cir) {
        cir.setReturnValue(new MenuProvider() {
            @Override
            public AbstractContainerMenu createMenu(int paramInt, @NotNull Inventory paramInventory, @NotNull Player paramPlayer) {
                BlockEntity tileEntity = level.getBlockEntity(blockPos);
                if (tileEntity instanceof PhotocopierBlockEntity photocopierBlockEntity) {
                    return new ChestMenu(MenuType.GENERIC_9x3, paramInt, paramInventory, photocopierBlockEntity, 3);
                } else {
                    return null;
                }
            }

            @Override
            public @NotNull Component getDisplayName() {
                return new TranslatableComponent(ModLangKeyValue.PHOTOCOPIER_MENU_LABEL.getKey());
            }
        });
    }
    @Inject(method = {"appendHoverText"}, at= @At("HEAD"), cancellable = true)
    public void appendHoverText(ItemStack itemStack, BlockGetter blockReader, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        list.add(new TranslatableComponent(ModLangKeyValue.PHOTOCOPIER_HOVER_FIRST.getKey()));
        list.add(new TranslatableComponent(ModLangKeyValue.PHOTOCOPIER_HOVER_SECOND.getKey()));
        ci.cancel();
    }
}
