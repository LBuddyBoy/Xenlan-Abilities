package services.xenlan.xabilities.items.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.HashMap;
import java.util.Map;

public class ExoticBone implements Listener {

    private Map<Player, Integer> hits = new HashMap<>();
    private AbilityEvents item = AbilityEvents.EXOTICBONE;
    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getBone().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("ExoticBone.Name")).replace("%time%", Cooldowns.getBone().getRemaining(event.getPlayer()))));
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
                if (Cooldowns.getBone().onCooldown(damager)) {
                    for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                        damager.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("ExoticBone.Name")).replace("%time%", Cooldowns.getBone().getRemaining(damager))));
                        event.setCancelled(true);
                        return;
                    }
                }

                hits.putIfAbsent(damager, 0);
                hits.put(damager, hits.get(damager) + 1);

                if (hits.get(damager) == xAbilities.config.getConfig().getInt("ExoticBone.Hits")) {
                    for (String list : xAbilities.config.getConfig().getStringList("ExoticBone.Message.Been-Hit")) {
                        damaged.sendMessage(chatUtil.chat(list.replaceAll("%player%", damager.getName()).replaceAll("%time%", String.valueOf(xAbilities.config.getConfig().getInt("ExoticBone.Message.Been-Hit")))));
                    }
                    for (String list : xAbilities.config.getConfig().getStringList("ExoticBone.Message.Hit-Someone")) {
                        damager.sendMessage(chatUtil.chat(list.replaceAll("%player%", damaged.getName())).replaceAll("%ability%", xAbilities.config.getConfig().getString("ExoticBone.Name")));
                    }
                    Cooldowns.getIsboned().applyCooldown(damaged, xAbilities.config.getConfig().getInt("ExoticBone.Cooldown.Damaged") * 1000);
                    Cooldowns.getBone().applyCooldown(damager, xAbilities.config.getConfig().getInt("ExoticBone.Cooldown.Damager") * 1000);
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

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockPlaceEvent event) {
        if (Cooldowns.getIsboned().onCooldown(event.getPlayer())) {
            for (String list : xAbilities.config.getConfig().getStringList("ExoticBone.Message.Cant-Build")) {
                event.getPlayer().sendMessage(chatUtil.chat(list).replace("%time%", Cooldowns.getIsboned().getRemaining(event.getPlayer())));
            }
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent event) {
        if (Cooldowns.getIsboned().onCooldown(event.getPlayer())) {
            for (String list : xAbilities.config.getConfig().getStringList("ExoticBone.Message.Cant-Build")) {
                event.getPlayer().sendMessage(chatUtil.chat(list).replace("%time%", Cooldowns.getIsboned().getRemaining(event.getPlayer())));
            }
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (Cooldowns.getIsboned().onCooldown(player)) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getClickedBlock().getType() == Material.CHEST
                        || event.getClickedBlock().getType() == Material.TRAP_DOOR
                        || event.getClickedBlock().getType() == Material.WOOD_DOOR
                        || event.getClickedBlock().getType() == Material.WOODEN_DOOR
                        || event.getClickedBlock().getType() == Material.TRAPPED_CHEST
                        || event.getClickedBlock().getType() == Material.FENCE_GATE) {
                    if (event.getClickedBlock() == null)
                        return;

                    for (String list : xAbilities.config.getConfig().getStringList("ExoticBone.Message.Cant-Build")) {
                        event.getPlayer().sendMessage(chatUtil.chat(list).replace("%time%", Cooldowns.getIsboned().getRemaining(event.getPlayer())));
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
}
