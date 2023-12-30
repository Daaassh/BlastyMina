package org.me.blastymina.database;


import com.comphenix.protocol.wrappers.BukkitConverters;
import net.bytebuddy.dynamic.scaffold.MethodRegistry;
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
            if (connection == null) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ MySQL ] Erro ao conectar ao banco de dados!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void savePlayer(Player p) throws SQLException {
        String query = "INSERT INTO users (uuid, blocks, nivel, xp, fortuna, britadeira,laser,raio,bonus, skin) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement collection = connection.prepareStatement(query);
            collection.setString(1, String.valueOf(p.getUniqueId()));
            collection.setInt(2, 1);
            collection.setInt(3, 1);
            collection.setDouble(4, 1.0);
            collection.setInt(5, 1);
            collection.setInt(6, 0);
            collection.setInt(7, 0);
            collection.setInt(8, 0);
            collection.setInt(9, 0);
            collection.setInt(10,1);
            collection.executeUpdate();
            collection.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ MySQL ] Erro ao salvar o jogador " + p.getName() + " no banco de dados!");
            throw new RuntimeException(e);
        }
    }

    public static void updatePlayer(PlayerManager manager, Player p) {
        String query = "UPDATE users SET blocks = ?, nivel = ?, xp = ?, fortuna = ?, britadeira = ? ,laser = ?,raio = ?, bonus = ?, skin = ? WHERE uuid = ?";
        try {
            PreparedStatement collection = connection.prepareStatement(query);
            collection.setInt(1, manager.getBlocks());
            collection.setInt(2, manager.getNivel());
            collection.setDouble(3, manager.getXP());
            collection.setInt(4, manager.getFortuna());
            collection.setInt(5, manager.getBritadeira());
            collection.setInt(6, manager.getLaser());
            collection.setInt(7, manager.getRaio());
            collection.setInt(8, manager.getBonus());
            collection.setInt(9, manager.getSkin());
            collection.setString(10, String.valueOf(manager.getPlayer().getUniqueId()));
            collection.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ MySQL ] Erro ao salvar o jogador " + p.getName() + " no banco de dados!");
            throw new RuntimeException(e);
        }

    }

    public static PlayerManager getPlayer(Player player) throws SQLException {
        String query = "SELECT * FROM users WHERE uuid = ?";
        PlayerManager manager = null;
        try (Connection connect = MySqlConnector.getDatabase();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, String.valueOf(player.getUniqueId()));
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    int nivel = results.getInt("nivel");
                    int blocks = results.getInt("blocks");
                    double xp = results.getDouble("xp");
                    int fortuna = results.getInt("fortuna");
                    int britadeira = results.getInt("britadeira");
                    int laser = results.getInt("laser");
                    int raio = results.getInt("raio");
                    int bonus = results.getInt("bonus");
                    int skin = results.getInt("skin");
                    manager = new PlayerManager(player, blocks,nivel, xp, fortuna, britadeira, laser, raio, bonus, skin);
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ MySQL ] " + ChatColor.RED + "Nenhuma informação encontrada para o jogador " + player.getName());
                }
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ MySQL ] Erro ao pegar as informações do jogador " + player.getName());
                throw new RuntimeException(e);
            }
        }

        return manager;
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
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ MySQL ] O jogador " + p.getName() + " ja existe no banco de dados!");
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


