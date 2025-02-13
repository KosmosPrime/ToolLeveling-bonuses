package com.tristankechlo.toolleveling.blockentity;

import com.google.common.base.Preconditions;
import com.tristankechlo.toolleveling.ToolLeveling;
import com.tristankechlo.toolleveling.menu.ToolLevelingTableMenu;
import com.tristankechlo.toolleveling.util.Predicates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ToolLevelingTableBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    private static final Component CONTAINER_NAME = Component.translatable("container.toolleveling.tool_leveling_table");
    private NonNullList<ItemStack> items = NonNullList.withSize(NUMBER_OF_SLOTS, ItemStack.EMPTY);
    public static final int NUMBER_OF_SLOTS = 13;
    public static final int[] SLOTS = IntStream.range(1, NUMBER_OF_SLOTS).toArray();
    public static final int[] BOOK_SLOTS = IntStream.range(1, 7).toArray();
    public static final int[] BONUS_SLOTS = IntStream.range(7, NUMBER_OF_SLOTS).toArray();

    public ToolLevelingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ToolLeveling.TLT_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
    }

    @Override
    protected Component getDefaultName() {
        return CONTAINER_NAME;
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInv) {
        return new ToolLevelingTableMenu(windowId, playerInv, this, this.worldPosition);
    }

    @Override
    public int getContainerSize() {
        return NUMBER_OF_SLOTS;
    }

    @Override
    public boolean isEmpty() {
        return this.items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int index) {
        if (index < 0 || index >= this.getContainerSize()) {
            return ItemStack.EMPTY;
        }
        return this.items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.items, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack stack = this.items.get(index);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(index, ItemStack.EMPTY);
            return stack;
        }
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        this.items.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 0) {
            return Predicates.IS_UPGRADE_ITEM.test(stack);
        }
        if (index >= 1 && index <= 6) {
            return Predicates.IS_BOOK.test(stack);
        }
        if (index >= 7 && index <= 12) {
            return Predicates.IS_BONUS_ITEM.test(stack);
        }
        return false;
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public final void sync() {
        Preconditions.checkNotNull(this.level);
        if (this.level.isClientSide()) {
            throw new IllegalStateException("Cannot call sync() on the logical client!");
        }
        level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.sync();
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index != 0 && this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return this.canPlaceItemThroughFace(index, stack, direction);
    }

    public ItemStack getStackToEnchant() {
        return this.getItem(0);
    }

    public List<WeightedEntry.Wrapper<Enchantment>> getEnchantments() {
        var weights = new ArrayList<WeightedEntry.Wrapper<Enchantment>>();

        for (int i : BOOK_SLOTS) {
            var enchantments = EnchantmentHelper.getEnchantments(this.items.get(i));
            for (var entry : enchantments.entrySet()) {
                weights.add(WeightedEntry.wrap(entry.getKey(), entry.getValue()));
            }
        }

        return weights;
    }

}
