package com.knockturnmc.hoggyback;

import com.knockturnmc.hoggyback.commands.HoggyBackCommand;
import com.knockturnmc.hoggyback.listeners.HoggyBackListener;
import com.knockturnmc.hoggyback.listeners.SignOffListener;
import com.knockturnmc.hoggyback.wrappers.BukkitWrapper;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The core class that extends JavaPlugin from Spigot.  This also stores all the users who have
 * hoggyback enabled in a hash map.
 */
public class HoggyBackCore extends JavaPlugin {

    private static final Map<UUID, Boolean> USER_ENABLED = new HashMap<UUID, Boolean>();
    private static HoggyBackCore instance;

    /**
     * Enables the plugins, setting the instance, registering the listeners, and
     * registering the command.
     */
    @Override
    public void onEnable(){
        setInstance(this);

        BukkitWrapper.registerListener(new HoggyBackListener());
        BukkitWrapper.registerListener(new SignOffListener());
        BukkitWrapper.registerCommand("hoggyback", new HoggyBackCommand());
    }

    /**
     * Getting the instance of this java plugin.
     * @return Instance.
     */
    public static HoggyBackCore getInstance() {
        return instance;
    }

    /**
     * Setting the instance of this plugin, this is private because it should only be used once in
     * the on enable of this class.
     *
     * @param instance instance of this class.
     */
    private static void setInstance(HoggyBackCore instance) {
        HoggyBackCore.instance = instance;
    }

    /**
     * The map of users who have hoggyback enabled or not.
     *
     * @return Hashmap of enabled or not users.
     */
    public static Map<UUID, Boolean> getUserEnabled() {
        return USER_ENABLED;
    }
}
