package services.xenlan.xabilities.items.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.gui.abilityreset.AbilityResetGUI;
import services.xenlan.xabilities.xAbilities;

public class AbilityReset implements Listener {

    AbilityEvents item = AbilityEvents.ABILITYRESET;

    @EventHandler
    public void clickCooldowns(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!item.isSimilar(event.getPlayer().getItemInHand()))
                return;
            if (Cooldowns.getAbilityreset().onCooldown(event.getPlayer())) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    event.getPlayer().sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString
                            ("Ability-Reset.Name")).replace("%time%", Cooldowns.getAbilityreset().getRemaining(event.getPlayer()))));
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

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (Cooldowns.getAbilitycd().onCooldown(p)) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Ability.OnCooldown")) {
                    p.sendMessage(chatUtil.chat(list.replace("%time%", Cooldowns.getAbilitycd().getRemaining(p))));
                    event.setCancelled(true);
                    p.updateInventory();
                    return;
                }
            }
            if (Cooldowns.getAbilityreset().onCooldown(p)) {
                for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                    p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("Ability-Reset.Name")).replace("%time%", Cooldowns.getAbilityreset().getRemaining(p))));
                    event.setCancelled(true);
                    p.updateInventory();
                    return;
                }
            }
            AbilityResetGUI.openResetGUI(p);
        }
    }
}
