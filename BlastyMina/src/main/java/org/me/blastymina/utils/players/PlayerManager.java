package org.me.blastymina.utils.players;

import org.bukkit.entity.Player;
import org.me.blastymina.api.SpawnFirework;
import org.me.blastymina.api.TitleAPI;
import org.me.blastymina.database.MySqlUtils;

public class PlayerManager {
    private Player player;
    private Integer blocks;
    private Integer nivel;
    private double xp;
    private Integer fortuna;
    private Integer britadeira;
    private Integer laser;
    private Integer raio;
    private Integer bonus;

    public PlayerManager(Player player, Integer nivel, Integer xp, Integer fortuna, Integer britadeira, Integer laser, Integer raio, Integer bonus) {
        this.player = player;
        this.nivel = nivel;
        this.xp = xp;
        this.fortuna = fortuna;
        this.britadeira = britadeira;
        this.laser = laser;
        this.raio = raio;
        this.bonus = bonus;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }


    public Integer getFortuna() {
        return fortuna;
    }

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public Double getXP() {
        return xp;
    }
    public void setXp(double xp) {
        this.xp = xp;
    }


    public void setFortuna(Integer fortuna) {
        this.fortuna = fortuna;
    }

    public Integer getBritadeira() {
        return britadeira;
    }

    public void setBritadeira(Integer britadeira) {
        this.britadeira = britadeira;
    }

    public Integer getLaser() {
        return laser;
    }

    public void setLaser(Integer laser) {
        this.laser = laser;
    }

    public Integer getRaio() {
        return raio;
    }

    public void setRaio(Integer raio) {
        this.raio = raio;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }
    public void verifyLevels() {
        switch (MySqlUtils.getPlayer(player).getNivel()) {
            case 10:
                player.sendMessage("§aParabéns, você alcançou o nível 10!");
                SpawnFirework.small(player);
                break;
            case 25:
                SpawnFirework.small(player);
                player.sendMessage("§aParabéns, você alcançou o nível 25!");
                break;
            case 50:
                SpawnFirework.medium(player);
                player.sendMessage("§aParabéns, você alcançou o nível 50!");
                break;
            case 75:
                SpawnFirework.medium(player);
                player.sendMessage("§aParabéns, você alcançou o nível 75!");
                break;
            case 150:
                SpawnFirework.big(player);
                player.sendMessage("§aParabéns, você alcançou o nível 150!");
                break;
            case 300:
                SpawnFirework.big(player);
                player.sendMessage("§aParabéns, você alcançou o nível 300!");
                break;
        }
    }
}