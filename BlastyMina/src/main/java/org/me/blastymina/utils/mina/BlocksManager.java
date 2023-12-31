package org.me.blastymina.utils.mina;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.utils.players.PlayerManager;

public class BlocksManager {
    private PlayerManager manager;
    private FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
    private Material[] tiposBlocos = {};
    private Double[] probabilidades = {};

    public BlocksManager(PlayerManager manager, Double randomnumber) {
        this.manager = manager;
        setup(randomnumber);
    }

    private Material setup(double numeroAleatorio) {
        double acumulado = 0.0;

        for (int i = 0; i < tiposBlocos.length; i++) {
            acumulado += probabilidades[i];
            if (numeroAleatorio <= acumulado && canUseMaterial(manager.getPlayer(), tiposBlocos[i])) {
                return tiposBlocos[i];
            }
        }

        return tiposBlocos[tiposBlocos.length - 1];
    }

    public Material[] getTipos() {
        for (String sections : config.getConfigurationSection("mina.blocks.material").getKeys(false)) {
            tiposBlocos = config.getConfigurationSection("mina.blocks.material." + sections)
                    .getKeys(false)
                    .stream()
                    .map(Material::valueOf)
                    .toArray(Material[]::new);
            probabilidades = config.getConfigurationSection("mina.blocks.material." + sections)
                    .getKeys(false)
                    .stream()
                    .map(key -> config.getDouble("mina.blocks.material." + sections + "." + key))
                    .toArray(Double[]::new);
        }
        return tiposBlocos;
    }

    public boolean canUseMaterial(Player player, Material material) {
        String requiredLevel = config.getString("mina.materials." + material.toString() + ".required-level");

        return true;
    }
}