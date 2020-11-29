package services.xenlan.xabilities.items.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.Iterator;
import java.util.UUID;

public class EnderButt implements Listener {

    AbilityEvents item = AbilityEvents.ENDERBUTT;

    @EventHandler

    public void onClick(PlayerInteractEvent event) {

        Player p = event.getPlayer();

        if (!event.getAction().name().contains("RIGHT"))
            return;

        if (!item.isSimilar(p.getItemInHand()))
            return;
        if (item.isSimilar(p.getItemInHand())) {
            if (AbilityEvents.checkLocation(p.getLocation(), xAbilities.config.getConfig().getInt("SPAWN.Radius"), 0, 0)) {
                for (String list : xAbilities.config.getConfig().getStringList("SPAWN.CantUse")) {
                    p.sendMessage(chatUtil.chat(list));
                }
                event.setCancelled(true);
                return;
            }
        }
        if (Cooldowns.getAbilitycd().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Ability.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%time%", Cooldowns.getAbilitycd().getRemaining(p))));
                event.setCancelled(true);
                event.setUseItemInHand(Event.Result.DENY);
                p.updateInventory();
                return;
            }
        }
        if (Cooldowns.getEnderbutt().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("EnderButt.Name")).replace("%time%", Cooldowns.getEnderbutt().getRemaining(p))));
                event.setCancelled(true);
                event.setUseItemInHand(Event.Result.DENY);
                p.updateInventory();
                return;
            }
        }
        AbilityEvents.takeItem(p, p.getItemInHand());
        Cooldowns.getEnderbutt().applyCooldown(p, xAbilities.config.getConfig().getInt("EnderButt.Cooldown") * 1000);
        if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
            Cooldowns.getAbilitycd().applyCooldown(p, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
        }
        Rocket.fall.add(p.getUniqueId());
        p.setVelocity(p.getLocation().getDirection().normalize().multiply(3.0f));
        event.setCancelled(true);
    }
    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getEnderbutt().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("EnderButt.Name")).replace("%time%", Cooldowns.getEnderbutt().getRemaining(event.getPlayer()))));
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
    public void run() {
        Iterator<UUID> iterator = Rocket.fall.iterator();
        while (iterator.hasNext()) {
            UUID uuid = iterator.next();
            Player player = Bukkit.getPlayer(uuid);

            if (player != null && (player.isOnGround())) {
                iterator.remove();
            }
        }
    }

}
