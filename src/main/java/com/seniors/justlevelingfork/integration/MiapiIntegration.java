package com.seniors.justlevelingfork.integration;

import com.seniors.justlevelingfork.JustLevelingFork;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MiapiIntegration {
    private static final String MOD_ID = "miapi";
    private static boolean initialized = false;
    private static boolean available = false;
    private static Method isModularItemMethod;
    private static Method getModulesMethod;
    private static Method getFlatListMethod;
    private static Method moduleIdMethod;
    private static ResourceLocation emptyModuleId;
    private static ResourceLocation internalModuleId;

    public static boolean isLoaded() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID);
    }

    public static boolean isModularItem(ItemStack stack) {
        if (!initialize()) {
            return false;
        }

        try {
            return (boolean) isModularItemMethod.invoke(null, stack);
        } catch (ReflectiveOperationException exception) {
            JustLevelingFork.getLOGGER().warn(">> Failed to check Miapi modular item state.", exception);
            return false;
        }
    }

    public static List<ResourceLocation> getModuleIds(ItemStack stack) {
        if (!initialize()) {
            return List.of();
        }

        try {
            Object rootModule = getModulesMethod.invoke(null, stack);
            List<?> modules = (List<?>) getFlatListMethod.invoke(rootModule);
            List<ResourceLocation> moduleIds = new ArrayList<>();
            for (Object module : modules) {
                ResourceLocation moduleId = (ResourceLocation) moduleIdMethod.invoke(module);
                if (!moduleId.equals(emptyModuleId) && !moduleId.equals(internalModuleId) && !moduleIds.contains(moduleId)) {
                    moduleIds.add(moduleId);
                }
            }
            return moduleIds;
        } catch (ReflectiveOperationException exception) {
            JustLevelingFork.getLOGGER().warn(">> Failed to read Miapi module IDs.", exception);
            return List.of();
        }
    }

    private static boolean initialize() {
        if (initialized) {
            return available;
        }

        initialized = true;
        if (!isLoaded()) {
            return false;
        }

        try {
            Class<?> modularItemClass = Class.forName("smartin.miapi.item.modular.ModularItem");
            Class<?> itemModuleClass = Class.forName("smartin.miapi.modules.ItemModule");
            Class<?> moduleInstanceClass = Class.forName("smartin.miapi.modules.ModuleInstance");

            isModularItemMethod = modularItemClass.getMethod("isModularItem", ItemStack.class);
            getModulesMethod = itemModuleClass.getMethod("getModules", ItemStack.class);
            getFlatListMethod = moduleInstanceClass.getMethod("getFlatList");
            moduleIdMethod = moduleInstanceClass.getMethod("moduleId");

            emptyModuleId = readModuleId(itemModuleClass, "empty");
            internalModuleId = readModuleId(itemModuleClass, "internal");
            available = true;
        } catch (ReflectiveOperationException exception) {
            JustLevelingFork.getLOGGER().warn(">> Miapi is loaded but the integration could not initialize.", exception);
        }

        return available;
    }

    private static ResourceLocation readModuleId(Class<?> itemModuleClass, String fieldName) throws ReflectiveOperationException {
        Field field = itemModuleClass.getField(fieldName);
        Object module = field.get(null);
        Method idMethod = itemModuleClass.getMethod("id");
        return (ResourceLocation) idMethod.invoke(module);
    }
}
