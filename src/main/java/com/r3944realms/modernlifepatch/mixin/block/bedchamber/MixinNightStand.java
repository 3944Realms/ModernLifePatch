package com.r3944realms.modernlifepatch.mixin.block.bedchamber;

import com.dairymoose.modernlife.blocks.NightStandBlock;
import com.dairymoose.modernlife.tileentities.NightStandBlockEntity;
import com.dairymoose.modernlife.util.ModernLifeUtil;
import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import com.r3944realms.modernlifepatch.modInterface.IContainMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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

import static com.dairymoose.modernlife.blocks.NightStandBlock.FACING;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(NightStandBlock.class)
public abstract class MixinNightStand extends Block implements EntityBlock ,IContainMenu{
    public MixinNightStand(Properties properties) {
        super(properties);
    }
    @Unique
    private static final VoxelShape SHAPE_S = Shapes.join(Stream.of(
            Block.box(0, 0, 12, 2, 1, 14),
            Block.box(14, 0, 12, 16, 1, 14),
            Block.box(0, 0, 0, 2, 1, 2),
            Block.box(14, 0, 0, 16, 1, 2)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), Block.box(0, 1, 0, 16, 16, 14), BooleanOp.OR), SHAPE_E, SHAPE_N, SHAPE_W;
    static {
        SHAPE_W = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_S);
        SHAPE_N = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_W);
        SHAPE_E = ModernLifeUtil.RotateVoxelShapeClockwise(SHAPE_N);
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

    @Inject(method = {"getMenuProvider"}, at = @At("HEAD"), cancellable = true)
    public void getMenuProvider(BlockState blockState, Level level, BlockPos blockPos, CallbackInfoReturnable<MenuProvider> cir) {
        if (!MixinNightStand.this.ContainMenuLabel().isEmpty()) {
            cir.setReturnValue(new MenuProvider() {
                @Override
                public AbstractContainerMenu createMenu(int paramInt, @NotNull Inventory paramInventory, @NotNull Player paramPlayer) {
                    BlockEntity tileEntity = level.getBlockEntity(blockPos);
                    if (tileEntity instanceof NightStandBlockEntity nightStandBlockEntity) {
                        return new ChestMenu(MenuType.GENERIC_9x2, paramInt, paramInventory, nightStandBlockEntity, 2);
                    } else {
                        return null;
                    }
                }

                @Override
                public @NotNull Component getDisplayName() {
                    return new TranslatableComponent(String.valueOf(MixinNightStand.this.ContainMenuLabel()));
                }
            });
        }
    }
    @Unique
    @Override
    public String ContainMenuLabel() {
        return ModLangKeyValue.NIGHT_STAND_MENU_LABEL.getKey();
    }

    @Inject(method = {"appendHoverText"}, at= @At("HEAD"), cancellable = true)
    private void appendHoverTextHead(ItemStack itemStack, BlockGetter blockReader, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        list.add(new TranslatableComponent(ModLangKeyValue.STORE_18_HOVER.getKey()));
        ci.cancel();
    }
}
