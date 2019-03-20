package com.knockturnmc.hoggyback.util;

import org.bukkit.ChatColor;

/**
 * A static class that is used for language related to this plugin.
 */
public final class Lang {

    /**
     * As all fields in this class are static, the class doesn't need to be constructed.
     */
    private Lang(){}

    public static final String ENABLED_ERROR = ChatColor.RED + "That player does not have hoggyback turned on.  " +
            "Tell them to turn on /hoggyback";

    public static final String PLAYER_ONLY_ERROR = ChatColor.RED + "You must be a player to hoggyback.";

    public static final String SET_HOGGY_BACK = "You have now set hoggyback to : %1$s";

}
