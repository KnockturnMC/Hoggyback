package com.knockturnmc.hoggyback.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class SignOffListener implements Listener {

    @EventHandler
    public void onLogOff(PlayerQuitEvent event) {
        if(event.getPlayer().getVehicle() != null) event.getPlayer().getVehicle().eject();
    }
}
