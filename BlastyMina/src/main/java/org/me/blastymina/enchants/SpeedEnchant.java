package org.me.blastymina.enchants;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;
import org.me.blastymina.utils.players.PlayerManager;

public class SpeedEnchant {
    private PlayerManager manager;

    public SpeedEnchant(PlayerManager manager) {
        this.manager = manager;
        setup();
    }
    private void setup(){
        Integer lvl = manager.getVelocidade();
        try {
            manager.getPlayer().addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.SPEED, 20 * lvl, lvl));
        }catch (Exception e){
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§cErro ao adicionar velocidade ao jogador " + manager.getPlayer().getName());
        }
    }
}
