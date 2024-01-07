package org.me.blastymina.enchants.specials;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.api.TitleAPI;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.utils.players.PlayerManager;
import org.me.blastymina.utils.rewards.EnchantsRewardsManager;

import java.io.IOException;
import java.sql.SQLException;

public class LaserEnchant {
    private Player p;
    private TitleAPI api = new TitleAPI();
    ProtocolManager manager = BlastyMina.getManager();
    private CustomFileConfiguration config = new CustomFileConfiguration("enchants.yml", BlastyMina.getPlugin(BlastyMina.class));

    public LaserEnchant(Player p) throws IOException, InvalidConfigurationException, SQLException {
        this.p = p;
        packetSend();
    }

    private void packetSend() throws IOException, InvalidConfigurationException, SQLException {
        int blocks = 0;
        int distance = config.getInt("enchants.laser.distance");
        BlockIterator blockIterator = new BlockIterator(p, distance);
        
        while (blockIterator.hasNext()) {
            Block currentBlock = blockIterator.next();

            if (currentBlock.getType() != Material.AIR) continue;

            PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
            packet.getBlockPositionModifier().write(0, new BlockPosition(currentBlock.getX(), currentBlock.getY(), currentBlock.getZ()));
            packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));

            try {
                manager.sendServerPacket(p, packet);
                blocks++;
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Laser ]" + ChatColor.RED + "Erro ao enviar o pacote do jogador " + p.getName() + ".");
            }
        }
        new EnchantsRewardsManager(p, blocks, "laser");
        PlayerManager playerManager = MySqlUtils.getPlayer(p);
        playerManager.setBreakblocks(playerManager.getBreakblocks() + blocks);
        api.sendFullTitle(p, 3, 3, 5, ChatColor.YELLOW + "Laser", "Ativado foram quebrados " + blocks + " blocos");

    }
}

