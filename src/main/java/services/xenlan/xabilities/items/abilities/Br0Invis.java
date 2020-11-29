package services.xenlan.xabilities.items.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.*;

public class Br0Invis implements Listener {

    private AbilityEvents item = AbilityEvents.BROINVIS;
    private Map<UUID, ItemStack[]> inventories = new HashMap<>();
    private Map<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onBroinvis(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (!event.getAction().name().contains("RIGHT"))
            return;

        if (!item.isSimilar(p.getItemInHand()))
            return;

        if (Cooldowns.getAbilitycd().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Ability.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("BelchBomb.Name")).replace("%time%", Cooldowns.getAbilitycd().getRemaining(p))));
                event.setCancelled(true);
                return;
            }
        }
        if (Cooldowns.getBroinvis().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("BroInvis.Name")).replace("%time%", Cooldowns.getBroinvis().getRemaining(p))));
                event.setCancelled(true);
                return;
            }
        }
        AbilityEvents.takeItem(p, p.getItemInHand());
        Cooldowns.getBroinvis().applyCooldown(p, xAbilities.config.getConfig().getInt("BroInvis.Cooldown") * 1000);
        Cooldowns.getAbilitycd().applyCooldown(p, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
        for (String list : xAbilities.config.getConfig().getStringList("BroInvis.Message.Used")) {
            p.sendMessage(chatUtil.chat(list));
        }

        int time = xAbilities.config.getConfig().getInt("BroInvis.Invis-Time");

        new BukkitRunnable() {
            @Override
            public void run() {
                p.getInventory().setArmorContents(inventories.get(p.getUniqueId()));

                inventories.remove(p.getUniqueId());

            }
        }.runTaskLater(xAbilities.getInstance(), time * 20);

        inventories.put(p.getUniqueId(), p.getInventory().getArmorContents());
        cooldown.put(p.getUniqueId(), (long) (time * 1000));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, time * 20 ,10), true);
        p.getInventory().setArmorContents(null);

    }

    @EventHandler
    public void onHitbro(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (!(event.getDamager() instanceof Player))
            return;

        Player damaged = (Player) event.getEntity();

        if (cooldown.containsKey(damaged.getUniqueId())) {

            damaged.getInventory().setArmorContents(inventories.get(damaged.getUniqueId()));

            for (String list : xAbilities.config.getConfig().getStringList("BroInvis.Message.Been-Hit")) {
                damaged.sendMessage(chatUtil.chat(list));
            }
            damaged.updateInventory();
            inventories.remove(damaged.getUniqueId());
            cooldown.remove(damaged.getUniqueId());
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getBroinvis().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("BroInvis.Name")).replace("%time%", Cooldowns.getBroinvis().getRemaining(event.getPlayer()))));
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
