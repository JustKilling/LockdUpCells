package me.emiel.esclockupcells.Commands;

import me.emiel.esclockupcells.CellManager;
import me.emiel.esclockupcells.Models.Cell;
import me.emiel.esclockupeconomy.CoinManager;
import me.emiel.esclockupeconomy.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddTrust implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;

        if(args.length < 1){
            MessageSender.SendErrorWithPrefix(p, "Please use the command like this: ");
            MessageSender.SendErrorWithPrefix(p, "/trust <name>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if(!target.hasPlayedBefore()){
            MessageSender.SendErrorWithPrefix(p, "This player was not found!");
            return true;
        }
        if(target.getUniqueId() == p.getUniqueId()){
            MessageSender.SendErrorWithPrefix(p, "You can't trust yourself!");
            return true;
        }
        Cell cell = CellManager.getCellFromPlayer(p.getUniqueId());
        if(cell == null){
            MessageSender.SendErrorWithPrefix(p, "You don't have a cell!");
            return true;
        }
        if(CellManager.isPlayerTrusted(cell.get_cellid(), target.getUniqueId())){
            MessageSender.SendErrorWithPrefix(p, "This player is already trusted!");
            return true;
        }
        if(CellManager.isFull(cell)){
            MessageSender.SendErrorWithPrefix(p, "You can't add any more players!");
            return true;
        }
        CellManager.addTrusted(cell, target.getUniqueId());
        MessageSender.SendMessageWithPrefix(p, "&fTrusted &b&l"+ target.getName() + "&r&f!");
        return true;
    }
}
