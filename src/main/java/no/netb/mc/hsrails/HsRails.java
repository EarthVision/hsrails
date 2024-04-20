package no.netb.mc.hsrails;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class HsRails extends JavaPlugin {

    private static Set<CommandSender> receivedHeadsUp = new HashSet<>();

    @Override
    public void onEnable() {
        saveDefaultConfig(); // copies default file to data folder, will not override existing file

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new MinecartListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("unloading...");
    }
}
