package services.xenlan.xabilities.items.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import services.xenlan.xabilities.cooldown.Cooldowns;
import services.xenlan.xabilities.items.AbilityEvents;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

public class SecondChance implements Listener {

    private AbilityEvents item = AbilityEvents.SECONDCHANCE;

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
        if (Cooldowns.getSecondchance().onCooldown(p)) {
            for (String list : xAbilities.config.getConfig().getStringList("Message.Item.OnCooldown")) {
                p.sendMessage(chatUtil.chat(list.replace("%ability%", xAbilities.config.getConfig().getString("SecondChance.Name")).replace("%time%", Cooldowns.getSecondchance().getRemaining(p))));
                event.setCancelled(true);
                return;
            }
        }
        AbilityEvents.takeItem(p, p.getItemInHand());
        Cooldowns.getSecondchance().applyCooldown(p, xAbilities.config.getConfig().getInt("SecondChance.Cooldown") * 1000);
        Cooldowns.getAbilitycd().applyCooldown(p, xAbilities.config.getConfig().getInt("Ability-Cooldown") * 1000);
        for (String list : xAbilities.config.getConfig().getStringList("SecondChance.Message.Used")) {
            p.sendMessage(chatUtil.chat(list));
        }

        String command = xAbilities.config.getConfig().getString("SecondChance.Command");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", p.getName()));

    }

}
