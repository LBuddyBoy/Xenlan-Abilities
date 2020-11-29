package services.xenlan.xabilities.items.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.*;

public class RageAbility implements Listener {

    private AbilityEvents item = AbilityEvents.RAGEABILITY;
    private Map<UUID, Integer> hits = new HashMap<>();
    private Set<UUID> raged = new HashSet<>();

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
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("ExoticBone.Name")).replace("%time%", Cooldowns.getAbilitycd().getRemaining(p))));
                event.setCancelled(true);
                p.updateInventory();
                return;
            }
        }
        if (Cooldowns.getRageability().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("RageAbility.Name")).replace("%time%", Cooldowns.getRageability().getRemaining(p))));
                event.setCancelled(true);
                p.updateInventory();
                return;
            }
        }
        AbilityEvents.takeItem(p, p.getItemInHand());
        Cooldowns.getRageability().applyCooldown(p, xAbilities.config.getConfig().getInt("RageAbility.Cooldown") * 1000);
        if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
            Cooldowns.getAbilitycd().applyCooldown(p, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
        }
        for (String list : xAbilities.config.getConfig().getStringList("RageAbility.Message.Used")) {
            p.sendMessage(chatUtil.chat(list));
        }
        new BukkitRunnable() {
            @Override
            public void run() {

                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, hits.get(p.getUniqueId()) * 20,
                        xAbilities.config.getConfig().getInt("RageAbility.Effect.Power") - 1));
                for (String list : xAbilities.config.getConfig().getStringList("RageAbility.Message.Finished")) {
                    p.sendMessage(chatUtil.chat(list).replaceAll("%hits%", String.valueOf(hits.get(p.getUniqueId()))));
                }
                hits.remove(p.getUniqueId());
                raged.remove(p.getUniqueId());
            }
        }.runTaskLater(xAbilities.getInstance(), 20L * xAbilities.config.getConfig().getInt("RageAbility.Max-Seconds"));
        raged.add(p.getUniqueId());
        hits.putIfAbsent(p.getUniqueId(), 0);
    }

    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getRageability().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("RageAbility.Name")).replace("%time%", Cooldowns.getRageability().getRemaining(event.getPlayer()))));
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Player) {
                Player damager = (Player) event.getDamager();
                Player damaged = (Player) event.getEntity();
                if (raged.contains(damaged.getUniqueId())) {

                    hits.put(damaged.getUniqueId(), hits.get(damaged.getUniqueId()) + 1);

                    if (hits.get(damaged.getUniqueId()) == xAbilities.config.getConfig().getInt("RageAbility.Max-Seconds")) {
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }
}
