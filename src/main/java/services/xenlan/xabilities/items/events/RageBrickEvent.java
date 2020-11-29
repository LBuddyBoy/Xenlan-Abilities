package services.xenlan.xabilities.items.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import services.xenlan.xabilities.items.abilities.RageBrick;
import services.xenlan.xabilities.items.abilities.TankIngot;

import java.util.ArrayList;
import java.util.List;

public class RageBrickEvent {
    public static List<Player> getNearbyPlayers(Player player) {
        List<Player> valid = new ArrayList<>();
        for (Entity entity : player.getNearbyEntities(RageBrick.BRICK_RANGE, RageBrick.BRICK_RANGE / 2, RageBrick.BRICK_RANGE)) {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                valid.add(nearbyPlayer);
            }
        }
        return (valid);
    }
}
