
package org.me.blastymina.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.api.TitleAPI;

public class SendPlayerToSpawn {
    public Player player;
    private TitleAPI api = new TitleAPI();
    FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();

    public SendPlayerToSpawn(Player player) {
        this.player = player;
        this.sendPlayer();
    }

    public void sendPlayer() {
        double x = config.getDouble("mina.location.spawn.x");
        double y = config.getDouble("mina.location.spawn.y");
        double z = config.getDouble("mina.location.spawn.z");
        World world = Bukkit.getWorld(config.getString("mina.location.spawn.world"));
        api.sendFullTitle(player, 5, 5, 10, "§c§lMina", "§c§lSua mina foi resetada!");
        player.teleport(new Location(world, x, y, z));
    }
}

