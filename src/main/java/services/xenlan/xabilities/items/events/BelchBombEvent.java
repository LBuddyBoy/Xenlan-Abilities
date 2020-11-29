package services.xenlan.xabilities.items.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import services.xenlan.xabilities.items.abilities.TankIngot;
import services.xenlan.xabilities.xAbilities;

import java.util.ArrayList;
import java.util.List;

public class BelchBombEvent {

    public static int BELCH_RANGE = xAbilities.config.getConfig().getInt("BelchBomb.Distance");

    public static List<Player> getNearbyPlayers(Player player) {
        List<Player> valid = new ArrayList<>();
        for (Entity entity : player.getNearbyEntities(BELCH_RANGE, BELCH_RANGE  / 2, BELCH_RANGE)) {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                valid.add(nearbyPlayer);
            }
        }
        return (valid);
    }

    public int getBELCH_RANGE() {
        return BELCH_RANGE;
    }
}
