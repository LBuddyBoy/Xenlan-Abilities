package services.xenlan.xabilities.items.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.HashMap;
import java.util.Map;

public class PotionCounter implements Listener {

    private Map<Player, Integer> hits = new HashMap<Player, Integer>();
    private AbilityEvents item = AbilityEvents.POTIONCOUNTER;

    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getPotioncounter().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("PotionCounter.Name")).replace("%time%", Cooldowns.getPotioncounter().getRemaining(event.getPlayer()))));
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

                if (!item.isSimilar(damager.getItemInHand()))
                    return;
                if (item.isSimilar(damager.getItemInHand())) {
                    if (AbilityEvents.checkLocation(damager.getLocation(), xAbilities.config.getConfig().getInt("SPAWN.Radius"), 0, 0)) {
                        for (String list : xAbilities.config.getConfig().getStringList("SPAWN.CantUse")) {
                            damager.sendMessage(chatUtil.chat(list));
                        }
                        event.setCancelled(true);
                        return;
                    }
                }
                if (Cooldowns.getAbilitycd().onCooldown(damager)) {
                    for (String list : xAbilities.config.getConfig().getStringList("Message.Ability.OnCooldown")) {
                        damager.sendMessage(chatUtil.chat(list).replace("%time%", Cooldowns.getAbilitycd().getRemaining(damager)));
                        event.setCancelled(true);
                        return;
                    }
                }
                if (Cooldowns.getPotioncounter().onCooldown(damager)) {
                    for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                        damager.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("PotionCounter.Name")).replace("%time%", Cooldowns.getPotioncounter().getRemaining(damager))));
                        event.setCancelled(true);
                        return;
                    }
                }
                hits.putIfAbsent(damager, 0);
                hits.put(damager, hits.get(damager) + 1);

                if (hits.get(damager) == xAbilities.config.getConfig().getInt("PotionCounter.Hits")) {

                    for (String list : xAbilities.config.getConfig().getStringList("PotionCounter.Message.Hit-Someone")) {
                        damager.sendMessage(chatUtil.chat(list.replaceAll("%pots%", String.valueOf(AbilityEvents.getPots(damaged))).replaceAll("%player%", damaged.getName())).replaceAll("%ability%", xAbilities.config.getConfig().getString("ExoticBone.Name")));
                    }
                    Cooldowns.getPotioncounter().applyCooldown(damager, xAbilities.config.getConfig().getInt("PotionCounter.Cooldown") * 1000);
                    if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
                        Cooldowns.getAbilitycd().applyCooldown(damager, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
                    }
                    AbilityEvents.takeItem(damager, damager.getItemInHand());
                    hits.remove(damager);
                    damager.updateInventory();
                }
                return;
            }
        }
    }

}
