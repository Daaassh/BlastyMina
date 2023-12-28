
package org.me.blastymina.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.me.blastymina.BlastyMina;

public class PacketsManager {
    private Player player;
    private ProtocolManager manager = BlastyMina.getManager();
    private Location loc;
    private Integer size;

    public PacketsManager(Player player, Location loc, Integer size) {
        this.player = player;
        this.loc = loc;
        this.size = size;
    }

    public void packetSend() {
        if (this.player == null || !this.player.isOnline()) {
            return;
        }
        try {
            PacketContainer packet = this.manager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
            this.createCuboid(packet, this.size);
            this.player.sendMessage("\u00a7a[ Blasty Mina ]" + ChatColor.GREEN + "Sua mina foi resetada..");
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("\u00a7a[ Blasty Mina ] \u00a7cPacket n\u00e3o foi enviada.");
        }
    }

    public void createCuboid(PacketContainer packet, Integer size) {
        LocationConfig config = new LocationConfig(BlastyMina.getPlugin(BlastyMina.class));
        if (config == null) {
            return;
        }
        if (config.loadWorld("mina.location.world") == null) {
            Bukkit.getConsoleSender().sendMessage("\u00a7a[ Blasty Mina ] \u00a7cLocal da mina n\u00e3o definida.");
        } else {
            World world = Bukkit.getWorld((String)config.loadWorld("mina.location.world"));
            double x = config.loadLocation("mina.location.x");
            double y = config.loadLocation("mina.location.y");
            double z = config.loadLocation("mina.location.z");
            int centerX = (int)x;
            int centerY = (int)y;
            int centerZ = (int)z;
            for (int i = centerX - size / 2; i <= centerX + size / 2; ++i) {
                for (int k = centerY - size / 2; k <= centerY + size / 2; ++k) {
                    for (int j = centerZ - size / 2; j <= centerZ + size / 2; ++j) {
                        try {
                            Block block = world.getBlockAt(i, centerY, j);
                            packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
                            packet.getBlockData().write(0, WrappedBlockData.createData(Material.STONE));
                            Bukkit.getScheduler().runTaskLater(BlastyMina.getPlugin(BlastyMina.class), () -> this.manager.sendServerPacket(this.player, packet), 5L);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Bukkit.getConsoleSender().sendMessage("\u00a7a[ Blasty Mina ] \u00a7cPacket n\u00e3o foi enviada.");
                        }
                    }
                }
            }
        }
    }
}

