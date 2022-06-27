package me.emiel.esclockupcells.Commands;

import me.emiel.esclockupcells.CellManager;
import me.emiel.esclockupcells.Helper.DoorManager;
import me.emiel.esclockupcells.Helper.MessageSender;
import me.emiel.esclockupcells.Models.Cell;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetCellDoor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if(args.length <= 0){
            MessageSender.SendErrorWithPrefix(p, "Please use the command like this:");
            MessageSender.SendErrorWithPrefix(p, "&r&l/getcelldoor <cellname>");
            return true;
        }
        Cell cell = CellManager.findCellByName(args[0]);
        if(cell == null){
            MessageSender.SendErrorWithPrefix(p, "This cell does not exist!");
            return true;
        }
        ItemStack stack = DoorManager.getDoor(cell.get_cellid());
        p.getInventory().addItem(stack);
        MessageSender.SendMessageWithPrefix(p, "Given the door for cell: &l&b" + cell.get_cellName());

        return true;
    }
}
