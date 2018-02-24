package com.knockturnmc.hoggyback.wrappers;

import com.knockturnmc.hoggyback.HoggyBackCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public class BukkitWrapper {

    private BukkitWrapper(){}

    public static void registerListener(Listener listener){
        HoggyBackCore.getInstance().getServer().getPluginManager().registerEvents(listener, HoggyBackCore.getInstance());
    }

    public static void bukkitBroadcast(String message) {
        Bukkit.broadcastMessage(message);
    }

    public static void registerCommand(String command, CommandExecutor cmd){
        HoggyBackCore.getInstance().getCommand(command).setExecutor(cmd);
    }

}
