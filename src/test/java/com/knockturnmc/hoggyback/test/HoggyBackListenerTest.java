package com.knockturnmc.hoggyback.test;

import com.knockturnmc.hoggyback.HoggyBackCore;
import com.knockturnmc.hoggyback.listeners.HoggyBackListener;
import com.knockturnmc.hoggyback.util.Lang;
import com.knockturnmc.hoggyback.util.Permission;
import com.knockturnmc.hoggyback.wrappers.BukkitWrapper;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BukkitWrapper.class})
public class HoggyBackListenerTest {

    @Before
    public void setup(){
        PowerMockito.mockStatic(BukkitWrapper.class);
    }

    @Test
    public void onShiftclickPermError(){
        HoggyBackListener listener = new HoggyBackListener();
        Player player = Mockito.mock(Player.class);
        Entity entity = Mockito.mock(Player.class);

        when(player.hasPermission(Permission.HOGGY_BACK_PERMS)).thenReturn(false);
        when(player.isSneaking()).thenReturn(true);

        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, entity);

        listener.onShiftLeftClick(event);

        verify(player, times(1)).hasPermission(Permission.HOGGY_BACK_PERMS);
    }

    @Test
    public void onNotLivingReturn(){
        HoggyBackListener listener = new HoggyBackListener();
        Player player = Mockito.mock(Player.class);
        Entity entity = Mockito.mock(Arrow.class);

        when(player.hasPermission(Permission.HOGGY_BACK_PERMS)).thenReturn(true);

        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, entity);

        listener.onShiftLeftClick(event);

        verify(player, times(0)).getPassengers();
    }

    @Test
    public void onNotPlayerPickUp(){
        HoggyBackListener listener = new HoggyBackListener();
        Player player = Mockito.mock(Player.class);
        Entity entity = Mockito.mock(Pig.class);

        List<Entity> passengers = new ArrayList<Entity>();

        when(player.hasPermission(Permission.HOGGY_BACK_PERMS)).thenReturn(true);
        when(player.getPassengers()).thenReturn(passengers);
        when(player.isSneaking()).thenReturn(true);

        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, entity);

        listener.onShiftLeftClick(event);

        verify(player, times(1)).addPassenger(entity);
    }

    @Test
    public void onPlayerPickUpFail(){
        HoggyBackListener listener = new HoggyBackListener();
        Player player = Mockito.mock(Player.class);
        Entity entity = Mockito.mock(Player.class);

        List<Entity> passengers = new ArrayList<Entity>();

        when(player.hasPermission(Permission.HOGGY_BACK_PERMS)).thenReturn(true);
        when(player.getPassengers()).thenReturn(passengers);
        when(player.isSneaking()).thenReturn(true);


        UUID uuid = UUID.randomUUID();
        when(player.getUniqueId()).thenReturn(uuid);

        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, entity);

        listener.onShiftLeftClick(event);

        verify(player, times(1)).sendMessage(Lang.ENABLED_ERROR);

        assertTrue(HoggyBackListener.getCooldown().containsKey(uuid));

        verify(player, times(0)).addPassenger(entity);
    }

    @Test
    public void onPlayerPickUpFailDueToOff(){
        HoggyBackListener listener = new HoggyBackListener();
        Player player = Mockito.mock(Player.class);
        Entity entity = Mockito.mock(Player.class);

        List<Entity> passengers = new ArrayList<Entity>();

        when(player.hasPermission(Permission.HOGGY_BACK_PERMS)).thenReturn(true);
        when(player.getPassengers()).thenReturn(passengers);
        when(player.isSneaking()).thenReturn(true);


        UUID uuid = UUID.randomUUID();
        UUID uuid1 = UUID.randomUUID();
        when(player.getUniqueId()).thenReturn(uuid);
        when(entity.getUniqueId()).thenReturn(uuid1);

        HoggyBackCore.getUserEnabled().put(uuid1, false);

        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, entity);

        listener.onShiftLeftClick(event);

        verify(player, times(1)).sendMessage(Lang.ENABLED_ERROR);

        assertTrue(HoggyBackListener.getCooldown().containsKey(uuid));
        verify(player, times(0)).addPassenger(entity);
    }

    @Test
    public void onPlayerPickUpFailButNoMessage(){
        HoggyBackListener listener = new HoggyBackListener();
        Player player = Mockito.mock(Player.class);
        Entity entity = Mockito.mock(Player.class);

        List<Entity> passengers = new ArrayList<Entity>();

        when(player.hasPermission(Permission.HOGGY_BACK_PERMS)).thenReturn(true);
        when(player.getPassengers()).thenReturn(passengers);
        when(player.isSneaking()).thenReturn(true);


        UUID uuid = UUID.randomUUID();
        when(player.getUniqueId()).thenReturn(uuid);
        HoggyBackListener.getCooldown().put(uuid, System.currentTimeMillis());

        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, entity);

        listener.onShiftLeftClick(event);

        verify(player, times(0)).sendMessage(Lang.ENABLED_ERROR);

        assertTrue(HoggyBackListener.getCooldown().containsKey(uuid));

        verify(player, times(0)).addPassenger(entity);
    }

    @Test
    public void onPlayerPickUpPass(){
        HoggyBackListener listener = new HoggyBackListener();
        Player player = Mockito.mock(Player.class);
        Entity entity = Mockito.mock(Player.class);

        List<Entity> passengers = new ArrayList<Entity>();

        when(player.hasPermission(Permission.HOGGY_BACK_PERMS)).thenReturn(true);
        when(player.getPassengers()).thenReturn(passengers);
        when(player.isSneaking()).thenReturn(true);


        UUID uuid = UUID.randomUUID();
        UUID uuid1 = UUID.randomUUID();
        when(player.getUniqueId()).thenReturn(uuid);
        when(entity.getUniqueId()).thenReturn(uuid1);

        HoggyBackCore.getUserEnabled().put(uuid1, true);

        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, entity);

        listener.onShiftLeftClick(event);

        verify(player, times(1)).addPassenger(entity);
    }

    @Test
    public void onNotPlayerPickUpAndThrowFirst(){
        HoggyBackListener listener = new HoggyBackListener();
        Player player = Mockito.mock(Player.class);
        Entity entityA = Mockito.mock(Pig.class);
        Entity entityB = Mockito.mock(Chicken.class);

        List<Entity> passengers = new ArrayList<Entity>();
        passengers.add(entityB);

        when(player.hasPermission(Permission.HOGGY_BACK_PERMS)).thenReturn(true);
        when(player.getPassengers()).thenReturn(passengers);

        Location location = Mockito.mock(Location.class);
        Vector vector = new Vector(1,1,1);

        when(player.getEyeLocation()).thenReturn(location);
        when(player.getVelocity()).thenReturn(vector);
        when(location.getDirection()).thenReturn(vector);
        when(player.isSneaking()).thenReturn(true);

        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, entityA);

        listener.onShiftLeftClick(event);

        verify(player, times(1)).eject();

        verify(entityB, times(1)).setVelocity(any(Vector.class));

        verify(player, times(1)).addPassenger(entityA);
    }

    @Test
    public void cannotThrowEmpty(){
        HoggyBackListener listener = new HoggyBackListener();

        Player player = Mockito.mock(Player.class);

        List<Entity> entities = new ArrayList<Entity>();



        when(player.getPassengers()).thenReturn(entities);

        PlayerInteractEvent event = new PlayerInteractEvent(player, Action.LEFT_CLICK_AIR, null, null, null);

        listener.onThrowClick(event);

        verify(player, times(1)).getPassengers();

        verify(player, times(0)).eject();
    }

    @Test
    public void throwOnClick(){
        HoggyBackListener listener = new HoggyBackListener();

        Player player = Mockito.mock(Player.class);

        List<Entity> entities = new ArrayList<Entity>();

        LivingEntity entity = Mockito.mock(Pig.class);
        entities.add(entity);

        when(player.getPassengers()).thenReturn(entities);

        Location location = Mockito.mock(Location.class);
        Vector vector = new Vector(1,1,1);

        when(player.getEyeLocation()).thenReturn(location);
        when(player.getVelocity()).thenReturn(vector);
        when(location.getDirection()).thenReturn(vector);

        PlayerInteractEvent event = new PlayerInteractEvent(player, Action.LEFT_CLICK_AIR, null, null, null);

        listener.onThrowClick(event);

        verify(player, times(1)).eject();
        verify(entity, times(1)).setVelocity(any(Vector.class));
    }
}
