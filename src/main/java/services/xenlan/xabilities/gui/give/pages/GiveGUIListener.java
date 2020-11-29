package services.xenlan.xabilities.gui.give.pages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import services.xenlan.xabilities.util.build.ItemBuilder;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.gui.give.convo.Coversation;
import services.xenlan.xabilities.xAbilities;

import java.util.ArrayList;
import java.util.List;

public class GiveGUIListener implements Listener {
    @EventHandler
    public void onInventoryInteractPage1(InventoryClickEvent event) {
        if (!ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("Give GUI (Page 1 of 2)"))) {
            return;
        }
        event.setCancelled(true);
        if (event.getSlot() == -999) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Player p = xAbilities.getInstance().getPlayerMap().get(player);
        if (ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("Give GUI (Page 1 of 2)"))) {
            for (String section : xAbilities.getConfiguration().getConfig().getConfigurationSection("Give-GUI.Items.Page-1").getKeys(false)) {
                for (String list : xAbilities.getConfiguration().getConfig().getStringList("Give-GUI.Items.Page-1." + section + ".Lore")) {
                    List<String> lore = new ArrayList<>();
                    lore.add(chatUtil.chat(list));
                    for (String list2 : xAbilities.getConfiguration().getConfig().getStringList("Give-GUI.Items.Page-Items.Page-1.Next-Page.Lore")) {
                        List<String> lore2 = new ArrayList<>();
                        lore2.add(chatUtil.chat(list2));
                        for (String list3 : xAbilities.getConfiguration().getConfig().getStringList("Give-GUI.Items.Page-Items.Page-1.Previous-Page.Lore")) {
                            List<String> lore3 = new ArrayList<>();
                            lore3.add(chatUtil.chat(list3));
                            if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-Items.Page-1.Next-Page.Item"))).displayName(
                                    chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-Items.Page-1.Next-Page.Name")))
                                    .setLore(lore2).build())) {
                                player.closeInventory();
                                xAbilities.getInstance().getPlayerMap().remove(p);
                                xAbilities.getInstance().getPlayerMap().remove(player);
                                GiveGUI.openGiveGUIPage2(player, p);
                                return;
                            }
                            if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-Items.Page-1.Previous-Page.Item"))).displayName(
                                    chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-Items.Page-1.Previous-Page.Name")))
                                    .setLore(lore3).build())) {
                                player.closeInventory();
                                xAbilities.getInstance().getPlayerMap().remove(p);
                                xAbilities.getInstance().getPlayerMap().remove(player);
                                player.sendMessage(chatUtil.chat("&cYou are already on Page 1."));
                                return;
                            }
                            if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-1." + section + ".Item"))).displayName(
                                    chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-1." + section + ".Name")))
                                    .data((short) xAbilities.getConfiguration().getConfig().getInt("Give-GUI.Items.Page-1." + section + ".Data"))

                                    .setLore(lore)
                                    .build())) {
                                String command = xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-1." + section + ".Command");
                                player.closeInventory();
                                Coversation.conversationDouble(player, chatUtil.chat("&eEnter an amount to give " + p.getName() + "."), (d) -> {
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", p.getName()).replaceAll("%amount%", String.valueOf(d.intValue())));
                                    player.sendMessage(chatUtil.chat("&bYou have given &3" + p.getName() + " &bx" + d.intValue() + " of " + xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-1." + section + ".Name")));
                                    xAbilities.getInstance().getPlayerMap().remove(player);
                                    xAbilities.getInstance().getPlayerMap().remove(p);
                                });
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryInteractPage2(InventoryClickEvent event) {
        if (!ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("Give GUI (Page 2 of 2)"))) {
            return;
        }
        event.setCancelled(true);
        if (event.getSlot() == -999) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Player p = xAbilities.getInstance().getPlayerMap().get(player);
        if (ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("Give GUI (Page 2 of 2)"))) {
            for (String section : xAbilities.getConfiguration().getConfig().getConfigurationSection("Give-GUI.Items.Page-2").getKeys(false)) {
                for (String list : xAbilities.getConfiguration().getConfig().getStringList("Give-GUI.Items.Page-2." + section + ".Lore")) {
                    List<String> lore = new ArrayList<>();
                    lore.add(chatUtil.chat(list));
                    for (String list2 : xAbilities.getConfiguration().getConfig().getStringList("Give-GUI.Items.Page-Items.Page-2.Next-Page.Lore")) {
                        List<String> lore2 = new ArrayList<>();
                        lore2.add(chatUtil.chat(list2));
                        for (String list3 : xAbilities.getConfiguration().getConfig().getStringList("Give-GUI.Items.Page-Items.Page-2.Previous-Page.Lore")) {
                            List<String> lore3 = new ArrayList<>();
                            lore3.add(chatUtil.chat(list3));
                            if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-Items.Page-2.Next-Page.Item"))).displayName(
                                    chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-Items.Page-2.Next-Page.Name")))
                                    .setLore(lore2).build())) {
                                player.closeInventory();
                                xAbilities.getInstance().getPlayerMap().remove(p);
                                xAbilities.getInstance().getPlayerMap().remove(player);
                                player.sendMessage(chatUtil.chat("&cYou are already on Page 2."));
                                return;
                            }
                            if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-Items.Page-2.Previous-Page.Item"))).displayName(
                                    chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-Items.Page-2.Previous-Page.Name")))
                                    .setLore(lore3).build())) {
                                player.closeInventory();
                                xAbilities.getInstance().getPlayerMap().remove(p);
                                xAbilities.getInstance().getPlayerMap().remove(player);
                                GiveGUI.openGiveGUI(player, p);
                                return;
                            }
                            if (event.getCurrentItem().isSimilar(new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-2." + section + ".Item"))).displayName(
                                    chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-2." + section + ".Name")))
                                    .data((short) xAbilities.getConfiguration().getConfig().getInt("Give-GUI.Items.Page-2." + section + ".Data"))
                                    .setLore(lore)
                                    .build())) {
                                String command = xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-2." + section + ".Command");
                                player.closeInventory();
                                Coversation.conversationDouble(player, chatUtil.chat("&eEnter an amount to give " + p.getName() + "."), (d) -> {
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", p.getName()).replaceAll("%amount%", String.valueOf(d.intValue())));
                                    player.sendMessage(chatUtil.chat("&bYou have given &3" + p.getName() + " &bx" + d.intValue() + " of " + xAbilities.getConfiguration().getConfig().getString("Give-GUI.Items.Page-2." + section + ".Name")));
                                    xAbilities.getInstance().getPlayerMap().remove(player);
                                    xAbilities.getInstance().getPlayerMap().remove(p);
                                });
                            }
                        }
                    }
                }
            }
        }
    }
}

