package services.xenlan.xabilities.items.refund;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.build.ItemBuilder;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.ArrayList;
import java.util.List;

public class Refund implements Listener {

    @EventHandler
    public void onRottenEgg(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();

        List<String> lore = new ArrayList<>();
        for (String list : xAbilities.config.getConfig().getStringList("RottenEgg.Lore")) {
            lore.add(chatUtil.chat(list));
        }
        ItemStack stack = new ItemBuilder(Material.valueOf(xAbilities.config.getConfig().getString("RottenEgg.Item")), 1).displayName(
                chatUtil.chat(xAbilities.config.getConfig().getString("RottenEgg.Name")))
                .setLore(lore).build();

        if (projectile.getShooter() instanceof Skeleton)
            return;
        if (projectile.getShooter() instanceof Blaze)
            return;

        Player player = (Player) projectile.getShooter();

        if (!xAbilities.getEggRefunds().containsKey(player))
            return;
        if (event.getEntity() == null)
            return;

        if (!(projectile.hasMetadata("rottenegg")))
            return;
        if (xAbilities.config.getConfig().getBoolean("Refund.Enabled")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    xAbilities.getEggRefunds().remove(player);
                    Cooldowns.getRefundcool().applyCooldown(player, xAbilities.config.getConfig().getInt("Refund.Cooldown") * 1000);
                    Cooldowns.getAbilitycd().removeCooldown(player);
                    Cooldowns.getRottenegg().removeCooldown(player);
                    player.getInventory().addItem(stack);
                    for (String list : xAbilities.config.getConfig().getStringList("Refund.Message.Refunded")) {
                        player.sendMessage(chatUtil.chat(list));
                    }
                }
            }.runTaskLater(xAbilities.getInstance(), 20);
        }
    }

    @EventHandler
    public void onMixerHit(ProjectileHitEvent event) {

        Projectile projectile = event.getEntity();

        List<String> lore = new ArrayList<>();
        for (String list : xAbilities.config.getConfig().getStringList("Mixer.Lore")) {
            lore.add(chatUtil.chat(list));
        }
        ItemStack stack = new ItemBuilder(Material.valueOf(xAbilities.config.getConfig().getString("Mixer.Item")), 1).displayName(
                chatUtil.chat(xAbilities.config.getConfig().getString("Mixer.Name")))
                .setLore(lore).build();

        if (projectile.getShooter() instanceof Skeleton)
            return;
        if (projectile.getShooter() instanceof Blaze)
            return;

        Player player = (Player) projectile.getShooter();
        if (!xAbilities.getEggRefunds().containsKey(player))
            return;
        if (event.getEntity() == null)
            return;

        if (!(projectile.hasMetadata("mixer")))
            return;
        if (xAbilities.config.getConfig().getBoolean("Refund.Enabled")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    xAbilities.getEggRefunds().remove(player);
                    Cooldowns.getRefundcool().applyCooldown(player, xAbilities.config.getConfig().getInt("Refund.Cooldown") * 1000);
                    Cooldowns.getAbilitycd().removeCooldown(player);
                    Cooldowns.getMixer().removeCooldown(player);
                    player.getInventory().addItem(stack);
                    for (String list : xAbilities.config.getConfig().getStringList("Refund.Message.Refunded")) {
                        player.sendMessage(chatUtil.chat(list));
                    }
                }
            }.runTaskLater(xAbilities.getInstance(), 20);
        }
    }
    @EventHandler
    public void onImpulseHit(ProjectileHitEvent event) {

        Projectile projectile = event.getEntity();

        List<String> lore = new ArrayList<>();
        for (String list : xAbilities.config.getConfig().getStringList("ImpulseBomb.Lore")) {
            lore.add(chatUtil.chat(list));
        }
        ItemStack stack = new ItemBuilder(Material.valueOf(xAbilities.config.getConfig().getString("ImpulseBomb.Item")),
                1).displayName(
                chatUtil.chat(xAbilities.config.getConfig().getString("ImpulseBomb.Name")))
                .setLore(lore).build();

        if (projectile.getShooter() instanceof Skeleton)
            return;
        if (projectile.getShooter() instanceof Blaze)
            return;

        Player player = (Player) projectile.getShooter();
        if (!xAbilities.getSnowballRefunds().containsKey(player))
            return;
        if (event.getEntity() == null)
            return;

        if (!(projectile.hasMetadata("impulsebomb")))
            return;

        if (xAbilities.config.getConfig().getBoolean("Refund.Enabled")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    xAbilities.getSnowballRefunds().remove(player);
                    Cooldowns.getRefundcool().applyCooldown(player, xAbilities.config.getConfig().getInt("Refund.Cooldown") * 1000);
                    Cooldowns.getAbilitycd().removeCooldown(player);
                    Cooldowns.getImpulsebomb().removeCooldown(player);
                    player.getInventory().addItem(stack);
                    for (String list : xAbilities.config.getConfig().getStringList("Refund.Message.Refunded")) {
                        player.sendMessage(chatUtil.chat(list));
                    }
                }
            }.runTaskLater(xAbilities.getInstance(), 20L);
        }
    }
    @EventHandler
    public void onSwitcherHit(ProjectileHitEvent event) {

        Projectile projectile = event.getEntity();

        List<String> lore = new ArrayList<>();
        for (String list : xAbilities.config.getConfig().getStringList("Switcher.Lore")) {
            lore.add(chatUtil.chat(list));
        }
        ItemStack stack = new ItemBuilder(Material.valueOf(xAbilities.config.getConfig().getString("Switcher.Item")), 1).displayName(
                chatUtil.chat(xAbilities.config.getConfig().getString("Switcher.Name")))
                .setLore(lore).build();

        if (projectile.getShooter() instanceof Skeleton)
            return;
        if (projectile.getShooter() instanceof Blaze)
            return;

        Player player = (Player) projectile.getShooter();
        if (!xAbilities.getSnowballRefunds().containsKey(player))
            return;
        if (event.getEntity() == null)
            return;

        if (!(projectile.hasMetadata("switcher")))
            return;

        if (xAbilities.config.getConfig().getBoolean("Refund.Enabled")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    xAbilities.getSnowballRefunds().remove(player);
                    Cooldowns.getRefundcool().applyCooldown(player, xAbilities.config.getConfig().getInt("Refund.Cooldown") * 1000);
                    Cooldowns.getAbilitycd().removeCooldown(player);
                    Cooldowns.getSwitcher().removeCooldown(player);
                    player.getInventory().addItem(stack);
                    for (String list : xAbilities.config.getConfig().getStringList("Refund.Message.Refunded")) {
                        player.sendMessage(chatUtil.chat(list));
                    }
                }
            }.runTaskLater(xAbilities.getInstance(), 20L);
        }
    }

    @EventHandler
    public void onChicken(EntitySpawnEvent event) {
        if (event.getEntityType().equals(EntityType.CHICKEN)) {
            event.setCancelled(true);
        } else {
            return;
        }
    }

}
