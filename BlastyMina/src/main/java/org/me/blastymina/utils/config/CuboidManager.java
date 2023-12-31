package org.me.blastymina.utils.config;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;

public class CuboidManager {
    private static Material[] tiposBlocos = {Material.COAL_BLOCK, Material.COAL_ORE, Material.STONE, Material.COBBLESTONE};
    private static double[] probabilidades = {0.01, 0.09, 0.1, 0.5};
    private static ProtocolManager manager = BlastyMina.getManager();

    public CuboidManager(Player player) {
        criarCubo(player);
    }

    private void criarCubo(Player player) {
        int tamanho = BlastyMina.getPlugin(BlastyMina.class).getConfig().getInt("mina.location.width");
        World world = Bukkit.getWorld(BlastyMina.getPlugin(BlastyMina.class).getConfig().getString("mina.location.mina.world"));
        double locx = BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.location.mina.x");
        double locy = BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.location.mina.y");
        double locz = BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.location.mina.z");
        int metadeTamanho = tamanho / 2;

        for (int x = -metadeTamanho; x <= metadeTamanho; x++) {
            for (int y = -metadeTamanho; y <= metadeTamanho; y++) {
                for (int z = -metadeTamanho; z <= metadeTamanho; z++) {
                    Location loc = new Location(world, locx + x, locy + y, locz + z);
                    Block block = world.getBlockAt(loc);
                    PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
                    packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
                    packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));
                    try {
                        Bukkit.getScheduler().runTaskLater(BlastyMina.getPlugin(BlastyMina.class), () -> manager.sendServerPacket(player, packet), 1L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage("\u00a7a[ Blasty Mina ] \u00a7cMina foi resetada..");
    }

    private Material randomBlock(double numeroAleatorio) {
        double acumulado = 0.0;

        for (int i = 0; i < tiposBlocos.length; i++) {
            acumulado += probabilidades[i];
            if (numeroAleatorio <= acumulado) {
                return tiposBlocos[i];
            }
        }

        // Se não foi selecionado um bloco, retorne o último tipo de bloco
        return tiposBlocos[tiposBlocos.length - 1];
    }
}

