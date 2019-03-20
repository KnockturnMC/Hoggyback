package com.knockturnmc.hoggyback.commands;

import com.knockturnmc.hoggyback.HoggyBackCore;
import com.knockturnmc.hoggyback.util.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

/**
 * A command to disable or enable hoggyback.
 */
public class HoggyBackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Lang.PLAYER_ONLY_ERROR);
            return false;
        }

        Player player = (Player) sender;

        boolean on = true;

        Map<UUID, Boolean> enabled = HoggyBackCore.getUserEnabled();

        if(enabled.containsKey(player.getUniqueId())) on = !enabled.get(player.getUniqueId());

        String enab = on? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF";

        player.sendMessage(String.format(Lang.SET_HOGGY_BACK, enab));

        enabled.put(player.getUniqueId(), on);

        return true;
    }

}
