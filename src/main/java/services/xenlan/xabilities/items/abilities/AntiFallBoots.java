package services.xenlan.xabilities.items.abilities;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.Random;

public class AntiFallBoots implements Listener {

    AbilityEvents item = AbilityEvents.ANTIFALL;

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL && item.isSimilar(((Player) entity).getInventory().getBoots())) {
            Player player = (Player) entity;
            Random ran = new Random();
            int choice = ran.nextInt(100) + 1;
            if (Cooldowns.getAbilitycd().onCooldown(player)) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Ability.OnCooldown")) {
                    player.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("ExoticBone.Name")).replace("%time%", Cooldowns.getAbilitycd().getRemaining(player))));
                    event.setCancelled(false);
                    player.updateInventory();
                }
                return;
            }
            if (Cooldowns.getAntifall().onCooldown(player)) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    player.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("AntiFall.Name")).replace("%time%", Cooldowns.getAntifall().getRemaining(player))));
                    event.setCancelled(false);
                    player.updateInventory();
                }
                return;
            }
            if (choice > xAbilities.config.getConfig().getInt("AntiFall.Chance")) {
                if (xAbilities.config.getConfig().getBoolean("AntiFall.Cooldown.Enabled-Success")) {
                    Cooldowns.getAntifall().applyCooldown(player, xAbilities.config.getConfig().getInt("AntiFall.Cooldown.Time") * 1000);
                    if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
                        Cooldowns.getAbilitycd().applyCooldown(player, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
                    }
                    for (String list : xAbilities.config.getConfig().getStringList("AntiFall.Message.Success")) {
                        player.sendMessage(chatUtil.chat(list));
                    }
                    event.setCancelled(true);
                }
                return;
            }
            if (choice < xAbilities.config.getConfig().getInt("AntiFall.Chance")) {
                if (xAbilities.config.getConfig().getBoolean("AntiFall.Cooldown.Enabled-Failed")) {
                    Cooldowns.getAntifall().applyCooldown(player, xAbilities.config.getConfig().getInt("AntiFall.Cooldown.Time") * 1000);
                    if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
                        Cooldowns.getAbilitycd().applyCooldown(player, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
                    }
                    for (String list : xAbilities.config.getConfig().getStringList("AntiFall.Message.Failed")) {
                        player.sendMessage(chatUtil.chat(list));
                    }
                    event.setCancelled(false);
                }
            }
        }
    }

}

