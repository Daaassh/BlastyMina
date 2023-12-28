
package org.me.blastymina.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.me.blastymina.BlastyMina;

public class onBlockBreak
implements Listener {
    private final ProtocolManager protocolManager = BlastyMina.getManager();

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player == null || !player.isOnline()) {
            return;
        }
        if (player.getWorld().getName().equalsIgnoreCase("mina") && event.getBlock().getType() == Material.STONE) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTaskLater((Plugin)BlastyMina.getPlugin(BlastyMina.class), () -> this.packetSend(player, event.getBlock()), 5L);
        }
    }

    private void packetSend(Player player, Block block) {
        if (player == null || !player.isOnline()) {
            return;
        }
        try {
            PacketContainer packet = this.protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
            packet.getBlockPositionModifier().write(0, (Object)new BlockPosition(block.getX(), block.getY(), block.getZ()));
            packet.getBlockData().write(0, (Object)WrappedBlockData.createData((Material)Material.AIR));
            this.protocolManager.sendServerPacket(player, packet);
            player.sendMessage("\u00a7a[ Blasty Mina ]" + ChatColor.GREEN + "Bloco destru\u00eddo.");
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("\u00a7a[ Blasty Mina ] \u00a7cPacket n\u00e3o foi enviada.");
        }
    }
}

