package org.me.blastymina.enchants.specials;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.utils.players.PlayerManager;
import org.me.blastymina.utils.rewards.EnchantsRewardsManager;

import java.io.IOException;
import java.sql.SQLException;

public class RaioEnchant {
    private final Block block;
    private final Player p;
    ProtocolManager manager = BlastyMina.getManager();
    public RaioEnchant(Player p, Block block) throws IOException, InvalidConfigurationException, SQLException {
        this.block = block;
        this.p = p;
        this.packetSend();
    }

    public void packetSend() throws IOException, InvalidConfigurationException, SQLException {
        PlayerManager managers = MySqlUtils.getPlayer(p);
        int blocks = 0;
        double y = block.getY();
        World world = block.getWorld();
            for (int h = (int)y; h < (y - 15); h--) {
                Block currentBlock = world.getBlockAt(block.getX(), h, block.getZ());
                if (currentBlock.getType() != Material.AIR) continue;
                PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
                packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), h, block.getZ()));
                packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));
                try {
                    manager.sendServerPacket(p, packet);
                    blocks++;
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Raio ]" + ChatColor.RED + "Erro ao enviar o pacote do jogador " + this.p.getName() + ".");
                }
            }
        new EnchantsRewardsManager(p, blocks, "raio");
        managers.setBlocks(managers.getBlocks() + blocks);
        MySqlUtils.updatePlayer(managers, p);
        p.sendMessage(ChatColor.RED + " Raio ativado, foram quebrados " + blocks + " blocos");
    }
}
