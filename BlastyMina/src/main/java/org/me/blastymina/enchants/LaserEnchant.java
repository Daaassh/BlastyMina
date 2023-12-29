package org.me.blastymina.enchants;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.me.blastymina.BlastyMina;

public class LaserEnchant {
    public Player p;
    ProtocolManager manager = BlastyMina.getManager();

    public LaserEnchant(Player p) {
        this.p = p;
        this.packetSend();
    }

    public void packetSend() {
        int blocks = 0;
        BlockIterator blockIterator = new BlockIterator(p, 15);
        
        while (blockIterator.hasNext()) {
            Block currentBlock = blockIterator.next();

            if (currentBlock.getType() != Material.STONE) continue;

            PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
            packet.getBlockPositionModifier().write(0, new BlockPosition(currentBlock.getX(), currentBlock.getY(), currentBlock.getZ()));
            packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));

            try {
                manager.sendServerPacket(p, packet);
                ++blocks;
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Laser ]" + ChatColor.RED + "Erro ao enviar o pacote do jogador " + p.getName() + ".");
            }
        }

        p.sendMessage(ChatColor.RED + "Laser ativado, foram quebrados " + blocks + " blocos");
    }
}

