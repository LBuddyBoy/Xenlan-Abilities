package services.xenlan.xabilities.items.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.map.MinecraftFont;
import services.xenlan.xabilities.gui.pocketbard.PocketBardGUI;
import services.xenlan.xabilities.items.AbilityEvents;

public class PocketBard implements Listener {

    private AbilityEvents item = AbilityEvents.POCKETBARD;

    @EventHandler

    public void onClick(PlayerInteractEvent event) {

        Player p = event.getPlayer();

        if (!event.getAction().name().contains("RIGHT"))
            return;

        if (!item.isSimilar(p.getItemInHand()))
            return;

        PocketBardGUI.openAbilityGUI(p);
    }
}
