
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
import org.me.blastymina.utils.porcentage.PorcentageManager;
import org.me.blastymina.utils.rewards.CreateItems;

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
            double chance = BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.rewards.chance");
            if (new PorcentageManager(chance).setup()) {
                new CreateItems(player);
            }

            Bukkit.getScheduler().runTaskLater(BlastyMina.getPlugin(BlastyMina.class), () -> packetSend(player, event.getBlock()), 1L);

        }
    }

    private void packetSend(Player player, Block block) {
        if (player == null || !player.isOnline()) {
            return;
        }
        try {
            PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
            packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
            packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));
            protocolManager.sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("\u00a7a[ Blasty Mina ] \u00a7cPacket n\u00e3o foi enviada.");
        }
    }
}

