package services.xenlan.xabilities.items.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.ArrayList;

public class ImpulseBomb implements Listener {

    AbilityEvents item = AbilityEvents.IMPULSE;

    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getImpulsebomb().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("ImpulseBomb.Name")).replace("%time%", Cooldowns.getImpulsebomb().getRemaining(event.getPlayer()))));
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void SwitcherThrown(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (!event.getAction().name().contains("RIGHT"))
            return;

        if (!item.isSimilar(player.getItemInHand()))
            return;

        if (Cooldowns.getAbilitycd().onCooldown(player)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Ability.OnCooldown")) {
                player.sendMessage(chatUtil.chat(list.replace("%time%", Cooldowns.getAbilitycd().getRemaining(player))));
                event.setUseItemInHand(Event.Result.DENY);
                event.setCancelled(true);
                player.updateInventory();
                return;
            }
        }


        if (Cooldowns.getRefundcool().onCooldown(player)) {
            for (String list : xAbilities.config.getConfig().getStringList("Refund.Message.Cooldown")) {
                player.sendMessage(chatUtil.chat(list.replace("%time%", Cooldowns.getRefundcool().getRemaining(player))));
                event.setUseItemInHand(Event.Result.DENY);
                event.setCancelled(true);
                player.updateInventory();
                return;
            }
        }
        if (Cooldowns.getImpulsebomb().onCooldown(player)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                player.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("ImpulseBomb.Name")).replace("%time%", Cooldowns.getImpulsebomb().getRemaining(player))));
                event.setUseItemInHand(Event.Result.DENY);
                event.setCancelled(true);
                player.updateInventory();
                return;
            }
        }
        Cooldowns.getRefundcool().removeCooldown(player);
        Cooldowns.getAbilitycd().applyCooldown(player, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
        Cooldowns.getImpulsebomb().applyCooldown(player, xAbilities.config.getConfig().getInt("ImpulseBomb.Cooldown") * 1000);
        Snowball snowball = event.getPlayer().launchProjectile(Snowball.class);
        snowball.setMetadata("impulsebomb", new FixedMetadataValue(xAbilities.getInstance(), player.getUniqueId()));
        player.updateInventory();
        AbilityEvents.takeItem(player, player.getItemInHand());
        xAbilities.getSnowballRefunds().put(player, snowball);
        event.setCancelled(true);
    }

    @EventHandler
    public void SwitcherHit(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Snowball) {
                Projectile snowball = (Projectile) event.getDamager();
                Player damaged = (Player) event.getEntity();
                Player damager = (Player) snowball.getShooter();

                if (item.isSimilar(damager.getItemInHand())) {
                    if (AbilityEvents.checkLocation(damager.getLocation(), xAbilities.config.getConfig().getInt("SPAWN.Radius"), 0, 0)) {
                        for (String list : xAbilities.config.getConfig().getStringList("SPAWN.CantUse")) {
                            damager.sendMessage(chatUtil.chat(list));
                        }
                        event.setCancelled(true);
                        return;
                    }
                }
                if (item.isSimilar(damaged.getItemInHand())) {
                    if (AbilityEvents.checkLocation(damaged.getLocation(), xAbilities.config.getConfig().getInt("SPAWN.Radius"), 0, 0)) {
                        for (String list : xAbilities.config.getConfig().getStringList("SPAWN.CantUse")) {
                            damaged.sendMessage(chatUtil.chat(list));
                        }
                        event.setCancelled(true);
                        return;
                    }
                }
                if (!snowball.hasMetadata("impulsebomb"))
                    return;

                if (xAbilities.getSnowballRefunds().containsKey(damager)) {
                    xAbilities.getSnowballRefunds().remove(damager);
                    double x = xAbilities.config.getConfig().getDouble("ImpulseBomb.Knockback.X");
                    double y = xAbilities.config.getConfig().getDouble("ImpulseBomb.Knockback.Y");
                    double z = xAbilities.config.getConfig().getDouble("ImpulseBomb.Knockback.Z");
                    damaged.setVelocity(damaged.getEyeLocation().getDirection().multiply(xAbilities.config.getConfig().getDouble("ImpulseBomb.Knockback")));
                    //damaged.setVelocity(new Vector(
                    //        damager.getLocation().getDirection().getX() * x, y, damager.getLocation().getDirection().getZ() *  z));
                    for (String list : xAbilities.config.getConfig().getStringList("ImpulseBomb.Message.Been-Hit")) {
                        damaged.sendMessage(chatUtil.chat(list).replaceAll("%player%", damager.getName()));
                    }
                    for (String list : xAbilities.config.getConfig().getStringList("ImpulseBomb.Message.Hit-Someone")) {
                        damager.sendMessage(chatUtil.chat(list).replaceAll("%player%", damaged.getName()));
                    }
                }
            }
        }
    }

}
