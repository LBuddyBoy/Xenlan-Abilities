package services.xenlan.xabilities.gui.abilityreset;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import services.xenlan.xabilities.util.build.ItemBuilder;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.List;

public class AbilityResetGUI {

    public static void openResetGUI(Player player) {

        Inventory drawer = Bukkit.createInventory(null, xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-1-Size"), chatUtil.chat("&4&lReset &7(Page 1 of 2)"));

        if (xAbilities.getConfiguration().getConfig().getBoolean("Ability-Reset.Items.Page-Items.Page-1.Next-Page.Enabled")) {
            List<String> lore3 = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-Items.Page-1.Previous-Page.Lore");
            drawer.setItem(xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-Items.Page-1.Next-Page.Slot") - 1, new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-1.Next-Page.Item"))).displayName(
                    chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-1.Next-Page.Name")))
                    .setLore(chatUtil.list(lore3)).build());
        }
        if (xAbilities.getConfiguration().getConfig().getBoolean("Ability-Reset.Items.Page-Items.Page-1.Previous-Page.Enabled")) {
            List<String> lore2 = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-Items.Page-1.Previous-Page.Lore");
            drawer.setItem(xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-Items.Page-1.Previous-Page.Slot") - 1, new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-1.Previous-Page.Item"))).displayName(
                    chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-1.Previous-Page.Name")))
                    .setLore(chatUtil.list(lore2)).build());
        }

        for (String section : xAbilities.getConfiguration().getConfig().getConfigurationSection("Ability-Reset.Items.Page-1").getKeys(false)) {

            List<String> lore = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-1." + section + ".Lore");

            int data = xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-1." + section + ".Data");
            String name = xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-1." + section + ".Name");
            String item = xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-1." + section + ".Item");

            ItemStack stack = new ItemBuilder(Material.valueOf(item)
                    , 1)
                    .displayName(chatUtil.chat(name))
                    .data((short) data)
                    .setLore(chatUtil.list(lore)).build();

            drawer.setItem(xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-1." + section + ".Slot") - 1, stack);
        }
        player.openInventory(drawer);
    }


    public static void openResetGUIPage2(Player player) {

        Inventory drawer = Bukkit.createInventory(null, xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-2-Size"), chatUtil.chat("&4&lReset &7(Page 2 of 2)"));

        if (xAbilities.getConfiguration().getConfig().getBoolean("Ability-Reset.Items.Page-Items.Page-2.Next-Page.Enabled")) {
            List<String> lore3 = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-Items.Page-2.Previous-Page.Lore");
            drawer.setItem(xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-Items.Page-2.Next-Page.Slot") - 1, new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-2.Next-Page.Item"))).displayName(
                    chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-2.Next-Page.Name")))
                    .setLore(chatUtil.list(lore3)).build());
        }
        if (xAbilities.getConfiguration().getConfig().getBoolean("Ability-Reset.Items.Page-Items.Page-2.Previous-Page.Enabled")) {
            List<String> lore2 = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-Items.Page-2.Previous-Page.Lore");
            drawer.setItem(xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-Items.Page-2.Previous-Page.Slot") - 1,
                    new ItemBuilder
                            (Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-2.Previous-Page.Item")))
                            .displayName(chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-Items.Page-2.Previous-Page.Name")))
                            .setLore(chatUtil.list(lore2)).build());
        }

        for (String section : xAbilities.getConfiguration().getConfig().getConfigurationSection("Ability-Reset.Items.Page-2").getKeys(false)) {

            List<String> lore = xAbilities.getConfiguration().getConfig().getStringList("Ability-Reset.Items.Page-2." + section + ".Lore");

            int data = xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-2." + section + ".Data");

            ItemStack stack = new ItemBuilder(Material.valueOf(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-2." + section + ".Item")))
                    .displayName(chatUtil.chat(xAbilities.getConfiguration().getConfig().getString("Ability-Reset.Items.Page-2." + section + ".Name")))
                    .data((short) data)
                    .setLore(chatUtil.list(lore)).build();

            drawer.setItem(xAbilities.getConfiguration().getConfig().getInt("Ability-Reset.Items.Page-2." + section + ".Slot") - 1, stack);

        }
        player.openInventory(drawer);
    }
}
