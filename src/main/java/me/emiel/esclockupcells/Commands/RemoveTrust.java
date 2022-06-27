package me.emiel.esclockupcells.Commands;

import me.emiel.esclockupcells.CellManager;
import me.emiel.esclockupcells.Models.Cell;
import me.emiel.esclockupeconomy.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveTrust implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;

        if(args.length < 1){
            MessageSender.SendErrorWithPrefix(p, "Please use the command like this: ");
            MessageSender.SendErrorWithPrefix(p, "/untrust <name>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if(!target.hasPlayedBefore()){
            MessageSender.SendErrorWithPrefix(p, "This player was not found!");
            return true;
        }

        Cell cell = CellManager.getCellFromPlayer(p.getUniqueId());
        if(cell == null){
            MessageSender.SendErrorWithPrefix(p, "You don't have a cell!");
            return true;
        }
        if(!CellManager.removeTrusted(cell, target.getUniqueId())){
            MessageSender.SendErrorWithPrefix(p, "This player isn't trusted!");
            return true;
        }
        MessageSender.SendMessageWithPrefix(p, "&fUntrusted &b&l"+ target.getName() + "&r&f!");
        return true;
    }
}
