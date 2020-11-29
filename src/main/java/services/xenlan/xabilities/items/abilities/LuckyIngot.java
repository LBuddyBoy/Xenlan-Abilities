package services.xenlan.xabilities.items.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.Random;

public class LuckyIngot implements Listener {
    private AbilityEvents item = AbilityEvents.LUCKYINGOT;

    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getLuckyingot().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("LuckyIngot.Name")).replace("%time%", Cooldowns.getLuckyingot().getRemaining(event.getPlayer()))));
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

        Random ran = new Random();
        int choice = ran.nextInt(100) + 1;
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
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("ExoticBone.Name")).replace("%time%", Cooldowns.getAbilitycd().getRemaining(p))));
                event.setCancelled(true);
                p.updateInventory();
                return;
            }
        }
        if (Cooldowns.getLuckyingot().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("LuckyIngot.Name")).replace("%time%", Cooldowns.getLuckyingot().getRemaining(p))));
                event.setCancelled(true);
                p.updateInventory();
                return;
            }
        }
        if (choice < xAbilities.config.getConfig().getInt("LuckyIngot.Positive-Chance")) {
            for (String list : xAbilities.config.getConfig().getStringList("LuckyIngot.Message.Positive")) {
                p.sendMessage(chatUtil.chat(list));
            }
            Cooldowns.getLuckyingot().applyCooldown(p, xAbilities.config.getConfig().getInt("LuckyIngot.Cooldown") * 1000);
            if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
                Cooldowns.getAbilitycd().applyCooldown(p, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
            }
            for (String section : xAbilities.config.getConfig().getConfigurationSection("LuckyIngot.Effects.Positive").getKeys(false)) {

                int power = xAbilities.config.getConfig().getInt("LuckyIngot.Effects.Positive." + section + ".Power");
                int time = xAbilities.config.getConfig().getInt("LuckyIngot.Effects.Positive." + section + ".Time");
                String type = xAbilities.config.getConfig().getString("LuckyIngot.Effects.Positive." + section + ".Type");

                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.getByName(type), time * 20, power - 1));
            }
            AbilityEvents.takeItem(p, p.getItemInHand());
            p.updateInventory();

        } else {
            for (String list : xAbilities.config.getConfig().getStringList("LuckyIngot.Message.Negative")) {
                p.sendMessage(chatUtil.chat(list));
            }
            Cooldowns.getLuckyingot().applyCooldown(p, xAbilities.config.getConfig().getInt("LuckyIngot.Cooldown") * 1000);
            if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
                Cooldowns.getAbilitycd().applyCooldown(p, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
            }
            for (String section : xAbilities.config.getConfig().getConfigurationSection("LuckyIngot.Effects.Negative").getKeys(false)) {

                int power = xAbilities.config.getConfig().getInt("LuckyIngot.Effects.Negative." + section + ".Power");
                int time = xAbilities.config.getConfig().getInt("LuckyIngot.Effects.Negative." + section + ".Time");
                String type = xAbilities.config.getConfig().getString("LuckyIngot.Effects.Negative." + section + ".Type");

                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.getByName(type), time * 20, power - 1));
            }
            AbilityEvents.takeItem(p, p.getItemInHand());
            p.updateInventory();
        }
    }
}
