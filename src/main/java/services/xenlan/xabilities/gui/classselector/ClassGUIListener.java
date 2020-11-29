package services.xenlan.xabilities.gui.classselector;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import services.xenlan.xabilities.util.build.ItemBuilder;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.ArrayList;
import java.util.List;

public class ClassGUIListener {

    /*@EventHandler
    public void onInventoryInteractPage1(InventoryClickEvent event) {
        if (!ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("Class Selector"))) {
            return;
        }
        event.setCancelled(true);
        if (event.getSlot() == -999) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(ChatColor.stripColor("Class Selector"))) {

            String material = xAbilities.getConfiguration().getConfig().getString("Class-GUI.Items.DIAMOND.Item");
            String name = xAbilities.getConfiguration().getConfig().getString("Class-GUI.Items.DIAMOND.Name");
            List<String> lore = xAbilities.getConfiguration().getConfig().getStringList("Class-GUI.Items.DIAMOND.Lore");

            ItemStack stack = new ItemBuilder(Material.valueOf(material))
                    .displayName(chatUtil.chat(name))
                    .setLore(chatUtil.list(lore))
                    .build();

            if (event.getCurrentItem().isSimilar(stack)) {


                String enchant = xAbilities.getConfiguration().getConfig().getString("Class-GUI.Items.DIAMOND.Armor.Boots.Enchants." + section + ".Enchant");
                int level = xAbilities.getConfiguration().getConfig().getInt("Class-GUI.Items.DIAMOND.Armor.Boots.Enchants." + section + ".Level");


                ItemStack boots = new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Class-GUI.Items.DIAMOND.Armor.Boots.Item")))
                        .enchant(Enchantment.getByName(enchant), level, true)
                        .build();
                player.getInventory().setBoots(boots);

                player.closeInventory();

            }

        }
    }*/

}
