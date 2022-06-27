package me.emiel.esclockupcells.Commands;

import me.emiel.esclockupcells.CellManager;
import me.emiel.esclockupcells.Helper.MessageSender;
import me.emiel.esclockupcells.Items.CellVoucher;
import me.emiel.esclockupcells.Models.Size;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetCellVoucher implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if(args.length > 0){
            Size size = CellManager.stringToSize(args[0]);
            if (size != null){
                player.getInventory().addItem(CellVoucher.getVoucher(size));
                MessageSender.SendMessageWithPrefix(player, "Successfully given a voucher for a &b&l" + size.name() +"&r&f cell!");
                return true;
            }
        }
        MessageSender.SendErrorWithPrefix(player, "Please use the command like this: /getcellvoucher <small,medium,large>");
        return true;
    }
}
