
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.api.PrimeActionbar;
import org.me.blastymina.api.TitleAPI;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.utils.porcentage.PorcentageManager;
import org.me.blastymina.utils.rewards.CreateItems;

public class onBlockBreak
implements Listener {
    private final ProtocolManager protocolManager = BlastyMina.getManager();

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
        if (player == null || !player.isOnline()) {
            return;
        }
        if (player.getWorld().getName().equalsIgnoreCase("mina") && event.getBlock().getType() == Material.STONE) {
            event.setCancelled(true);
            double chance = BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.rewards.chance");
            if (new PorcentageManager(chance).setup()) {
                new CreateItems(player);
            }
            MySqlUtils.getPlayer(player).setXp(MySqlUtils.getPlayer(player).getXP() + (int)BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.blocks.xp-por-block"));
            MySqlUtils.getPlayer(player).setBlocks(MySqlUtils.getPlayer(player).getBlocks() + 1);
            MySqlUtils.getPlayer(player).verifyLevels();
            player.sendMessage(ChatColor.RED + "Blocos: " + MySqlUtils.getPlayer(player).getBlocks());
            player.sendMessage(ChatColor.RED + "XP: " + MySqlUtils.getPlayer(player).getXP());
            Bukkit.getScheduler().runTaskLater(BlastyMina.getPlugin(BlastyMina.class), () -> packetSend(player, event.getBlock()), 1L);
            PrimeActionbar.sendActionbar(player,ChatColor.YELLOW + "Mina | " + ChatColor.translateAlternateColorCodes('&', config.getString("mina.blocks.msg-on-break").replace("{coins}", String.valueOf((int)BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.blocks.coin-por-block")))));
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

