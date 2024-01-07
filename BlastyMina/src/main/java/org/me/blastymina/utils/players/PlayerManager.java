package org.me.blastymina.utils.players;

import org.bukkit.entity.Player;
import org.me.blastymina.api.SpawnFirework;
import org.me.blastymina.api.TitleAPI;
import org.me.blastymina.database.MySqlUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private Integer skin;
    private Integer explosao;
    private Integer multiplicador;
    private Integer velocidade;
    private Integer xpbooster;
    private Integer breakblocks;


    private Map<UUID, Player> xpplayers = new HashMap<>();

    public PlayerManager(Player player, Integer breakblocks,Integer velocidade,Integer xpbooster,Integer multiplicador,Integer explosao,Integer blocks,Integer nivel, Double xp, Integer fortuna, Integer britadeira, Integer laser, Integer raio, Integer bonus, Integer skin) {
        this.player = player;
        this.breakblocks = breakblocks;
        this.nivel = nivel;
        this.velocidade = velocidade;
        this.xpbooster = xpbooster;
        this.multiplicador = multiplicador;
        this.explosao = explosao;
        this.blocks = blocks;
        this.xp = xp;
        this.fortuna = fortuna;
        this.britadeira = britadeira;
        this.laser = laser;
        this.raio = raio;
        this.bonus = bonus;
        this.skin = skin;
    }


    public Integer getBreakblocks() {
        return breakblocks;
    }

    public void setBreakblocks(Integer breakblocks) {
        this.breakblocks = breakblocks;
    }

    public Integer getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(Integer velocidade) {
        this.velocidade = velocidade;
    }

    public Integer getXpbooster() {
        return xpbooster;
    }

    public void setXpbooster(Integer xpbooster) {
        this.xpbooster = xpbooster;
    }

    public Integer getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(Integer multiplicador) {
        this.multiplicador = multiplicador;
    }
    public Integer getExplosao() {
        return explosao;
    }

    public void setExplosao(Integer explosao) {
        this.explosao = explosao;
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

    public Integer getSkin() {
        return skin;
    }

    public void setSkin(Integer skin) {
        this.skin = skin;
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
    public void onUpXP() {
        if (xp >= 100) {
            player.sendMessage("§aParabéns, você alcançou o nível " + (nivel + 1) + "!");
            xp = 0;
            nivel++;
            xpplayers.put(player.getUniqueId(), player);
        }
    }
    public void verifyLevels() throws SQLException {
        switch (MySqlUtils.getPlayer(player).getNivel()) {
            case 10:
                player.sendMessage("§aParabéns, você alcançou o nível 10!");
                if (xpplayers.containsKey(player.getUniqueId())) {
                    SpawnFirework.small(player);
                    xpplayers.remove(player.getUniqueId());
                }
                break;
            case 25:
                if (xpplayers.containsKey(player.getUniqueId())) {
                    SpawnFirework.small(player);
                    xpplayers.remove(player.getUniqueId());
                }
                player.sendMessage("§aParabéns, você alcançou o nível 25!");
                break;
            case 50:
                if (xpplayers.containsKey(player.getUniqueId())) {
                    SpawnFirework.medium(player);
                    xpplayers.remove(player.getUniqueId());
                }
                player.sendMessage("§aParabéns, você alcançou o nível 50!");
                break;
            case 75:
                if (xpplayers.containsKey(player.getUniqueId())) {
                    SpawnFirework.medium(player);
                    xpplayers.remove(player.getUniqueId());
                }
                player.sendMessage("§aParabéns, você alcançou o nível 75!");
                break;
            case 150:
                if (xpplayers.containsKey(player.getUniqueId())) {
                    SpawnFirework.big(player);
                    xpplayers.remove(player.getUniqueId());
                }
                player.sendMessage("§aParabéns, você alcançou o nível 150!");
                break;
            case 300:
                if (xpplayers.containsKey(player.getUniqueId())) {
                    SpawnFirework.big(player);
                    xpplayers.remove(player.getUniqueId());
                }
                player.sendMessage("§aParabéns, você alcançou o nível 300!");
                break;
        }
    }
}
