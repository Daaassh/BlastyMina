
package org.me.blastymina.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.api.TitleAPI;
import org.me.blastymina.configs.CustomFileConfiguration;

import java.io.IOException;

public class SendPlayerToSpawn {
    public Player player;
    private TitleAPI api = new TitleAPI();
    CustomFileConfiguration config = new CustomFileConfiguration("locations.yml", BlastyMina.getPlugin(BlastyMina.class));

    public SendPlayerToSpawn(Player player) throws IOException, InvalidConfigurationException {
        this.player = player;
        this.sendPlayer();
    }

    public void sendPlayer() {
        double x = config.getDouble("spawn.x");
        double y = config.getDouble("spawn.y");
        double z = config.getDouble("spawn.z");
        double yaw = config.getDouble("spawn.yaw");
        double pitch = config.getDouble("spawn.pitch");
        World world = Bukkit.getWorld(config.getString("spawn.world"));
        api.sendFullTitle(player, 5, 5, 10, "§c§lMina", "§c§lSua mina foi criada!");
        player.teleport(new Location(world, x, y, z, (float) yaw, (float) pitch));
    }
}

