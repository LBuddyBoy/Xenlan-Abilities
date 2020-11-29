package services.xenlan.xabilities.gui.abilityreset;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.build.ItemBuilder;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.List;

public class AbilityResetGUIListener implements Listener {

    @EventHandler
    public void onInventoryInteractPage1(InventoryClickEvent event) {
        if (!ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("Reset (Page 1 of 2)"))) {
            return;
        }
        event.setCancelled(true);
        if (event.getSlot() == -999) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("Reset (Page 1 of 2)"))) {
            for (String section : xAbilities.getConfiguration().getConfig().getConfigurationSection("Ability-Reset.Items.Page-1").getKeys(false)) {
                List<String> lore = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-1." + section + ".Lore");
                List<String> lore2 = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-Items.Page-1.Next-Page.Lore");
                List<String> lore3 = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-Items.Page-1.Previous-Page.Lore");
                if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-1.Next-Page.Item"))).displayName(
                        chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-1.Next-Page.Name")))
                        .setLore(chatUtil.list(lore2)).build())) {
                    player.closeInventory();
                    AbilityResetGUI.openResetGUIPage2(player);
                    return;
                }
                if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-1.Previous-Page.Item"))).displayName(
                        chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-1.Previous-Page.Name")))
                        .setLore(chatUtil.list(lore3)).build())) {
                    player.closeInventory();
                    player.sendMessage(chatUtil.chat("&cYou are already on Page 1."));
                    return;
                }
                if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-1." + section + ".Item"))).displayName(
                        chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-1." + section + ".Name")))
                        .data((short) xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-1." + section + ".Data"))
                        .setLore(chatUtil.list(lore))
                        .build())) {

                    Cooldowns.getAbilityreset().applyCooldown(player, xAbilities.config.getConfig().getInt("Ability-Reset.Cooldown") * 1000);
                    AbilityEvents.takeItem(player, player.getItemInHand());
                    String command = xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-1." + section + ".Command");
                    player.closeInventory();
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName()));
                    player.sendMessage(chatUtil.chat("&eYou have just reset your " + event.getCurrentItem().getItemMeta().getDisplayName() + "&e Cooldown."));
                }
            }
        }
    }

    @EventHandler
    public void onInventoryInteractPage2(InventoryClickEvent event) {
        if (!ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("Reset (Page 2 of 2)"))) {
            return;
        }
        event.setCancelled(true);
        if (event.getSlot() == -999) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("Reset (Page 2 of 2)"))) {
            for (String section : xAbilities.getConfiguration().getConfig().getConfigurationSection("Ability-Reset.Items.Page-2").getKeys(false)) {
                List<String> lore1 = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-2." + section + ".Lore");
                List<String> lore2 = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-Items.Page-2.Next-Page.Lore");
                List<String> lore3 = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-Items.Page-2.Previous-Page.Lore");
                if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-2.Next-Page.Item"))).displayName(
                        chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-2.Next-Page.Name")))
                        .setLore(chatUtil.list(lore2)).build())) {
                    player.closeInventory();
                    player.sendMessage(chatUtil.chat("&cYou are already on Page 2."));
                    return;
                }
                if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-2.Previous-Page.Item"))).displayName(
                        chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-2.Previous-Page.Name")))
                        .setLore(chatUtil.list(lore3)).build())) {
                    player.closeInventory();
                    AbilityResetGUI.openResetGUI(player);
                    return;
                }
                if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-2." + section + ".Item"))).displayName(
                        chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-2." + section + ".Name")))
                        .data((short) xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-2." + section + ".Data"))
                        .setLore(chatUtil.list(lore1))
                        .build())) {

                    Cooldowns.getAbilityreset().applyCooldown(player, xAbilities.config.getConfig().getInt("Ability-Reset.Cooldown") * 1000);
                    AbilityEvents.takeItem(player, player.getItemInHand());
                    String command = xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-2." + section + ".Command");
                    player.closeInventory();
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName()));
                    player.sendMessage(chatUtil.chat("&eYou have just reset your " + event.getCurrentItem().getItemMeta().getDisplayName() + "&e Cooldown."));

                }
            }
        }
    }
}

