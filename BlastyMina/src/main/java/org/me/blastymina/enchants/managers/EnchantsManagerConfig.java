package org.me.blastymina.enchants.managers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.utils.players.PlayerManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnchantsManagerConfig {
    private PlayerManager manager;
    private String enchant;
    private CustomFileConfiguration config = new CustomFileConfiguration("enchants.yml", BlastyMina.getPlugin(BlastyMina.class));

    public EnchantsManagerConfig(PlayerManager manager, String enchant) throws IOException, InvalidConfigurationException {
        this.manager = manager;
        this.enchant = enchant;
    }
    public String returnName(){
        return ChatColor.translateAlternateColorCodes('&',config.getString("enchants." + enchant + ".item.name"));
    }
    public List<String> returnLore(){
        List<String> lore = translateColors(config.getStringList("enchants." + enchant + ".item.lore"));
        replaceEnchants(lore);
        replaceMaxLevel(lore);
        return lore;
    }
    private List<String> translateColors(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            input.set(i, ChatColor.translateAlternateColorCodes('&', input.get(i)));
        }
        return input;
    }
    private List<String> replaceEnchants(List<String> input){
        for (int i = 0; i < input.size(); i++) {
            String elemento = input.get(i);
            elemento = elemento.replace("{" + enchant + "}", String.valueOf(verifyEnchant(enchant)));
            input.set(i, elemento);
        }
        return input;
    }
    private List<String> replaceMaxLevel(List<String> input){
        for (int i = 0; i < input.size(); i++) {
            String elemento = input.get(i);
            elemento = elemento.replace("@max_level", getMaxLevel());
            input.set(i, elemento);
        }
        return input;
    }

    private int verifyEnchant(String enchant){
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
        }
        return 0;
    }
    private String getMaxLevel(){
        return String.valueOf(config.getInt("enchants." + enchant + ".max-level"));
    }
}
