package com.tristankechlo.toolleveling.client;

import com.tristankechlo.toolleveling.menu.ToolLevelingTableMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Supplier;

public final class ComponentUtil {

    // components related to buttons
    public static final Component HELP_BUTTON_TEXT = make(".help.button_text");
    public static final Supplier<Tooltip> HELP_BUTTON_TOOLTIP = () -> Tooltip.create(make(".help.button_tooltip"));
    public static final Component INFO_BUTTON_TEXT = make(".percentages.button_text");
    public static final Supplier<Tooltip> INFO_BUTTON_TOOLTIP = () -> Tooltip.create(make(".percentages.button_tooltip"));
    public static final Component START_BUTTON_TEXT = make(".start_upgrade.button_text");
    public static final Supplier<Tooltip> START_BUTTON_TOOLTIP = () -> Tooltip.create(make(".start_upgrade.button_tooltip"));

    // components related to the info fields
    public static final Component TITLE_PERCENTAGES = makeTitle(".percentages.field_title");
    public static final Component TITLE_SUCCESS_CHANCE = makeTitle(".success_chance.field_title");
    public static final Component TITLE_BONUSES = makeTitle(".summary.field_title");
    public static final Component TITLE_HELP_FIELD = makeTitle(".help.field_title");

    // other stuff
    private static final String START = "screen.toolleveling.tool_leveling_table";

    public static MutableComponent make(String string) {
        return Component.translatable(START + string);
    }

    public static MutableComponent makeTitle(String str) {
        return make(str).withStyle(ChatFormatting.GOLD, ChatFormatting.UNDERLINE);
    }

    public static Component makePercentage(ToolLevelingTableMenu.PercentageHolder p) {
        return makePercentage(p.enchantment.getDescriptionId(), p.percentage);
    }

    public static Component makePercentage(String str, float percentage) {
        percentage = Math.round(percentage * 10000F) / 100F; // round to 2 decimal place
        MutableComponent text = Component.translatable(str).withStyle(ChatFormatting.GRAY);
        text.append(Component.literal(" " + percentage + "%").withStyle(ChatFormatting.GREEN));
        return text;
    }

    public static MutableComponent makeChance(String str, float chance) {
        return Component.translatable(START + str, chance).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC);
    }

    public static Component makeHelpField(String str, Integer number) {
        return Component.translatable(START + str, number).withStyle(ChatFormatting.GRAY);
    }

    public static Component makeSummary(String str, int iterations, int strength) {
        MutableComponent iterationsText = Component.literal("" + iterations).withStyle(ChatFormatting.GREEN);
        MutableComponent strengthText = Component.literal("" + strength).withStyle(ChatFormatting.GREEN);
        MutableComponent strText = Component.literal("1").withStyle(ChatFormatting.GREEN);
        if (strength > 1) {
            str += ".multi";
        }
        return Component.translatable(START + str, iterationsText, strText, strengthText).withStyle(ChatFormatting.GRAY);
    }

}
