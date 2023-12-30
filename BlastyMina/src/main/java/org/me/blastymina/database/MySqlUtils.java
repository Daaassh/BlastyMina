package org.me.blastymina.database;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.utils.players.PlayerManager;

import java.sql.*;

public class MySqlUtils {
    static FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
    static Connection connection;

    static {
        try {
            connection = MySqlConnector.getDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void savePlayer(Player p) throws SQLException {
        String query = "INSERT INTO users (uuid, blocks, nivel, xp, fortuna, britadeira,laser,raio,bonus) VALUES = (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement collection = connection.prepareStatement(query);
            collection.setString(1, String.valueOf(p.getUniqueId()));
            collection.setInt(2, 1);
            collection.setInt(3, 1);
            collection.setInt(4, 0);
            collection.setInt(5, 1);
            collection.setInt(6, 0);
            collection.setInt(7, 0);
            collection.setInt(8, 0);
            collection.setInt(9, 0);
            collection.executeUpdate();
            collection.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ MySQL ] Erro ao salvar o jogador " + p.getName() + " no banco de dados!");
            throw new RuntimeException(e);
        }
    }

    public static void updatePlayer(Player p) throws SQLException {
        String query = "UPDATE users SET blocks = ?, nivel = ?, xp = ?, fortuna = ?, britadeira = ? ,laser = ?,raio = ?, bonus = ? WHERE uuid = ?";
        try {
            PreparedStatement collection = connection.prepareStatement(query);
            collection.setInt(1, getPlayer(p).getBlocks());
            collection.setInt(2, getPlayer(p).getNivel());
            collection.setDouble(3, getPlayer(p).getXP());
            collection.setInt(4, getPlayer(p).getFortuna());
            collection.setInt(5, getPlayer(p).getBritadeira());
            collection.setInt(6, getPlayer(p).getLaser());
            collection.setInt(7, getPlayer(p).getRaio());
            collection.setInt(8, getPlayer(p).getBonus());
            collection.setString(9, String.valueOf(p.getUniqueId()));
            collection.executeUpdate();
            collection.close();

        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ MySQL ] Erro ao salvar o jogador " + p.getName() + " no banco de dados!");
            throw new RuntimeException(e);
        }

    }

    public static PlayerManager getPlayer(Player p) {
        String query = "SELECT * FROM users where uuid = ?";
        ResultSet collection;
        PlayerManager playerManager = null;
        try {
            collection = connection.createStatement().executeQuery(query);
            while (collection.next()) {
                Player player = Bukkit.getPlayer(collection.getString("uuid"));
                Integer nivel = collection.getInt("nivel");
                Integer xp = collection.getInt("xp");
                Integer fortuna = collection.getInt("fortuna");
                Integer britadeira = collection.getInt("britadeira");
                Integer laser = collection.getInt("laser");
                Integer raio = collection.getInt("raio");
                Integer bonus = collection.getInt("bonus");
                playerManager = new PlayerManager(player, nivel, xp, fortuna, britadeira, laser, raio, bonus);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playerManager;
    }


    public static void existPlayer(Player p) {
        String query = "SELECT * FROM users WHERE uuid = ?";
        try (Connection connections = MySqlConnector.getDatabase()) {
            try (PreparedStatement preparedStatement = connections.prepareStatement(query)) {
                preparedStatement.setString(1, String.valueOf(p.getUniqueId()));
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (!(resultSet.next())) {
                        savePlayer(p);
                    }
                    else {
                        updatePlayer(p);
                    }
                }
            } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        } catch (SQLException e) {
                throw new RuntimeException(e);
        }
    }
}


