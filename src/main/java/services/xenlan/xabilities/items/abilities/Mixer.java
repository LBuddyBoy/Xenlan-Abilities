package services.xenlan.xabilities.items.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.*;

public class Mixer implements Listener {

    private AbilityEvents item = AbilityEvents.MIXER;
    Map<Player, ItemStack> stackmeta2 = new HashMap<>();
    Map<Player, ItemStack> stackmeta3 = new HashMap<>();
    Map<Player, Integer> stackmeta4 = new HashMap<>();

    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getMixer().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("Mixer.Name")).replace("%time%", Cooldowns.getMixer().getRemaining(event.getPlayer()))));
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
        if (Cooldowns.getRefundcool().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Refund.Message.Cooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%time%", Cooldowns.getRefundcool().getRemaining(p))));
                event.setUseItemInHand(Event.Result.DENY);
                event.setCancelled(true);
                p.updateInventory();
                return;
            }
        }
        if (Cooldowns.getAbilitycd().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Ability.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%time%", Cooldowns.getAbilitycd().getRemaining(p))));
                event.setCancelled(true);
                p.updateInventory();
                return;
            }
        }
        if (Cooldowns.getMixer().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("Mixer.Name")).replace("%time%", Cooldowns.getMixer().getRemaining(p))));
                event.setCancelled(true);
                p.updateInventory();
                return;
            }
        }
        if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
            Cooldowns.getAbilitycd().applyCooldown(p, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
        }
        Cooldowns.getRefundcool().removeCooldown(p);
        AbilityEvents.takeItem(p, p.getItemInHand());
        Cooldowns.getMixer().applyCooldown(p, xAbilities.config.getConfig().getInt("Mixer.Cooldown") * 1000);
        Egg snowball = event.getPlayer().launchProjectile(Egg.class);
        snowball.setMetadata("mixer", new FixedMetadataValue(xAbilities.getInstance(), p.getUniqueId()));
        xAbilities.getEggRefunds().put(p, snowball);
        event.setCancelled(true);
    }

    Set<ItemStack> items = new HashSet<>();
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {

        ItemStack stack = new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("Mixer.Item")), 1);
        ItemMeta stackmeta = stack.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        for (String list : xAbilities.config.getConfig().getStringList("Mixer.Lore")) {
            lore.add(chatUtil.chat(list));
        }
        stackmeta.setLore(lore);
        stackmeta.setDisplayName(chatUtil.chat(xAbilities.config.getConfig().getString("Mixer.Name")));
        stack.setItemMeta(stackmeta);

        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Egg) {
                Egg snowball = (Egg) event.getDamager();
                Player damager = (Player) snowball.getShooter();
                Player damaged = (Player) event.getEntity();
                if (!item.isSimilar(damager.getItemInHand()))
                    return;
                if (!snowball.hasMetadata("mixer"))
                    return;
                if (item.isSimilar(damaged.getItemInHand())) {
                    if (AbilityEvents.checkLocation(damaged.getLocation(), xAbilities.config.getConfig().getInt("SPAWN.Radius"), 0, 0)) {
                        for (String list : xAbilities.config.getConfig().getStringList("SPAWN.CantUse")) {
                            damaged.sendMessage(chatUtil.chat(list));
                        }
                        event.setCancelled(true);
                        return;
                    }
                }
                if (item.isSimilar(damager.getItemInHand())) {
                    if (AbilityEvents.checkLocation(damager.getLocation(), xAbilities.config.getConfig().getInt("SPAWN.Radius"), 0, 0)) {
                        for (String list : xAbilities.config.getConfig().getStringList("SPAWN.CantUse")) {
                            damager.sendMessage(chatUtil.chat(list));
                        }
                        event.setCancelled(true);
                        return;
                    }
                }

                Random random = new Random();
                int chance = random.nextInt(8);

                if (xAbilities.getEggRefunds().containsKey(damager)) {
                    if (chance == 8) {

                    } else {
                        xAbilities.getEggRefunds().remove(damager);
                        stackmeta2.put(damaged, damaged.getInventory().getItemInHand());
                        stackmeta4.put(damaged, chance);
                        stackmeta3.put(damaged, damaged.getInventory().getItem(1));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                stackmeta3.put(damaged, damaged.getInventory().getItem(stackmeta4.get(damaged)));
                                damaged.setItemInHand(null);
                                damaged.getInventory().setItem(stackmeta4.get(damaged), null);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        damaged.setItemInHand(stackmeta3.get(damaged));
                                        damaged.getInventory().setItem(chance, stackmeta3.get(damaged));
                                        damaged.getInventory().setItem(stackmeta4.get(damaged), stackmeta2.get(damaged));
                                        stackmeta3.remove(damaged);
                                        stackmeta2.remove(damaged);
                                        stackmeta4.remove(damaged);
                                    }
                                }.runTaskLater(xAbilities.getInstance(), 2L);
                            }
                        }.runTaskLater(xAbilities.getInstance(), 2L);
                    }
                }
            }
        }
    }

}
