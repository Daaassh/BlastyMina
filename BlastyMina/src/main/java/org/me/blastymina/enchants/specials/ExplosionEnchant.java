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
import org.me.blastymina.api.TitleAPI;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.utils.players.PlayerManager;
import org.me.blastymina.utils.rewards.EnchantsRewardsManager;

import java.io.IOException;
import java.sql.SQLException;

public class ExplosionEnchant {
    private Player p;
    private PacketContainer packet;
    ProtocolManager manager = BlastyMina.getManager();
    private PlayerManager playerManager;
    private Block block;
    private TitleAPI api = new TitleAPI();
    private CustomFileConfiguration config = new CustomFileConfiguration("enchants.yml", BlastyMina.getPlugin(BlastyMina.class));


    public ExplosionEnchant(Player p, Block block) throws SQLException, IOException, InvalidConfigurationException {
        this.block = block;
        this.p = p;
        packetSend();
    }
    private void packetSend() throws SQLException, IOException, InvalidConfigurationException {
        int blocks = 0;
        double x = block.getX();
        double y = block.getY();
        double z = block.getZ();
        World world = block.getWorld();

        int width = 3;

        int centerX = (int) x;
        int centerY = (int) y;
        int centerZ = (int) z;

        for (int j = centerX - (width / 2); j <= centerX + (width / 2); ++j) {
            for (int k = centerY - (width / 2); k <= centerY + (width / 2); ++k) {
                for (int h = centerZ - (width / 2); h <= centerZ + (width / 2); ++h) {
                    Block currentBlock = world.getBlockAt(j, k, h);
                    if (currentBlock.getType() != Material.AIR) continue;
                    PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
                    packet.getBlockPositionModifier().write(0, new BlockPosition(j, k, h));
                    packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));
                    try {
                        manager.sendServerPacket(p, packet);
                        packet.getBlockPositionModifier().write(0, new BlockPosition(j, k, h));
                        packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));
                        manager.sendServerPacket(p, packet);
                        ++blocks;
                    } catch (Exception e) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Explosão ]" + ChatColor.RED +
                                "Erro ao enviar o pacote do jogador " + p.getName() + ".");
                    }
                }
            }
        }
        new EnchantsRewardsManager(p, blocks, "explosion");
        playerManager = MySqlUtils.getPlayer(p);
        playerManager.setBlocks(playerManager.getBlocks() + blocks);
        MySqlUtils.updatePlayer(playerManager, p);
        api.sendFullTitle(p, 3, 3, 5, ChatColor.YELLOW + "Explosão", "Ativado foram quebrados " + blocks + " blocos");
    }

}
