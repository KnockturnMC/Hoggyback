package com.knockturnmc.hoggyback.test;

import com.knockturnmc.hoggyback.HoggyBackCore;
import com.knockturnmc.hoggyback.commands.HoggyBackCommand;
import com.knockturnmc.hoggyback.util.Lang;
import com.knockturnmc.hoggyback.wrappers.BukkitWrapper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BukkitWrapper.class})
public class HoggyBackCommandTest {


    @Before
    public void setup(){
        PowerMockito.mockStatic(BukkitWrapper.class);
    }

    @Test
    public void onNotPlayer(){
        HoggyBackCommand command = new HoggyBackCommand();

        CommandSender sender = Mockito.mock(ConsoleCommandSender.class);
        Command com = Mockito.mock(Command.class);

        command.onCommand(sender, com, "hoggyback", new String[]{});

        verify(sender, times(1)).sendMessage(Lang.PLAYER_ONLY_ERROR);
    }

    @Test
    public void onPlayerNeverRunCommand(){
        HoggyBackCommand command = new HoggyBackCommand();

        Player sender = Mockito.mock(Player.class);
        Command com = Mockito.mock(Command.class);

        UUID uuid = UUID.randomUUID();
        when(sender.getUniqueId()).thenReturn(uuid);

        assertTrue(!HoggyBackCore.getUserEnabled().containsKey(uuid));

        command.onCommand(sender, com, "hoggyback", new String[]{});

        assertTrue(HoggyBackCore.getUserEnabled().containsKey(uuid));
        assertTrue(HoggyBackCore.getUserEnabled().get(uuid));
        verify(sender, times(1)).sendMessage(String.format(Lang.SET_HOGGY_BACK, ChatColor.GREEN + "ON"));
    }

    @Test
    public void onPlayerTurnOff(){
        HoggyBackCommand command = new HoggyBackCommand();

        Player sender = Mockito.mock(Player.class);
        Command com = Mockito.mock(Command.class);

        UUID uuid = UUID.randomUUID();
        when(sender.getUniqueId()).thenReturn(uuid);
        HoggyBackCore.getUserEnabled().put(uuid, true);

        assertTrue(HoggyBackCore.getUserEnabled().get(uuid));

        command.onCommand(sender, com, "hoggyback", new String[]{});

        assertTrue(HoggyBackCore.getUserEnabled().containsKey(uuid));
        assertTrue(!HoggyBackCore.getUserEnabled().get(uuid));
        verify(sender, times(1)).sendMessage(String.format(Lang.SET_HOGGY_BACK, ChatColor.RED + "OFF"));
    }

    @Test
    public void onPlayerTurnOn(){
        HoggyBackCommand command = new HoggyBackCommand();

        Player sender = Mockito.mock(Player.class);
        Command com = Mockito.mock(Command.class);

        UUID uuid = UUID.randomUUID();
        when(sender.getUniqueId()).thenReturn(uuid);
        HoggyBackCore.getUserEnabled().put(uuid, false);

        assertTrue(!HoggyBackCore.getUserEnabled().get(uuid));

        command.onCommand(sender, com, "hoggyback", new String[]{});

        assertTrue(HoggyBackCore.getUserEnabled().containsKey(uuid));
        assertTrue(HoggyBackCore.getUserEnabled().get(uuid));
        verify(sender, times(1)).sendMessage(String.format(Lang.SET_HOGGY_BACK, ChatColor.GREEN + "ON"));
    }

}
