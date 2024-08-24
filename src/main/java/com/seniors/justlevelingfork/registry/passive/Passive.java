package com.seniors.justlevelingfork.registry.passive;

import com.seniors.justlevelingfork.client.core.Utils;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerConfigClient;
import com.seniors.justlevelingfork.handler.HandlerResources;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Passive extends ForgeRegistryEntry<Passive> {
    public final ResourceLocation key;
    public final Aptitude aptitude;
    public final ResourceLocation texture;
    public final Object attribute;
    public final String attributeUuid;
    public final Object attributeValue;
    public final int[] levelsRequired;

    public Passive(ResourceLocation passiveKey, Aptitude aptitude, ResourceLocation passiveTexture, Object attribute, String attributeUuid, Object attributeValue, int... levelsRequired) {
        this.key = passiveKey;
        this.aptitude = aptitude;
        this.texture = passiveTexture;
        this.attribute = attribute;
        this.attributeUuid = attributeUuid;
        this.attributeValue = attributeValue;
        this.levelsRequired = levelsRequired;
    }

    public Passive get() {
        return this;
    }

    public String getMod() {
        return this.key.getNamespace();
    }

    public String getName() {
        return this.key.getPath();
    }

    public String getKey() {
        return String.format("passive.%s.%s", this.key.getNamespace(), this.key.getPath());
    }

    public String getDescription() {
        return getKey() + ".description";
    }

    public Attribute getAttribute() {
        if (this.attribute instanceof RegistryObject value) {
            return (Attribute)value.get();
        } else {
            return (Attribute)this.attribute;
        }
    }

    public double getValue() {
        double newValue = 0.0D;
        if (this.attributeValue != null) {
            Object object = this.attributeValue;
            if (object instanceof ForgeConfigSpec.DoubleValue) {
                ForgeConfigSpec.DoubleValue value = (ForgeConfigSpec.DoubleValue) object;
                newValue = value.get();
            }
            if (object instanceof ForgeConfigSpec.IntValue) {
                ForgeConfigSpec.IntValue value = (ForgeConfigSpec.IntValue) object;
                newValue = value.get();
            }
            if (object instanceof Number) {
                Number value = (Number) object;
                newValue = value.doubleValue();
            }

        }
        return newValue;
    }

    public int getNextLevelUp() {
        int[] requirement = new int[this.levelsRequired.length + 2];
        requirement[0] = 0;
        System.arraycopy(this.levelsRequired, 0, requirement, 1, this.levelsRequired.length);
        return requirement[getLevel() + 1];
    }

    public List<Component> tooltip() {
        DecimalFormat df = new DecimalFormat("0.##");
        String valuePerLevel = df.format(getValue() / this.levelsRequired.length);
        String valueActualLevel = df.format(getValue() / this.levelsRequired.length * getLevel());
        String valueMaxLevel = df.format(getValue());

        List<Component> list = new ArrayList<>();
        list.add(new TranslatableComponent("tooltip.passive.title").append(new TranslatableComponent(getKey())).withStyle(ChatFormatting.GREEN));
        list.add(new TranslatableComponent("tooltip.passive.description.passive_level", getLevel(), this.levelsRequired.length).withStyle(ChatFormatting.GRAY));
        list.add(TextComponent.EMPTY);
        if (Screen.hasShiftDown()) {
            list.add(TextComponent.EMPTY
                    .copy().append(new TranslatableComponent(getKey()).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.UNDERLINE))
                    .append(new TextComponent(": ").withStyle(ChatFormatting.GRAY))
                    .append(new TranslatableComponent(getDescription()).withStyle(ChatFormatting.GRAY)));
            list.add(TextComponent.EMPTY);
            list.add(new TranslatableComponent("tooltip.passive.description.other_info").withStyle(ChatFormatting.GRAY));
            list.add(new TextComponent(" ").append(new TranslatableComponent("tooltip.passive.description.level", valuePerLevel)).withStyle(ChatFormatting.DARK_GREEN));
            list.add(new TextComponent(" ").append(new TranslatableComponent("tooltip.passive.description.actual_level", valueActualLevel)).withStyle(ChatFormatting.DARK_GREEN));
            list.add(new TextComponent(" ").append(new TranslatableComponent("tooltip.passive.description.max_level", valueMaxLevel)).withStyle(ChatFormatting.DARK_GREEN));
            list.add(TextComponent.EMPTY);
            list.add(new TranslatableComponent("tooltip.passive.description.level_requirement").withStyle(ChatFormatting.DARK_PURPLE));
            if (getLevel() < this.levelsRequired.length) {
                list.add(new TextComponent(" ").append(new TranslatableComponent("tooltip.passive.description.passive_required", new TranslatableComponent(this.aptitude.getKey()).withStyle(ChatFormatting.GREEN),
                        new TextComponent(String.valueOf(getNextLevelUp())).withStyle(ChatFormatting.GREEN),
                        new TextComponent(String.valueOf(getLevel() + 1)).withStyle(ChatFormatting.GREEN))).withStyle(ChatFormatting.DARK_AQUA));
            } else {
                list.add(new TextComponent(" ").append(new TranslatableComponent("tooltip.passive.description.passive_max_level")).withStyle(ChatFormatting.DARK_AQUA));
            }
        } else {
            list.add(new TranslatableComponent("tooltip.general.description.more_information").withStyle(ChatFormatting.YELLOW));
        }
        if (HandlerConfigClient.showSkillModName.get()) {
            list.add(new TextComponent(Utils.getModName(getMod())).withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        }

        return list;
    }

    public int getLevel() {
        return AptitudeCapability.get().getPassiveLevel(this);
    }

    public int getLevel(Player player) {
        return AptitudeCapability.get(player).getPassiveLevel(this);
    }

    public int getMaxLevel() {
        return this.levelsRequired.length;
    }

    public ResourceLocation getTexture() {
        return Objects.requireNonNullElse(this.texture, HandlerResources.NULL_SKILL);
    }
}


