package com.seniors.justlevelingfork.registry.skills;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.client.core.Utils;
import com.seniors.justlevelingfork.client.core.Value;
import com.seniors.justlevelingfork.client.core.ValueType;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerConfigClient;
import com.seniors.justlevelingfork.handler.HandlerResources;
import com.seniors.justlevelingfork.registry.RegistryAptitudes;
import com.seniors.justlevelingfork.registry.RegistryCapabilities;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Skill {
    public final ResourceLocation key;
    public final Aptitude aptitude;
    public final int requiredLevel;
    public final ResourceLocation texture;
    private final Value[] configValues;

    public Skill(ResourceLocation skillKey, Aptitude aptitude, int levelRequirement, ResourceLocation skillTexture, Value... skillValues) {
        this.key = skillKey;
        this.aptitude = aptitude;
        this.requiredLevel = levelRequirement;
        this.texture = skillTexture;
        this.configValues = skillValues;
    }

    // KubeJS support
    public static Skill add(String skillName, String aptitudeName, int levelRequirement, String texture, Value... skillValues){
        Aptitude aptitude = RegistryAptitudes.getAptitude(aptitudeName);
        if (aptitude == null){
            throw new IllegalArgumentException("Aptitude name doesn't exist: " + aptitudeName);
        }

        ResourceLocation key = new ResourceLocation(JustLevelingFork.MOD_ID, skillName);
        return new Skill(key, aptitude, levelRequirement, HandlerResources.create(texture), skillValues);
    }

    public Skill get() {
        return this;
    }

    public String getMod() {
        return this.key.getNamespace();
    }

    public String getName() {
        return this.key.getPath();
    }

    public String getKey() {
        return "skill." + this.key.toLanguageKey();
    }

    public String getDescription() {
        return getKey() + ".description";
    }

    public int getLvl() {
        return this.requiredLevel;
    }

    public double[] getValue() {
        double[] newValue = new double[this.configValues.length];
        for (int i = 0; i < newValue.length; i++) {
            newValue[i] = 0.0D;
            if (this.configValues[i] != null) {
                Object object = (this.configValues[i]).value;
                if (object instanceof Double) {
                    newValue[i] = (Double) object;
                }
                object = (this.configValues[i]).value;
                if (object instanceof Integer) {
                    newValue[i] = (Integer) object;
                }
                object = (this.configValues[i]).value;
                if (object instanceof Number value) {
                    newValue[i] = value.doubleValue();
                }
            }
        }
        return newValue;
    }

    public MutableComponent getMutableDescription(String description) {
        Object[] newValue = new Object[this.configValues.length];
        for (int i = 0; i < newValue.length; i++) {
            if (this.configValues[i] != null) {
                newValue[i] = getParameter((this.configValues[i]).type, getValue()[i]);
            }
        }
        return Component.translatable(description, newValue);
    }

    public String getParameter(ValueType type, double parameterValue) {
        DecimalFormat df = new DecimalFormat("0.##");
        String probabilityValue = Utils.periodValue(1.0D / parameterValue * 100.0D);
        String parameter = df.format(parameterValue);
        if (type.equals(ValueType.MODIFIER)) parameter = "§cx" + parameter;
        if (type.equals(ValueType.DURATION)) parameter = "§9" + parameter + "s";
        if (type.equals(ValueType.AMPLIFIER)) parameter = "§6+" + parameter;
        if (type.equals(ValueType.PERCENT)) parameter = "§2" + parameter + "%";
        if (type.equals(ValueType.BOOST)) parameter = "§d" + Utils.intToRoman(Integer.parseInt(parameter));
        if (type.equals(ValueType.PROBABILITY))
            parameter = "§e1/" + parameter + "§r§7 (§2" + probabilityValue + "%§7§r)";
        return parameter + "§r§7";
    }

    public List<Component> tooltip() {
        List<Component> list = new ArrayList<>();
        list.add(Component.translatable("tooltip.skill.title").append(Component.translatable(getKey())).withStyle(ChatFormatting.AQUA));
        list.add(Component.translatable("tooltip.skill.description." + (canSkill() ? "on" : "off")).withStyle(canSkill() ? ChatFormatting.GREEN : ChatFormatting.RED));
        list.add(Component.empty());
        if (Screen.hasShiftDown()) {
            list.add(Component.empty()
                    .append(Component.translatable(getKey()).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.UNDERLINE))
                    .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                    .append(getMutableDescription(getDescription()).withStyle(ChatFormatting.GRAY)));
            list.add(Component.empty());
            list.add(Component.translatable("tooltip.skill.description.level_requirement").withStyle(ChatFormatting.DARK_PURPLE));
            if (this.requiredLevel > 0){
                list.add(Component.literal(" ").append(Component.translatable("tooltip.skill.description.available", Component.literal(String.valueOf(getLvl())).withStyle(ChatFormatting.GREEN))).withStyle(ChatFormatting.DARK_AQUA));
            }
            else{
                list.add(Component.translatable("tooltip.skill.description.off").withStyle(ChatFormatting.RED));
            }

        } else {
            list.add(Component.translatable("tooltip.general.description.more_information").withStyle(ChatFormatting.YELLOW));
        }
        if (HandlerConfigClient.showSkillModName.get()) {
            list.add(Component.literal(Utils.getModName(getMod())).withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        }
        return list;
    }

    public boolean canSkill() {
        return requiredLevel > 0 && AptitudeCapability.get().getToggleSkill(this);
    }

    public boolean canSkill(Player player) {
        return requiredLevel > 0 && AptitudeCapability.get(player).getToggleSkill(this);
    }

    public boolean getToggle() {
        return this.requiredLevel > 0 && AptitudeCapability.get().getAptitudeLevel(this.aptitude) >= this.requiredLevel;
    }

    public boolean getToggle(Player player) {
        return this.requiredLevel > 0 && AptitudeCapability.get(player).getAptitudeLevel(this.aptitude) >= this.requiredLevel;
    }

    public boolean isEnabled() {
        if (this.requiredLevel < 1) return true;
        if(AptitudeCapability.get() == null) return false;

        return (AptitudeCapability.get().getAptitudeLevel(this.aptitude) >= this.requiredLevel && AptitudeCapability.get().getToggleSkill(this));
    }

    public boolean isEnabled(Player player) {
        AtomicBoolean b = new AtomicBoolean(false);
        if (player != null &&
                !player.isDeadOrDying()) {
            player.getCapability(RegistryCapabilities.APTITUDE).ifPresent(aptitudeCapability -> b.set(
                    this.requiredLevel > 0 && (AptitudeCapability.get(player).getAptitudeLevel(this.aptitude) >= this.requiredLevel && AptitudeCapability.get(player).getToggleSkill(this))));
        }

        return b.get();
    }

    public ResourceLocation getTexture() {
        return Objects.requireNonNullElse(this.texture, HandlerResources.NULL_SKILL);
    }
}


