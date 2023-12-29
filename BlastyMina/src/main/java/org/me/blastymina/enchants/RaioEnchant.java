package org.me.blastymina.enchants;

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
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.me.blastymina.BlastyMina;

public class RaioEnchant {
    public Block block;
    public Player p;
    ProtocolManager manager = BlastyMina.getManager();
    public RaioEnchant(Player p, Block block) {
        this.block = block;
        this.p = p;
        this.packetSend();
    }

    public void packetSend() {
        int blocks = 0;
        double x = block.getX();
        double y = block.getY();
        World world = block.getWorld();
            for (int h = (int)y; h < (int)(y - 1.0); --h) {
                Block currentBlock = world.getBlockAt(block.getX(), h, block.getZ());
                if (currentBlock.getType() != Material.STONE) continue;
                PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
                packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), h, block.getZ()));
                packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));
                try {
                    manager.sendServerPacket(p, packet);
                    ++blocks;
                    continue;
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Raio ]" + ChatColor.RED + "Erro ao enviar o pacote do jogador " + this.p.getName() + ".");
                }
        }
        p.sendMessage(ChatColor.RED + " Raio ativado, foram quebrados " + blocks + " blocos");
    }
}
