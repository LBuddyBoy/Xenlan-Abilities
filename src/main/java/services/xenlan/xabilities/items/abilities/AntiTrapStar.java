package services.xenlan.xabilities.items.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.*;

public class AntiTrapStar implements Listener {

    private AbilityEvents item = AbilityEvents.ANTITRAPSTAR;
    private Map<UUID, UUID> anitrapstarmap = new HashMap<>();
    private List<Player> anittrap = new ArrayList<>();
    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getAntitrapstar().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("AntiTrapStar.Name")).replace("%time%", Cooldowns.getAntitrapstar().getRemaining(event.getPlayer()))));
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
    @EventHandler
    public void onTrapstar(PlayerInteractEvent event) {

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
            for (String list : xAbilities.config.getConfig().getStringList("Message.AbilityEvents.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("ExoticBone.Name")).replace("%time%", Cooldowns.getAbilitycd().getRemaining(p))));
                event.setCancelled(true);
                p.updateInventory();
                return;
            }
        }
        if (Cooldowns.getAntitrapstar().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("AntiTrapStar.Name")).replace("%time%", Cooldowns.getAntitrapstar().getRemaining(p))));
                event.setCancelled(true);
                p.updateInventory();
                return;
            }
        }
        Player damaged = Bukkit.getPlayer(anitrapstarmap.get(p.getUniqueId()));
        if (!anitrapstarmap.containsKey(p.getUniqueId())) {
            p.sendMessage(chatUtil.chat("&cNo player has hit you in the last 15 seconds."));
            return;
        }
        if (!anittrap.contains(p)) {
            p.sendMessage(chatUtil.chat("&cNo player has hit you in the last 15 seconds."));
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                p.teleport(damaged.getLocation());
                for (String list : xAbilities.config.getConfig().getStringList("NinjaStar.Message.Teleported")) {
                    p.sendMessage(chatUtil.chat(list).replaceAll("%player%", damaged.getName()));
                }
                if (xAbilities.config.getConfig().getBoolean("NinjaStar.Been-Teleported.Message")) {
                    for (String list : xAbilities.config.getConfig().getStringList("NinjaStar.Message.Been-Teleported")) {
                        damaged.sendMessage(chatUtil.chat(list).replaceAll("%player%", p.getName()));
                    }
                }
            }
        }.runTaskLater(xAbilities.getInstance(), xAbilities.config.getConfig().getInt("AntiTrapStar.WarmUp") * 20);

        for (String list : xAbilities.config.getConfig().getStringList("AntiTrapStar.Message.Teleporting")) {
            p.sendMessage(chatUtil.chat(list).replaceAll("%player%", damaged.getName()));
        }
        anittrap.remove(damaged);
        Cooldowns.getAntitrapstar().applyCooldown(p, xAbilities.config.getConfig().getInt("AntiTrapStar.Cooldown") * 1000);
        AbilityEvents.takeItem(p, p.getItemInHand());
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
//        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Snowball) ||
//                !(event.getDamager() instanceof Egg) || !(event.getDamager() instanceof Arrow) ||
//                !(event.getDamager() instanceof FishHook)) {
//            return;
//        }

        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Projectile)) {
            return;
        }
        Projectile projectile = (Projectile) event.getDamager();
        if (projectile.getShooter() instanceof Skeleton)
            return;
        if (projectile.getShooter() instanceof Blaze)
            return;
        Player damaged = (Player) event.getEntity();
        Player damager = (Player) ((Projectile) event.getDamager()).getShooter();

        if (damaged == damager) {
            return;
        }
        if (anitrapstarmap.containsKey(damaged.getUniqueId())) {
            anitrapstarmap.remove(damaged.getUniqueId());
            anittrap.remove(damaged);
        } else {
            anitrapstarmap.put(damaged.getUniqueId(), damager.getUniqueId());
            anittrap.add(damaged);
            new BukkitRunnable() {
                public void run() {
                    anitrapstarmap.remove(damaged.getUniqueId(), damager.getUniqueId());
                    anittrap.remove(damaged);
                }
            }.runTaskLater(xAbilities.getInstance(), 15 * 20);
        }
    }

}
