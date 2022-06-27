package me.emiel.esclockupcells.Commands;

import me.emiel.esclockupcells.GUI.RemoveAllCellsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveAllCells implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player p = (Player)sender;
        RemoveAllCellsGUI gui = new RemoveAllCellsGUI();
        gui.openInventory(p);
        return true;
    }
}
