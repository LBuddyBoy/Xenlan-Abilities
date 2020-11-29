package services.xenlan.xabilities.items.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.HashMap;
import java.util.Map;

public class AntiRedstone implements Listener {

    private Map<Player, Integer> hits = new HashMap<>();
    private AbilityEvents item = AbilityEvents.ANTIREDSTONE;

    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getAntiredstone().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("AntiRedstone.Name")).replace("%time%", Cooldowns.getAntiredstone().getRemaining(event.getPlayer()))));
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
                        damager.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("AntiRedstone.Name")).replace("%time%", Cooldowns.getAbilitycd().getRemaining(damager))));
                        event.setCancelled(true);
                        return;
                    }
                }
                if (Cooldowns.getAntiredstone().onCooldown(damager)) {
                    for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                        damager.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("AntiRedstone.Name")).replace("%time%", Cooldowns.getAntiredstone().getRemaining(damager))));
                        event.setCancelled(true);
                        return;
                    }
                }
                hits.putIfAbsent(damager, 0);
                hits.put(damager, hits.get(damager) + 1);

                if (hits.get(damager) == xAbilities.config.getConfig().getInt("AntiRedstone.Hits")) {
                    for (String list : xAbilities.config.getConfig().getStringList("AntiRedstone.Message.Been-Hit")) {
                        damaged.sendMessage(chatUtil.chat(list.replaceAll("%player%", damager.getName()).replaceAll("%time%", String.valueOf(xAbilities.config.getConfig().getInt("ExoticBone.Message.Been-Hit")))));
                    }
                    for (String list : xAbilities.config.getConfig().getStringList("AntiRedstone.Message.Hit-Someone")) {
                        damager.sendMessage(chatUtil.chat(list.replaceAll("%player%", damaged.getName())).replaceAll("%ability%", xAbilities.config.getConfig().getString("ExoticBone.Name")));
                    }
                    Cooldowns.getIsantiredstoned().applyCooldown(damaged, xAbilities.config.getConfig().getInt("AntiRedstone.Cooldown.Damaged") * 1000);
                    Cooldowns.getAntiredstone().applyCooldown(damager, xAbilities.config.getConfig().getInt("AntiRedstone.Cooldown.Damager") * 1000);
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

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if (Cooldowns.getIsantiredstoned().onCooldown(p)) {
            if (event.getBlockPlaced().getType() == Material.REDSTONE_BLOCK
                    || event.getBlockPlaced().getType() == Material.REDSTONE_TORCH_ON
                    || event.getBlockPlaced().getType() == Material.REDSTONE_TORCH_OFF
                    || event.getBlockPlaced().getType() == Material.REDSTONE
                    || event.getBlockPlaced().getType() == Material.REDSTONE_WIRE) {
                if (event.getBlockPlaced() == null)
                    return;

                for (String list : xAbilities.config.getConfig().getStringList("AntiRedstone.Message.Cant-Build")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list).replace("%time%", Cooldowns.getIsantiredstoned().getRemaining(event.getPlayer())));
                }
                event.setCancelled(true);
            } else {
                return;
            }
        }
    }

    @EventHandler
    public void onPressure(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.PHYSICAL)) {
            if (Cooldowns.getIsantiredstoned().onCooldown(event.getPlayer())) {
                if (event.getClickedBlock().getType() == Material.STONE_PLATE ||
                        event.getClickedBlock().getType() == Material.WOOD_PLATE ||
                        event.getClickedBlock().getType() == Material.GOLD_PLATE ||
                        event.getClickedBlock().getType() == Material.IRON_PLATE) {

                    for (String list : xAbilities.config.getConfig().getStringList("AntiRedstone.Message.Cant-Build")) {
                        event.getPlayer().sendMessage(chatUtil.chat(list).replace("%time%", Cooldowns.getIsantiredstoned().getRemaining(event.getPlayer())));
                    }
                    event.setCancelled(true);
                }
            } else {
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (Cooldowns.getIsantiredstoned().onCooldown(player)) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getClickedBlock().getType() == Material.LEVER
                        || event.getClickedBlock().getType() == Material.STONE_BUTTON
                        || event.getClickedBlock().getType() == Material.WOOD_BUTTON
                        || event.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                    if (event.getClickedBlock() == null)
                        return;

                    for (String list : xAbilities.config.getConfig().getStringList("AntiRedstone.Message.Cant-Build")) {
                        event.getPlayer().sendMessage(chatUtil.chat(list).replace("%time%", Cooldowns.getIsantiredstoned().getRemaining(event.getPlayer())));
                    }
                    event.setCancelled(true);
                } else {
                    return;
                }
            }
        }
    }

}
