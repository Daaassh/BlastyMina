
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

public class BritadeiraEnchant {
    public Player p;
    public PacketContainer packet;
    ProtocolManager manager = BlastyMina.getManager();
    public Block block;

    public BritadeiraEnchant(Player p, Block block) {
        this.block = block;
        this.p = p;
        packetSend();
    }

        public void packetSend() {
        int blocks = 0;
        double x = block.getX();
        double z = block.getZ();
        World world = block.getWorld();

        int width = 7;

        int centerX = (int) x;
        int centerZ = (int) z;

        for (int j = centerX - (width / 2); j <= centerX + (width / 2); ++j) {
            for (int h = centerZ - (width / 2); h <= centerZ + (width / 2); ++h) {
                Block currentBlock = world.getBlockAt(j,block.getY(),h);
                if (currentBlock.getType() != Material.STONE) continue;
                PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
                packet.getBlockPositionModifier().write(0, new BlockPosition(j, block.getY(), h));
                packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));

                try {
                    manager.sendServerPacket(p, packet);
                    packet.getBlockPositionModifier().write(0, new BlockPosition(j, block.getY(), h));
                    packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));
                    manager.sendServerPacket(p,packet);
                    ++blocks;
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Britadeira ]" + ChatColor.RED +
                            "Erro ao enviar o pacote do jogador " + p.getName() + ".");
                }
            }
        }

        this.p.sendMessage(ChatColor.AQUA + "[ Britadeira ]" + ChatColor.GREEN +
                " ativado foram quebrados " + blocks + " blocos");
    }
}

