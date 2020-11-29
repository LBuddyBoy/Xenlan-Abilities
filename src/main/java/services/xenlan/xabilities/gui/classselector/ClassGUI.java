package services.xenlan.xabilities.gui.classselector;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import services.xenlan.xabilities.util.build.ItemBuilder;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.List;

public class ClassGUI {

    public static void openClassGUI(Player player) {

        Inventory i;

        int size = xAbilities.getConfiguration().getConfig().getInt("Class-GUI.Size");

        i = Bukkit.createInventory(null, size, chatUtil.chat("&e&lClass Selector"));

        if (xAbilities.getConfiguration().getConfig().getBoolean("Class-GUI.Items.DIAMOND.Enabled")) {

            int slot = xAbilities.getConfiguration().getConfig().getInt("Class-GUI.Items.DIAMOND.Slot");
            String material = xAbilities.getConfiguration().getConfig().getString("Class-GUI.Items.DIAMOND.Item");
            String name = xAbilities.getConfiguration().getConfig().getString("Class-GUI.Items.DIAMOND.Name");
            List<String> lore = xAbilities.getConfiguration().getConfig().getStringList("Class-GUI.Items.DIAMOND.Lore");

            ItemStack stack = new ItemBuilder(Material.valueOf(material))
                    .displayName(chatUtil.chat(name))
                    .setLore(chatUtil.list(lore))
                    .build();

            i.setItem(slot, stack);
        }

        player.openInventory(i);

    }

}
