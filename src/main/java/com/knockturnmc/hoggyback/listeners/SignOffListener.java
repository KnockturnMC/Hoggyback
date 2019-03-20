package com.knockturnmc.hoggyback.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * A listener for when a player signs off.
 */
public class SignOffListener implements Listener {

    /**
     * This event ejects players who are on a vehicle to prevent an error.
     *
     * @param event Quit event
     */
    @EventHandler
    public void onLogOff(PlayerQuitEvent event) {
        if(event.getPlayer().getVehicle() != null) event.getPlayer().getVehicle().eject();
    }
}
