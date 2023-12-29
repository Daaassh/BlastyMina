
package org.me.blastymina.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;

public class SendPlayerToSpawn {
    public Player player;
    LocationConfig config = new LocationConfig(BlastyMina.getPlugin(BlastyMina.class));

    public SendPlayerToSpawn(Player player) {
        this.player = player;
        this.sendPlayer();
    }

    public void sendPlayer() {
        double x = config.loadLocation("spawn.location.x");
        double y = config.loadLocation("spawn.location.y");
        double z = config.loadLocation("spawn.location.z");
        World world = Bukkit.getWorld(config.loadWorld("spawn.location.world"));
        player.teleport(new Location(world, x, y, z));
    }
}

