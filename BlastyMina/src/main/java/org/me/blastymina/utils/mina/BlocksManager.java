package org.me.blastymina.utils.mina;


import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.utils.players.PlayerManager;

import java.io.IOException;
import java.util.*;

public class BlocksManager {
    private PlayerManager manager;
    private List<Material> typeBlocks = new ArrayList<>();
    private List<Double> probability = new ArrayList<>();
    private Map<List<Material>, List<Double>> blockConfig = new HashMap<>();
    private CustomFileConfiguration config = new CustomFileConfiguration("materials.yml", BlastyMina.getPlugin(BlastyMina.class));

    public BlocksManager(PlayerManager manager) throws IOException, InvalidConfigurationException {
        this.manager = manager;
        setMaterial();
    }
    private void setMaterial() {
        ConfigurationSection section = config.getConfigurationSection("blocks");
        int numberOfMaterials = returnNumberOfMaterials();
        int index = 0;
        for (String sections : section.getKeys(false)) {
            if (manager.getNivel() >= Integer.parseInt(sections)) {
                typeBlocks.add(Material.valueOf(section.getStringList(sections + ".type").get(0).toUpperCase()));
                probability.add(section.getDoubleList(sections + ".probability").get(0));
                index++;
            }
        }
    }
    private Material randomBlock(double numeroAleatorio) {
        typeBlocks.add(Material.IRON_BLOCK);
        double acumulado = 0.0;

        for (int i = 0; i < typeBlocks.size(); i++) {
            acumulado += probability.get(i);;
            if (numeroAleatorio <= acumulado) {
                return typeBlocks.get(i);
            }
        }

        return typeBlocks.get(0);
    }
    public Material returnBlocks(){
        Random random = new Random();
        return randomBlock(random.nextDouble());
    }
    private Integer returnNumberOfMaterials(){
        return config.getConfigurationSection("blocks").getKeys(false).size();
    }

}
