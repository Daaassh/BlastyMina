package org.me.blastymina.database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.me.blastymina.BlastyMina;
import java.net.URLDecoder;
import java.sql.*;

public class MySqlConnector {
    private static FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();

    private static Connection mysqlClient;

    public MySqlConnector() throws SQLException {
        connect();
    }
    static String porta = "3306";
    static String usuario = "u1661_DZnYnjNXCa";
    static String senhaCodificada = "etnmoP4X%40MZw8r%40j2Z13d31.";
    static String nomeBanco = "s1661_polarr";

    static String senhaDecodificada = null;
    static Connection connection;
    static String url = "jdbc:mysql://" + "nac-01.haskhosting.com.br" + ":" + porta + "/" + nomeBanco;

    public static void connect() {

        try {
            senhaDecodificada = URLDecoder.decode(senhaCodificada, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(url, usuario, senhaDecodificada);
            if (!(tableExists("users"))) {
                try {
                    createUsersTable();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ MySQL ] " + ChatColor.GREEN +" Tabelas criadas com sucesso");
                }catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ MySQL ] Erro ao criar as tabelas");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getDatabase() throws SQLException {
        return DriverManager.getConnection(url, usuario, senhaDecodificada);
    }

    private static boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);
        return resultSet.next();
    }

    private static void createUsersTable() throws SQLException {
        String createTableQuery = "CREATE TABLE users (UUID VARCHAR(255), blocks INT, xp DOUBLE, fortuna INT, britadeira INT, laser INT, raio INT, bonus INT, nivel INT, skin INT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        }
    }

}
