package com.knockturnmc.hoggyback.test;

import com.knockturnmc.hoggyback.listeners.SignOffListener;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignOffListenerTest {

    @Test
    public void onSignOff(){
        SignOffListener listener = new SignOffListener();

        Player player = Mockito.mock(Player.class);
        Player vehicle = Mockito.mock(Player.class);

        PlayerQuitEvent event = new PlayerQuitEvent(player, "");

        when(player.getVehicle()).thenReturn(vehicle);

        listener.onLogOff(event);

        verify(vehicle, times(1)).eject();

    }
}
