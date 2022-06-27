package me.emiel.esclockupcells.Helper;

import me.emiel.esclockupcells.EscLockupCells;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageSender {

    public static void SendMessage(Player player, String message){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void SendMessageWithPrefix(Player player, String message){

        String prefix = EscLockupCells.get_instance().getConfig().getString("prefix");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &l|&r "+ message));
    }
    public static void SendErrorWithPrefix(Player player, String message){
        String prefix = EscLockupCells.get_instance().getConfig().getString("prefix");
        String code = EscLockupCells.get_instance().getConfig().getString("errorcolorcode");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix  + " &l|&r "+  code + message));
    }


    public static void BroadcastMessageWithPrefix(String message) {
        String prefix = EscLockupCells.get_instance().getConfig().getString("prefix");
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &l|&r "+ message));
    }
}
