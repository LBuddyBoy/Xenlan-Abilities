package services.xenlan.xabilities.items.abilities;

import org.bukkit.Material;
import org.bukkit.World;
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

public class SwapperAxe implements Listener {

    private AbilityEvents item = AbilityEvents.SWAPPERAXE;

    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getSwapperaxe().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("SwapperAxe.Name")).replace("%time%", Cooldowns.getSwapperaxe().getRemaining(event.getPlayer()))));
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onSwapper(EntityDamageByEntityEvent event) {

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
                        damager.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("ExoticBone.Name")).replace("%time%", Cooldowns.getAbilitycd().getRemaining(damager))));
                        event.setCancelled(true);
                        return;
                    }
                }
                if (Cooldowns.getSwapperaxe().onCooldown(damager)) {
                    for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                        damager.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("SwapperAxe.Name")).replace("%time%", Cooldowns.getSwapperaxe().getRemaining(damager))));
                        event.setCancelled(true);
                        return;
                    }
                }

                Cooldowns.getSwapperaxe().applyCooldown(damager, xAbilities.config.getConfig().getInt("SwapperAxe.Cooldown") * 1000);
                if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
                    Cooldowns.getAbilitycd().applyCooldown(damager, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
                }

                if (damaged.getInventory().getHelmet() == null) {
                    for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.No-Helmet-Damaged")) {
                        damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                                xAbilities.config.getConfig().getInt("SwapperAxe.Effect.Time") * 23
                                , xAbilities.config.getConfig().getInt("SwapperAxe.Effect.Power")));
                        damaged.sendMessage(chatUtil.chat(list).replace("%player%", damager.getName()));
                    }
                    for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.No-Helmet-Damager")) {
                        damager.sendMessage(chatUtil.chat(list).replace("%player%", damaged.getName()));
                    }
                    Cooldowns.getSwapperaxe().applyCooldown(damager, xAbilities.config.getConfig().getInt("SwapperAxe.Cooldown") * 1000);

                    return;
                }

                if (damaged.getInventory().getHelmet().getType() != Material.DIAMOND_HELMET) {
                    for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.No-Helmet-Damaged")) {
                        damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, xAbilities.config.getConfig().getInt("SwapperAxe.Effect.Time") * 23, xAbilities.config.getConfig().getInt("SwapperAxe.Effect.Power")));
                        damaged.sendMessage(chatUtil.chat(list).replace("%player%", damager.getName()));
                    }
                    Cooldowns.getSwapperaxe().applyCooldown(damager, xAbilities.config.getConfig().getInt("SwapperAxe.Cooldown") * 1000);
                    return;
                }

                for (String s : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.Been-Hit")) {
                    damaged.sendMessage(chatUtil.chat(s).replace("%player%", damager.getName()));
                }
                for (String s : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.Hit-Someone")) {
                    damager.sendMessage(chatUtil.chat(s).replace("%player%", damaged.getName()));
                }
                if (xAbilities.config.getConfig().getBoolean("SwapperAxe.Delay.Enabled")) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!(damaged.getInventory().firstEmpty() == -1)) {
                                damaged.getInventory().addItem(damaged.getInventory().getHelmet());
                                damaged.getInventory().setHelmet(null);
                                for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.Been-Hit")) {
                                    damaged.sendMessage(chatUtil.chat(list).replace("%player%", damager.getName()));
                                }
                                Cooldowns.getSwapperaxe().applyCooldown(damager, xAbilities.config.getConfig().getInt("SwapperAxe.Cooldown") * 1000);
                                for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.Hit-Someone")) {
                                    damager.sendMessage(chatUtil.chat(list).replace("%player%", damaged.getName()));
                                }
                            } else {
                                World world = damaged.getWorld();
                                world.dropItem(damaged.getLocation(), damaged.getInventory().getHelmet());
                                damaged.getInventory().setHelmet(null);
                                for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.Been-Hit")) {
                                    damaged.sendMessage(chatUtil.chat(list).replace("%player%", damager.getName()));
                                }
                                for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.Full-Inv")) {
                                    damaged.sendMessage(chatUtil.chat(list).replace("%player%", damager.getName()));
                                }
                                for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.Hit-Someone")) {
                                    damager.sendMessage(chatUtil.chat(list).replace("%player%", damaged.getName()));
                                }
                                Cooldowns.getSwapperaxe().applyCooldown(damager, xAbilities.config.getConfig().getInt("SwapperAxe.Cooldown") * 1000);

                            }
                        }
                    }.runTaskLater(xAbilities.getInstance(), 20L * xAbilities.config.getConfig().getInt("SwapperAxe.Delay.Time"));
                }
                if (xAbilities.config.getConfig().getString("SwapperAxe.Delay.Enabled").equalsIgnoreCase("false")) {
                    if (!(damaged.getInventory().firstEmpty() == -1)) {
                        damaged.getInventory().addItem(damaged.getInventory().getHelmet());
                        damaged.getInventory().setHelmet(null);
                        for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.Been-Hit")) {
                            damaged.sendMessage(chatUtil.chat(list).replace("%player%", damager.getName()));
                            Cooldowns.getSwapperaxe().applyCooldown(damager, xAbilities.config.getConfig().getInt("SwapperAxe.Cooldown") * 1000);

                        }
                    } else {
                        World world = damaged.getWorld();
                        damaged.getInventory().setHelmet(null);
                        world.dropItem(damaged.getLocation(), damaged.getInventory().getHelmet());
                        Cooldowns.getSwapperaxe().applyCooldown(damager, xAbilities.config.getConfig().getInt("SwapperAxe.Cooldown") * 1000);

                        for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.Been-Hit")) {
                            damaged.sendMessage(chatUtil.chat(list).replace("%player%", damager.getName()));
                        }
                        for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.Full-Inv")) {
                            damaged.sendMessage(chatUtil.chat(list).replace("%player%", damager.getName()));
                        }
                        for (String list : xAbilities.config.getConfig().getStringList("SwapperAxe.Message.Hit-Someone")) {
                            damager.sendMessage(chatUtil.chat(list).replace("%player%", damaged.getName()));
                        }
                    }
                }
            }
        }
    }

}
