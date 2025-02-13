package com.tristankechlo.toolleveling.config;

import com.tristankechlo.toolleveling.ToolLeveling;
import com.tristankechlo.toolleveling.config.util.AbstractConfig;
import com.tristankechlo.toolleveling.config.values.AbstractConfigValue;
import com.tristankechlo.toolleveling.config.values.IngredientValue;
import com.tristankechlo.toolleveling.config.values.NumberValue;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public final class ToolLevelingConfig extends AbstractConfig {

    // bounds for the success chance of the enchantment process
    private final NumberValue<Float> minSuccessChance;
    private final NumberValue<Float> maxSuccessChance;
    private final NumberValue<Integer> requiredBookshelves;
    private final NumberValue<Integer> requiredBooks;
    private final IngredientValue bonusItemMoreEnchantments;
    private final IngredientValue bonusItemMoreLevels;
    private final List<AbstractConfigValue<?>> values;
    public static final ToolLevelingConfig INSTANCE = new ToolLevelingConfig();

    private ToolLevelingConfig() {
        super("tool_leveling_table.json", ToolLeveling.CONFIG_INFO_GENERAL);

        minSuccessChance = new NumberValue<>("min_success_chance", 75.0F, 0.0F, 100.0F, GsonHelper::getAsFloat);
        maxSuccessChance = new NumberValue<>("max_success_chance", 100.0F, 0.0F, 100.0F, GsonHelper::getAsFloat);
        requiredBookshelves = new NumberValue<>("required_bookshelves", 20, 0, 32, GsonHelper::getAsInt);
        requiredBooks = new NumberValue<>("required_books", 4, 1, 6, GsonHelper::getAsInt);
        bonusItemMoreEnchantments = new IngredientValue("bonus_item_more_enchantments", Ingredient.of(Items.NETHER_STAR));
        bonusItemMoreLevels = new IngredientValue("bonus_item_more_levels", Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE));

        values = List.of(minSuccessChance, maxSuccessChance, requiredBookshelves, requiredBooks, bonusItemMoreEnchantments, bonusItemMoreLevels);
    }

    @Override
    protected List<AbstractConfigValue<?>> getValues() {
        return values;
    }

    @Override
    protected String getComment() {
        return "Checkout '" + ToolLeveling.CONFIG_INFO_GENERAL + "' for more information about this config";
    }

    public float minSuccessChance() {
        return minSuccessChance.get();
    }

    public float maxSuccessChance() {
        return maxSuccessChance.get();
    }

    public int requiredBookshelves() {
        return requiredBookshelves.get();
    }

    public int requiredBooks() {
        return requiredBooks.get();
    }

    public boolean isBonusItemStrength(ItemStack stack) {
        return bonusItemMoreLevels.get().test(stack);
    }

    public boolean isBonusItemIterations(ItemStack stack) {
        return bonusItemMoreEnchantments.get().test(stack);
    }

}
