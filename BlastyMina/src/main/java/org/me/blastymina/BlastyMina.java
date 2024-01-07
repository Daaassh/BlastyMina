
package org.me.blastymina;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.me.blastymina.commands.MinaCommand;
import org.me.blastymina.commands.SetLocationMina;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.database.MySqlConnector;
import org.me.blastymina.events.onBlockBreak;
import org.me.blastymina.events.onInventoryClick;
import org.me.blastymina.events.onPlayerInteract;
import org.me.blastymina.events.onPlayerJoin;
import org.me.blastymina.utils.placeholder.BlastyExpansion;

import java.io.IOException;
import java.sql.SQLException;


public final class BlastyMina
extends JavaPlugin {
    private static ProtocolManager manager;
    private BlastyMina instance;

    public void onEnable() {
        instance = this;
        manager = ProtocolLibrary.getProtocolManager();
        saveDefaultConfig();
        registerEvents();
        try {
            new MySqlConnector();
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GREEN + "MySQL carregado.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            reloadConfig();
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GREEN + "Configurac\u0327o\u0303es carregadas.");
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.RED + "Erro ao carregar as configurac\u0327o\u0303es.");
        }
        try {
            verifySections();
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        getCommand("mina").setExecutor(new MinaCommand());
        try {
            getCommand("minaadm").setExecutor(new SetLocationMina());
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GREEN + "Plugin carregado.");
        verifyDependencies();
    }

    public static ProtocolManager getManager() {
        return manager;
    }

    private void registerEvents() {
        try {
            getServer().getPluginManager().registerEvents(new onPlayerJoin(), instance);
            getServer().getPluginManager().registerEvents(new onInventoryClick(), instance);
            getServer().getPluginManager().registerEvents(new onPlayerInteract(), instance);
            getServer().getPluginManager().registerEvents(new onBlockBreak(), instance);
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GREEN + "Eventos carregados.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void verifyDependencies() {
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.RED + "Plugin 'ProtocolLib' n\u00e3o encontrado.");
            getServer().getPluginManager().disablePlugin(this);
        }
        else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GREEN + "Plugin 'ProtocolLib' encontrado.");
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.RED + "Plugin 'PlaceholderAPI' n\u00e3o encontrado.");
            getServer().getPluginManager().disablePlugin(this);
        }
        else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GREEN + "Plugin 'PlaceholderAPI' encontrado.");
            new BlastyExpansion(this).register();
        }
    }
    private void verifySections() throws IOException, InvalidConfigurationException {
        CustomFileConfiguration enchants = new CustomFileConfiguration("enchants.yml", this);
        CustomFileConfiguration rewards = new CustomFileConfiguration("rewards.yml", this);
        CustomFileConfiguration locations = new CustomFileConfiguration("locations.yml", this);
        CustomFileConfiguration skins = new CustomFileConfiguration("skins.yml", this);
        CustomFileConfiguration materials = new CustomFileConfiguration("materials.yml", this);
        if (rewards.getConfigurationSection("rewards.itens") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.RED + "Nenhuma recompensa foi configurada.");
        }
        else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GREEN + "Recompensas carregadas.");
        }
        if (enchants.getConfigurationSection("enchants") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.RED + "Nenhum encantamento foi configurado.");
        }
        else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GREEN + "Encantamentos carregados.");
        }
        if (skins.getConfigurationSection("skins") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.RED + "Nenhuma skin foi configurada.");
        }
        else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GREEN + "Skins carregadas.");
        }
        if (locations.getConfigurationSection("spawn") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.RED + "Nenhuma localiza\u00e7\u00e3o foi configurada.");
        }
        else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GREEN + "Localiza\u00e7\u00f5es carregadas.");
        }
        if (materials.getConfigurationSection("blocks") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.RED + "Nenhum material foi configurado.");
        }
        else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GREEN + "Materiais carregados.");
        }
    }
}

