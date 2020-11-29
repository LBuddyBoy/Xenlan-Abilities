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
import services.xenlan.xabilities.items.events.TankIngotEvent;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

public class TankIngot implements Listener {

    public static int INGOT_RANGE = xAbilities.config.getConfig().getInt("TankIngot.Distance");
    private AbilityEvents item = AbilityEvents.TANKINGOT;

    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getTankingot().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("TankIngot.Name")).replace("%time%", Cooldowns.getTankingot().getRemaining(event.getPlayer()))));
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
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (Cooldowns.getAbilitycd().onCooldown(p)) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Ability.OnCooldown")) {
                    p.sendMessage(chatUtil.chat(list.replace("%time%", Cooldowns.getAbilitycd().getRemaining(p))));
                    event.setCancelled(true);
                    p.updateInventory();
                    return;
                }
            }
            if (Cooldowns.getTankingot().onCooldown(p)) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("TankIngot.Name")).replace("%time%", Cooldowns.getTankingot().getRemaining(p))));
                    event.setCancelled(true);
                    p.updateInventory();
                    return;
                }
            }
            if (TankIngotEvent.getNearbyPlayers(p).isEmpty()) {
                for (String list : xAbilities.config.getConfig().getStringList("TankIngot.Message.Denied")) {
                    p.sendMessage(chatUtil.chat(list));
                }
                event.setCancelled(true);
                p.updateInventory();
            }
            if (!TankIngotEvent.getNearbyPlayers(p).isEmpty()) {
                Cooldowns.getTankingot().applyCooldown(p, xAbilities.config.getConfig().getInt("TankIngot.Cooldown") * 1000);
                if (xAbilities.config.getConfig().getBoolean("Ability-Cooldown-Enabled")) {
                    Cooldowns.getAbilitycd().applyCooldown(p, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
                }
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (int) ((TankIngotEvent.getNearbyPlayers(p).size()) * 23 * xAbilities.config.getConfig().getDouble("TankIngot.Multiplier")), xAbilities.config.getConfig().getInt("TankIngot.Power") - 1));
                AbilityEvents.takeItem(p, p.getItemInHand());
                p.updateInventory();
                for (String list : xAbilities.config.getConfig().getStringList("TankIngot.Message.Success")) {
                    p.sendMessage(chatUtil.chat(list).replace("%nearby%", String.valueOf(TankIngotEvent.getNearbyPlayers(p).size())));
                    return;
                }
            }
        }
    }
}
