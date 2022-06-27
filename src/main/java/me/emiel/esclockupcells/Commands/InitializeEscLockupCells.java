package me.emiel.esclockupcells.Commands;

import me.emiel.esclockupcells.CellManager;
import me.emiel.esclockupcells.Helper.MessageSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InitializeEscLockupCells implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            CellManager.InstantiateCellTemplate(p.getWorld());
            MessageSender.SendMessageWithPrefix(p, "Successfully initialized the cells in world: " + p.getWorld().getName());
        }else{
            sender.sendMessage("Be a player!");
        }
        return true;
    }
}
