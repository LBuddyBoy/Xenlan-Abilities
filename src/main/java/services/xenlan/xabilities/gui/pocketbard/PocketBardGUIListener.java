package services.xenlan.xabilities.gui.pocketbard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.build.ItemBuilder;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.List;

public class PocketBardGUIListener implements Listener {
    @EventHandler
    public void onInventoryInteractPage1(InventoryClickEvent event) {
        if (!ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("PocketBard"))) {
            return;
        }
        event.setCancelled(true);
        if (event.getSlot() == -999) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("PocketBard"))) {
            for (String section : xAbilities.getConfiguration().getConfig().getConfigurationSection("PocketBard.Items").getKeys(false)) {
                    List<String> lore = xAbilities.getConfiguration().getConfig().getStringList("PocketBard.Items." + section + ".Lore");
                    if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("PocketBard.Items." + section + ".Item"))).displayName(
                            chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("PocketBard.Items." + section + ".Name")))
                            .setLore(chatUtil.list(lore))
                            .build())) {

                        //Cooldowns.getPocketbard().applyCooldown(player, xAbilities.config.getConfig().getInt("PocketBard.PocketBard.Cooldown") * 1000);
                        AbilityEvents.takeItem(player, player.getItemInHand());
                        String command = xAbilities.getConfiguration().getConfig().getString("PocketBard.Items." + section + ".Command");
                        player.closeInventory();
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName()));
                }
            }
        }
    }
}
