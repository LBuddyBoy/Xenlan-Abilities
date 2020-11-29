package services.xenlan.xabilities.items.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class Rocket implements Listener {

    private AbilityEvents item = AbilityEvents.ROCKET;
    public static Set<UUID> fall = new HashSet<>();

    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getRocket().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("Rocket.Name")).replace("%time%", Cooldowns.getRocket().getRemaining(event.getPlayer()))));
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

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
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (Cooldowns.getAbilitycd().onCooldown(p)) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Ability.OnCooldown")) {
                    p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("ExoticBone.Name")).replace("%time%", Cooldowns.getAbilitycd().getRemaining(p))));
                    p.updateInventory();
                    event.setCancelled(true);
                    return;
                }
            }
            if (Cooldowns.getRocket().onCooldown(p)) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("Rocket.Name")).replace("%time%", Cooldowns.getRocket().getRemaining(p))));
                    p.updateInventory();
                    event.setCancelled(true);
                    return;
                }
            }
            if (p.getLocation().getBlockX() == xAbilities.config.getConfig().getInt("SPAWN.RADIUS") || p.getLocation().getBlockZ() == xAbilities.config.getConfig().getInt("SPAWN.RADIUS")) {
                for (String list : xAbilities.config.getConfig().getStringList("SPAWN.CantUse")) {
                    p.sendMessage(chatUtil.chat(list));
                    event.setCancelled(true);
                }
            }
            for (String list : xAbilities.config.getConfig().getStringList("Rocket.Message.Success")) {
                p.sendMessage(chatUtil.chat(list.replaceAll("%player%", p.getName())));
            }
            Cooldowns.getRocket().applyCooldown(p, xAbilities.config.getConfig().getInt("Rocket.Cooldown") * 1000);
            if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
                Cooldowns.getAbilitycd().applyCooldown(p, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
            }
            AbilityEvents.takeItem(p, p.getItemInHand());
            p.setVelocity(new Vector(p.getLocation().getDirection().getX() * xAbilities.config.getConfig().getInt("Rocket.Z"), xAbilities.config.getConfig().getInt("Rocket.Y"),
                    p.getLocation().getDirection().getZ() * xAbilities.config.getConfig().getInt("Rocket.X")));
            fall.add(p.getUniqueId());
            xAbilities.getInstance().getServer().getScheduler().runTaskTimer(xAbilities.getInstance(), this::run, 50, 50);
            p.updateInventory();
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player player = (Player) entity;
            if (fall.remove(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    public void run() {
        Iterator<UUID> iterator = fall.iterator();
        while (iterator.hasNext()) {
            UUID uuid = iterator.next();
            Player player = Bukkit.getPlayer(uuid);

            if (player != null && (player.isOnGround())) {
                iterator.remove();
            }
        }
    }

}
