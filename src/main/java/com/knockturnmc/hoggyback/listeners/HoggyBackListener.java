package com.knockturnmc.hoggyback.listeners;

import com.knockturnmc.hoggyback.HoggyBackCore;
import com.knockturnmc.hoggyback.util.Lang;
import com.knockturnmc.hoggyback.util.Permission;
import com.knockturnmc.hoggyback.wrappers.BukkitWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A listener for if the user is being hoggy backed or not.
 */
public class HoggyBackListener implements Listener {

    private static final Map<UUID, Long> COOLDOWN = new HashMap<UUID, Long>();

    /**
     * Checks if a player is shift right clicking and if so, added a passenger to their head.
     * @param event Interact event.
     */
    @EventHandler
    public void onShiftLeftClick(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();

        if(!player.isSneaking()) return;

        if(!player.hasPermission(Permission.HOGGY_BACK_PERMS)) return;

        Entity entity = event.getRightClicked();

        if(!(entity instanceof LivingEntity)) return;

        if((!(entity instanceof Player)) && !player.hasPermission(Permission.HOGGY_BACK_ANIMAL_PICKUP)) return;

        LivingEntity animal = (LivingEntity) entity;

        if(player.getPassengers().isEmpty()) {
            addPassengerCheck(player, animal);
        }
        else {
            Entity pass = player.getPassengers().get(0);
            player.eject();
            pass.setVelocity(player.getEyeLocation().getDirection().add(player.getVelocity().multiply(.7)));
            addPassengerCheck(player, animal);
        }
    }

    /**
     * Checks if an entity is a passenger, if so throws them off.
     *
     * @param event interact event.
     */
    @EventHandler
    public void onThrowClick(PlayerInteractEvent event){
        if(event.getAction() != Action.LEFT_CLICK_AIR) return;

        Player player = event.getPlayer();
        if(player.getPassengers().isEmpty()) return;

        Entity entity = player.getPassengers().get(0);

        player.eject();
        entity.setVelocity(player.getEyeLocation().getDirection().add(player.getVelocity().multiply(.7)));
    }

    private void addPassengerCheck(Player player, LivingEntity entity) {
        if(entity instanceof Player) {
            Player hoggy = (Player) entity;
            UUID uuid = hoggy.getUniqueId();

            Map<UUID, Boolean> enabled = HoggyBackCore.getUserEnabled();
            if (!enabled.containsKey(uuid) || !enabled.get(uuid)) {
                if(!COOLDOWN.containsKey(player.getUniqueId()) ||
                        (COOLDOWN.get(player.getUniqueId()) + 1000 * 5L < System.currentTimeMillis())) {
                    player.sendMessage(Lang.ENABLED_ERROR);
                    COOLDOWN.put(player.getUniqueId(), System.currentTimeMillis());
                }
                return;
            }
        }

        player.addPassenger(entity);
    }

    @EventHandler
    public void onTeleportDismount(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        final List<Entity> passengers = player.getPassengers();
        Entity vehicle = player.getVehicle();

        if(vehicle != null) {
            vehicle.eject();
            vehicle.teleport(event.getTo());
        }

        if(passengers.size() > 0) {
            player.eject();

            BukkitWrapper.scheduleTaskLater(() -> passengers.forEach(player::addPassenger), 10L);
        }
    }

    public static Map<UUID, Long> getCooldown() {
        return COOLDOWN;
    }
}
