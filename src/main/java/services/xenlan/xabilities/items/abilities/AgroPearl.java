package services.xenlan.xabilities.items.abilities;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

public class AgroPearl implements Listener {
    AbilityEvents item = AbilityEvents.AGROPEARL;

    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getItem() == null)
                return;

            if (Cooldowns.getAgropearl().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("AgroPearl.Name")).replace("%time%", Cooldowns.getAgropearl().getRemaining(event.getPlayer()))));
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler

    public void onClick(PlayerInteractEvent event) {


        Player p = event.getPlayer();

        Inventory i;


        if (!event.getAction().name().contains("RIGHT"))
            return;

        if (event.getItem() == null)
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
        if (Cooldowns.getAgropearl().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("AgroPearl.Name")).replace("%time%", Cooldowns.getAgropearl().getRemaining(p))));
                event.setCancelled(true);
                event.setUseItemInHand(Event.Result.DENY);
                p.updateInventory();
                return;
            }
        }
        AbilityEvents.takeItem(p, p.getItemInHand());
        Cooldowns.getAgropearl().applyCooldown(p, xAbilities.config.getConfig().getInt("AgroPearl.Cooldown") * 1000);
        if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
            Cooldowns.getAbilitycd().applyCooldown(p, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
        }
        Cooldowns.getPreagropearl().applyCooldown(p, 20 * 1000);
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player)) {
            return;
        }

        Projectile entity = e.getEntity();
        Player player = (Player) entity.getShooter();

        if (!item.isSimilar(player.getItemInHand())) {
            return;
        }

        if (entity instanceof EnderPearl) {
            EnderPearl pearl = (EnderPearl) entity;
            pearl.setMetadata("agropearl", new FixedMetadataValue(xAbilities.getInstance(), player.getUniqueId()));
        }
    }

    @EventHandler
    public void onLand(PlayerTeleportEvent event) {
        if (Cooldowns.getPreagropearl().onCooldown(event.getPlayer())) {
            if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
                Cooldowns.getPreagropearl().removeCooldown(event.getPlayer());
                for (String list : xAbilities.config.getConfig().getStringList("AgroPearl.Message.Landed")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list));
                }
                for (String section : xAbilities.config.getConfig().getConfigurationSection("AgroPearl.Effects").getKeys(false)) {

                    int power = xAbilities.config.getConfig().getInt("AgroPearl.Effects." + section + ".Power");
                    int time = xAbilities.config.getConfig().getInt("AgroPearl.Effects." + section + ".Time");
                    String type = xAbilities.config.getConfig().getString("AgroPearl.Effects." + section + ".Type");
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.getByName(type), time * 20, power - 1));
                }
            }
            if (!Cooldowns.getPreagropearl().onCooldown(event.getPlayer())) {
                if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
                    event.setCancelled(false);
                }
            }
        }

    }

}
