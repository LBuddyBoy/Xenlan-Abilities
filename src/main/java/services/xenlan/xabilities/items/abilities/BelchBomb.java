package services.xenlan.xabilities.items.abilities;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.items.events.BelchBombEvent;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.List;

public class BelchBomb implements Listener {

    private AbilityEvents item = AbilityEvents.BELCHBOMB;
    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getBelchbomb().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("BelchBomb.Name")).replace("%time%", Cooldowns.getBelchbomb().getRemaining(event.getPlayer()))));
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
    @EventHandler
    public void onWither(PlayerInteractEvent event) {
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
        if (Cooldowns.getBelchbomb().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("BelchBomb.Name")).replace("%time%", Cooldowns.getBelchbomb().getRemaining(p))));
                event.setCancelled(true);
                return;
            }
        }

        if (BelchBombEvent.getNearbyPlayers(p).isEmpty()) {
            for (String list : xAbilities.config.getConfig().getStringList("BelchBomb.Message.None-Near")) {
                p.sendMessage(chatUtil.chat(list));
            }
            event.setCancelled(true);
            p.updateInventory();
        }

        Cooldowns.getBelchbomb().applyCooldown(p, xAbilities.config.getConfig().getInt("BelchBomb.Cooldown") * 1000);
        if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
            Cooldowns.getAbilitycd().applyCooldown(p, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
        }

        for (String list : xAbilities.config.getConfig().getStringList("BelchBomb.Message.Used")) {
            p.sendMessage(chatUtil.chat(list));
        }
        AbilityEvents.takeItem(p, p.getItemInHand());
        p.updateInventory();

        List<Entity> nearby = p.getNearbyEntities(BelchBombEvent.BELCH_RANGE, BelchBombEvent.BELCH_RANGE, BelchBombEvent.BELCH_RANGE);
        for (Entity entity : nearby) {
            if (entity instanceof Player) {
                Player players = ((Player) entity).getPlayer();

                for (String section : xAbilities.config.getConfig().getConfigurationSection("BelchBomb.Effects").getKeys(false)) {

                    int power = xAbilities.config.getConfig().getInt("BelchBomb.Effects." + section + ".Power");
                    int time = xAbilities.config.getConfig().getInt("BelchBomb.Effects." + section + ".Time");
                    String type = xAbilities.config.getConfig().getString("BelchBomb.Effects." + section + ".Type");

                    players.addPotionEffect(new PotionEffect(PotionEffectType.getByName(type), time * 20, power - 1));

                }

                for (String list : xAbilities.config.getConfig().getStringList("BelchBomb.Message.Been-Blinded")) {
                    players.sendMessage(chatUtil.chat(list).replaceAll("%player%", p.getName()));
                }
            }
        }
    }

}
