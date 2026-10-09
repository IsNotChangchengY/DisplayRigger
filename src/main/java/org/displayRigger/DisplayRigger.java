package org.displayRigger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.displayRigger.Listener.ClickListener;
import org.displayRigger.Listener.InteractingDisplayListener;
import org.displayRigger.Listener.SelectListener;
import org.displayRigger.command.DrCommand;
import org.displayRigger.common.GroupManager;

import java.util.Objects;

public final class DisplayRigger extends JavaPlugin {

    @Override
    public void onEnable() {
        saveResource("config.yml", false);
        saveResource("data.yml", false);

        GroupManager.load();

        Objects.requireNonNull(Bukkit.getPluginCommand("dr")).setExecutor(new DrCommand());
        Bukkit.getPluginManager().registerEvents(new SelectListener(), this);
        Bukkit.getPluginManager().registerEvents(new ClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractingDisplayListener(), this);

        Bukkit.getLogger().info(ChatColor.GREEN + "DisplayRigger enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "DisplayRigger disabled!");
    }
}