package org.displayRigger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.displayRigger.command.DrCommand;

import java.util.Objects;

public final class DisplayRigger extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(Bukkit.getPluginCommand("dr")).setExecutor(new DrCommand());
        Bukkit.getLogger().info(ChatColor.GREEN + "DisplayRigger enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "DisplayRigger disabled!");
    }
}