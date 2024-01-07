package org.me.blastymina.utils.mina;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.events.onBlockBreak;
import org.me.blastymina.utils.players.PlayerManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class CuboidManager {
    private static ProtocolManager manager = BlastyMina.getManager();

    public CuboidManager(Player player) throws IOException, InvalidConfigurationException, SQLException {
        criarCubo(player);
    }

    private void criarCubo(Player player) throws IOException, InvalidConfigurationException, SQLException {
        CustomFileConfiguration config = new CustomFileConfiguration("locations.yml", BlastyMina.getPlugin(BlastyMina.class));
        int tamanho = BlastyMina.getPlugin(BlastyMina.class).getConfig().getInt("mina.location.width");
        World world = Bukkit.getWorld(config.getString("mina.world"));
        double locx = config.getDouble("mina.x");
        double locy = config.getDouble("mina.y");
        double locz = config.getDouble("mina.z");
        int metadeTamanho = tamanho / 2;

        for (int x = -metadeTamanho; x <= metadeTamanho; x++) {
            for (int y = -metadeTamanho; y <= metadeTamanho; y++) {
                for (int z = -metadeTamanho; z <= metadeTamanho; z++) {
                    Location loc = new Location(world, locx + x, locy + y, locz + z);
                    Block block = loc.getWorld().getBlockAt(loc);
                    PacketContainer packet = manager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
                    packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
                    packet.getBlockData().write(0, WrappedBlockData.createData(getMaterials(player)));
                    try {
                        Bukkit.getScheduler().runTaskLater(BlastyMina.getPlugin(BlastyMina.class), () -> manager.sendServerPacket(player, packet), 20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage("\u00a7a[ Blasty Mina ] \u00a7cMina foi resetada..");
    }

    private Material getMaterials(Player p) throws SQLException, IOException, InvalidConfigurationException {
        return new BlocksManager(MySqlUtils.getPlayer(p)).returnBlocks();
    }
}

