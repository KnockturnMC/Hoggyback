package com.knockturnmc.hoggyback.wrappers;

import com.knockturnmc.hoggyback.HoggyBackCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

/**
 * A wrapper class created to make testing easier.
 */
public class BukkitWrapper {

    private BukkitWrapper(){}

    /**
     * Registers a new listener with this plugin.
     * @param listener listener to register.
     */
    public static void registerListener(Listener listener){
        HoggyBackCore.getInstance().getServer().getPluginManager()
                .registerEvents(listener, HoggyBackCore.getInstance());
    }

    /**
     * Registers a new command with this plugin.
     * @param command command to register.
     * @param cmd Command executor class.
     */
    public static void registerCommand(String command, CommandExecutor cmd){
        HoggyBackCore.getInstance().getCommand(command).setExecutor(cmd);
    }

    public static void scheduleTaskLater(Runnable runnable, long time){
        Bukkit.getScheduler().runTaskLater(HoggyBackCore.getInstance(), runnable, time);
    }

}
