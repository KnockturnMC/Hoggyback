package com.knockturnmc.hoggyback;

import com.knockturnmc.hoggyback.commands.HoggyBackCommand;
import com.knockturnmc.hoggyback.listeners.HoggyBackListener;
import com.knockturnmc.hoggyback.listeners.SignOffListener;
import com.knockturnmc.hoggyback.wrappers.BukkitWrapper;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HoggyBackCore extends JavaPlugin {

    private static final Map<UUID, Boolean> USER_ENABLED = new HashMap<>();
    private static HoggyBackCore instance;

    @Override
    public void onEnable(){
        setInstance(this);

        BukkitWrapper.registerListener(new HoggyBackListener());
        BukkitWrapper.registerListener(new SignOffListener());
        BukkitWrapper.registerCommand("hoggyback", new HoggyBackCommand());
    }

    public static HoggyBackCore getInstance() {
        return instance;
    }

    private static void setInstance(HoggyBackCore instance) {
        HoggyBackCore.instance = instance;
    }

    public static Map<UUID, Boolean> getUserEnabled() {
        return USER_ENABLED;
    }
}
