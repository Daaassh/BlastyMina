package org.me.blastymina.utils.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.utils.players.PlayerManager;

import java.sql.SQLException;

public class BlastyExpansion extends PlaceholderExpansion  {
    private BlastyMina plugin;

    public BlastyExpansion(BlastyMina plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "blasty";
    }

    @Override
    public String getIdentifier() {
        return "blastymina";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }


    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (player == null)
            return null;
        PlayerManager manager;
        Player players = player.getPlayer();
        try {
            manager = MySqlUtils.getPlayer(players);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (params.equalsIgnoreCase("blocks")) {
            return "" + manager.getBlocks();
        }
        if (params.equalsIgnoreCase("nivel")) {
            return "" + manager.getNivel();
        }
        if (params.equalsIgnoreCase("xp")) {
            return "" + String.valueOf(manager.getXP());
        }
        return null;
    }
}
