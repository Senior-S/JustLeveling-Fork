package com.seniors.justlevelingfork.integration;

import com.seniors.justlevelingfork.JustLevelingFork;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ToolAction;
import se.mickelus.tetra.items.modular.ItemModularHandheld;
import se.mickelus.tetra.util.TierHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TetraIntegration {
    private static final String _prefix = "tetra*tier*";
    public static final List<String> TetraItems = List.of("tetra:modular_double", "tetra:modular_sword");

    /**
     * Get a list of formatted modules.
     *
     * @param item ItemStack to check
     * @return List of formatted modules with their respective tier.
     */
    public static List<String> GetItemTypes(ItemStack item) {
        List<String> list = new ArrayList<>();
        if (item.getItem() instanceof ItemModularHandheld modularHandheld) {
            Set<ToolAction> toolActions = modularHandheld.getToolActions(item);

            for (int i = 0; i < toolActions.size(); i++) {
                ToolAction action = toolActions.stream().toList().get(i);
                String actionName = action.name();
                Tier tier = TierHelper.getTier(modularHandheld.getHarvestTier(item, action));

                if(tier == null){
                    JustLevelingFork.getLOGGER().warn("Item {} with action {} have a null tier.", item.getDisplayName().toString(), actionName);
                    continue;
                }

                switch (actionName){
                    case "axe_wax_off":
                    case "axe_scrape":
                    case "axe_dig":
                    case "axe_strip":
                        list.add(String.format("%saxe:%s", _prefix, tier.toString().toLowerCase()));
                        break;
                    case "pickaxe_dig":
                        list.add(String.format("%spickaxe:%s", _prefix, tier.toString().toLowerCase()));
                        break;
                    case "shovel_dig":
                        list.add(String.format("%sshovel:%s", _prefix, tier.toString().toLowerCase()));
                        break;
                    case "hoe_dig":
                        list.add(String.format("%shoe:%s", _prefix, tier.toString().toLowerCase()));
                        break;
                    case "cut":
                        list.add(String.format("%scut:%s", _prefix, tier.toString().toLowerCase()));
                        break;
                }
            }
        }

        return list;
    }
}
