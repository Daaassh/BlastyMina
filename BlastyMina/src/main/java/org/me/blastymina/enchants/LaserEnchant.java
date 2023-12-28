
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
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;

public class LaserEnchant {
    public Player p;
    public PacketContainer packet;
    ProtocolManager manager = BlastyMina.getManager();
    public Block block;

    public LaserEnchant(Player p, Block block) {
        this.block = block;
        this.p = p;
        this.packetSend();
    }

    public void packetSend() {
        int blocks = 0;
        double x = this.block.getX();
        double z = this.block.getZ();
        World world = this.block.getWorld();
        for (int j = (int)x; j < (int)(x + 15.0); ++j) {
            for (int h = (int)z; h < (int)(z + 1.0); ++h) {
                Block currentBlock = world.getBlockAt(j, this.block.getY(), h);
                if (currentBlock.getType() != Material.STONE) continue;
                PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
                packet.getBlockPositionModifier().write(0, (Object)new BlockPosition(j, this.block.getY(), h));
                packet.getBlockData().write(0, (Object)WrappedBlockData.createData((Material)Material.AIR));
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(this.p, packet);
                    ++blocks;
                    continue;
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Laser ]" + ChatColor.RED + "Erro ao enviar o pacote do jogador " + this.p.getName() + ".");
                }
            }
        }
        this.p.sendMessage(ChatColor.RED + " Laser ativado, foram quebrados " + blocks + " blocos");
    }
}

