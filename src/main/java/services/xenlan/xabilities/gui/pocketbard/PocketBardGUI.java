package services.xenlan.xabilities.gui.pocketbard;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import services.xenlan.xabilities.util.build.ItemBuilder;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.ArrayList;
import java.util.List;

public class PocketBardGUI {

    public static void openAbilityGUI(Player player) {
        Inventory drawer = Bukkit.createInventory(null, xAbilities.getConfiguration().getConfig().getInt("PocketBard.Size"), chatUtil.chat("&b&lPocketBard"));

        for (String section : xAbilities.getConfiguration().getConfig().getConfigurationSection("PocketBard.Items").getKeys(false)) {
            for (String list : xAbilities.getConfiguration().getConfig().getStringList("PocketBard.Items." + section + ".Lore")) {
                List<String> lore = new ArrayList<>();
                lore.add(chatUtil.chat(list));
                drawer.setItem(xAbilities.getConfiguration().getConfig().getInt("PocketBard.Items." + section + ".Slot"),
                        new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("PocketBard.Items." + section + ".Item"))).displayName(
                                chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("PocketBard.Items."  + section + ".Name")))
                                .setLore(lore).build());
            }
        }
        player.openInventory(drawer);
    }
    
}
