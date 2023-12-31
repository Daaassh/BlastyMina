package org.me.blastymina.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.utils.rewards.EnchantsRewardsManager;

public class PacketsManager {
    private Player player;
    private ProtocolManager manager = BlastyMina.getManager();
    private Location loc;
    private Integer size;

    public PacketsManager(Player player, Location loc, Integer size) {
        this.player = player;
        this.loc = loc;
        this.size = size;
        this.packetSend();
    }

    public void packetSend() {
        if (player == null || !player.isOnline()) {
            return;
        }
        try {
            player.sendMessage("\u00a7a[ Blasty Mina ]" + ChatColor.GREEN + "Sua mina foi resetada..");
            createCuboid();
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("\u00a7a[ Blasty Mina ] \u00a7cPacket n\u00e3o foi enviada.");
        }
    }


    public void createCubo() {
        LocationConfig config = new LocationConfig(BlastyMina.getPlugin(BlastyMina.class));
        if (config.loadWorld("mina.location.world") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Mina ]" + ChatColor.RED + "Local da mina não foi definida.");
        } else {
            World world = Bukkit.getWorld(config.loadWorld("mina.location.world"));
            double x = config.loadLocation("mina.location.x");
            double y = config.loadLocation("mina.location.y");
            double z = config.loadLocation("mina.location.z");
            Integer width = BlastyMina.getPlugin(BlastyMina.class).getConfig().getInt("mina.location.mina-width");
            int centerX = (int) x;
            int centerY = (int) y;
            int centerZ = (int) z;

            for (int j = centerX - (width / 2); j <= centerX + (width / 2); ++j) {
                for (int k = centerY - (width / 2); k <= centerY + (width / 2); ++k) {
                    for (int h = centerZ - (width / 2); h <= centerZ + (width / 2); ++h) {
                        Block currentBlock = world.getBlockAt(j, k, h);
                        if (currentBlock.getType() != Material.STONE) continue;
                        PacketContainer packet;
                        packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
                        try {
                            packet.getBlockPositionModifier().write(0, new BlockPosition(j, k, h));
                            packet.getBlockData().write(0, WrappedBlockData.createData(Material.STONE));
                            Bukkit.getScheduler().runTaskLater(BlastyMina.getPlugin(BlastyMina.class), () -> manager.sendServerPacket(player, packet), 1L);
                        } catch (Exception e) {
                            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Mina ]" + ChatColor.RED + "Erro ao criar a mina");
                        }
                    }
                }
            }
        }
    }

        public void createCuboid() {
            FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
            if (config.getString("mina.location.world") == null) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Mina ]" + ChatColor.RED + "Local da mina não foi definida.");
            } else {
                World world = Bukkit.getWorld(config.getString("mina.location.world"));
                double x = config.getDouble("mina.location.x");
                double y = config.getDouble("mina.location.y");
                double z = config.getDouble("mina.location.z");
                int centerX = (int) x;
                int centerY = (int) y;
                int centerZ = (int) z;

                for (int i = centerX - size / 2; i <= (centerX + size) / 2; ++i) {
                    for (int k = centerY - size / 2; k <= (centerY + size) / 2; ++k) {
                        for (int j = centerZ - size / 2; j <= (centerZ + size) / 2; ++j) {
                            try {
                                Block block = world.getBlockAt(i, k, j);
                                PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
                                packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
                                packet.getBlockData().write(0, WrappedBlockData.createData(Material.STONE));
                                Bukkit.getScheduler().runTaskLater(BlastyMina.getPlugin(BlastyMina.class), () -> manager.sendServerPacket(player, packet), 1L);
                                player.sendMessage("\u00a7a[ Blasty Mina ]" + ChatColor.GREEN + "Sua mina foi resetada...");
                            } catch (Exception e) {
                                e.printStackTrace();
                                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Mina ] " + ChatColor.RED + "Packet não foi enviado");
                            }
                        }
                    }
                }
            }
        }
    }


