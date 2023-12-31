package org.me.blastymina.utils.mina;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.events.onBlockBreak;
import org.me.blastymina.utils.players.PlayerManager;

import java.sql.SQLException;
import java.util.Random;

public class CuboidManager {
    private static ProtocolManager manager = BlastyMina.getManager();

    public CuboidManager(Player player) {
        criarCubo(player);
    }

    private void criarCubo(Player player) {
        int tamanho = BlastyMina.getPlugin(BlastyMina.class).getConfig().getInt("mina.location.width");
        World world = Bukkit.getWorld(BlastyMina.getPlugin(BlastyMina.class).getConfig().getString("mina.location.mina.world"));
        double locx = BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.location.mina.x");
        double locy = BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.location.mina.y");
        double locz = BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.location.mina.z");
        int metadeTamanho = tamanho / 2;

        for (int x = -metadeTamanho; x <= metadeTamanho; x++) {
            for (int y = -metadeTamanho; y <= metadeTamanho; y++) {
                for (int z = -metadeTamanho; z <= metadeTamanho; z++) {
                    Location loc = new Location(world, locx + x, locy + y, locz + z);
                    Block block = loc.getWorld().getBlockAt(loc);
                    PacketContainer packet = manager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
                    packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
                    packet.getBlockData().write(0, WrappedBlockData.createData(Material.STONE));
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

    private void getMaterials(Player p) throws SQLException {
        new BlocksManager(MySqlUtils.getPlayer(p), new Random().nextDouble());
    }
}

