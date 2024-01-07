
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
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.api.PrimeActionbar;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.enchants.managers.EnchantsManager;
import org.me.blastymina.enchants.economy.BonusEnchant;
import org.me.blastymina.enchants.economy.FortuneEnchant;
import org.me.blastymina.enchants.economy.MultiplicadorEnchant;
import org.me.blastymina.enchants.functions.SpeedEnchant;
import org.me.blastymina.enchants.managers.EnchantsManagerConfig;
import org.me.blastymina.enchants.specials.BritadeiraEnchant;
import org.me.blastymina.enchants.specials.ExplosionEnchant;
import org.me.blastymina.enchants.specials.LaserEnchant;
import org.me.blastymina.enchants.specials.RaioEnchant;
import org.me.blastymina.utils.mina.BlocksManager;
import org.me.blastymina.utils.players.PlayerManager;
import org.me.blastymina.utils.porcentage.PorcentageEnchantsManager;
import org.me.blastymina.utils.porcentage.PorcentageManager;
import org.me.blastymina.utils.rewards.CreateItems;
import org.me.blastymina.utils.rewards.EnchantsRewardsManager;

import java.io.IOException;
import java.sql.SQLException;

public class onBlockBreak
implements Listener {
    private static final ProtocolManager protocolManager = BlastyMina.getManager();

    @EventHandler
    public void blockBreak(BlockBreakEvent event) throws SQLException, IOException, InvalidConfigurationException {
        Player player = event.getPlayer();
        FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
        if (player == null || !player.isOnline()) {
            return;
        }
        if (player.getWorld().getName().equalsIgnoreCase("mina") && event.getBlock().getType() == Material.AIR) {
            event.setCancelled(true);
            double chance = enchantBonus(player);
            if (new PorcentageManager(chance).setup()) {
                new CreateItems(player);
            }
            managerSetup(player);
            enchantsSetup(player, event.getBlock());
            Bukkit.getScheduler().runTaskLater(BlastyMina.getPlugin(BlastyMina.class), () -> packetSend(player, event.getBlock()), 1L);
            PrimeActionbar.sendActionbar(player, ChatColor.translateAlternateColorCodes('&', config.getString("mina.blocks.msg-on-break").replace("{coins}", String.valueOf(enchantFortune(player)))));

        }
    }

    private static void packetSend(Player player, Block block) {
        if (player == null || !player.isOnline()) {
            return;
        }
        try {
            PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
            packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
            packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));
            protocolManager.sendServerPacket(player, packet);
        } catch (Exception es) {
            es.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("\u00a7a[ Blasty Mina ] \u00a7cPacket n\u00e3o foi enviada.");
        }
    }
    private void managerSetup(Player p) throws SQLException {
        PlayerManager playerManager = MySqlUtils.getPlayer(p);
        if (playerManager != null) {
            new SpeedEnchant(MySqlUtils.getPlayer(p));
            new MultiplicadorEnchant(playerManager, 1);
            playerManager.setBreakblocks(playerManager.getBreakblocks() + 1);
            p.getItemInHand().getItemMeta().setDisplayName(ChatColor.translateAlternateColorCodes('&', BlastyMina.getPlugin(BlastyMina.class).getConfig().getString("mina.blocks.name") + " | " + playerManager.getBreakblocks()));
            playerManager.setXp(playerManager.getXP() + BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.blocks.xp-por-block"));
            playerManager.onUpXP();
            MySqlUtils.updatePlayer(playerManager, p);
        } else {
            Bukkit.getLogger().warning("PlayerManager é nulo para o jogador: " + p.getName());
        }
    }
    private Double enchantFortune(Player player) throws SQLException {
        PlayerManager manager = MySqlUtils.getPlayer(player);
        return new FortuneEnchant(manager).setup();
    }
    private Double enchantBonus(Player player) throws SQLException {
        PlayerManager manager = MySqlUtils.getPlayer(player);
        return new BonusEnchant(manager).setup();
    }

    private void enchantsSetup(Player p, Block block) throws SQLException, IOException, InvalidConfigurationException {
        if (new EnchantsManager(MySqlUtils.getPlayer(p), "britadeira").chanceOfEnchant()) {
            new BritadeiraEnchant(p, block, MySqlUtils.getPlayer(p));
        }
        if (new EnchantsManager(MySqlUtils.getPlayer(p), "explosao").chanceOfEnchant()) {
            new ExplosionEnchant(p, block);
        }
        if (new EnchantsManager(MySqlUtils.getPlayer(p), "raio").chanceOfEnchant()) {
            new RaioEnchant(p, block);
        }
        if(new EnchantsManager(MySqlUtils.getPlayer(p), "laser").chanceOfEnchant()) {
            new LaserEnchant(p);
        }
    }
}

