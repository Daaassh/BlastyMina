package org.me.blastymina.enchants.managers;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.enchants.economy.FortuneEnchant;
import org.me.blastymina.utils.players.PlayerManager;
import org.me.blastymina.utils.porcentage.PorcentageEnchantsManager;

import java.io.IOException;

public class EnchantsManager {
    private PlayerManager manager;
    private String enchant;
    private CustomFileConfiguration config;

    public EnchantsManager(PlayerManager manager, String enchant) throws IOException, InvalidConfigurationException {
        this.manager = manager;
        this.enchant = enchant;
        this.config = new CustomFileConfiguration("enchants.yml", BlastyMina.getPlugin(BlastyMina.class));
    }

    public double returnCoins() {
        return new FortuneEnchant(manager).setup();
    }

    public boolean chanceOfEnchant() {
        double percentage = verifyEnchant(enchant) * config.getDouble("enchants." + enchant + ".initial-percentage");
        return new PorcentageEnchantsManager(percentage).setup();
    }

    private int verifyEnchant(String enchant) {
        switch (enchant) {
            case "fortuna":
                return manager.getFortuna();
            case "britadeira":
                return manager.getBritadeira();
            case "raio":
                return manager.getRaio();
            case "bonus":
                return manager.getBonus();
            case "laser":
                return manager.getLaser();
            case "explosao":
                return manager.getExplosao();
            case "multiplicador":
                return manager.getMultiplicador();
            case "velocidade":
                return manager.getVelocidade();
            case "xpbooster":
                return manager.getXpbooster();
            default:
                return 0;
        }
    }
}

