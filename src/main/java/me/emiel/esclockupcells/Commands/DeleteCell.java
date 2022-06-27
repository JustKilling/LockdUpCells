package me.emiel.esclockupcells.Commands;

import me.emiel.esclockupcells.CellManager;
import me.emiel.esclockupcells.Helper.MessageSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteCell implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can execute this command!");
            return true;
        }
        Player player = (Player) sender;
        if(args.length <= 0) {
            MessageSender.SendErrorWithPrefix(player, "Please provide a cell name!");
            return true;
        }
        if(CellManager.findCellByName(args[0]) == null){
            MessageSender.SendErrorWithPrefix(player, "This cell does not exist!");
            return true;
        }
        CellManager.deleteCellByName(args[0]);
        MessageSender.SendMessageWithPrefix(player,"Cell successfully deleted!");
        return true;
    }
}
