package me.emiel.esclockupcells.Commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.session.SessionOwner;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.emiel.esclockupcells.CellManager;
import me.emiel.esclockupcells.EscLockupCells;
import me.emiel.esclockupcells.Helper.DoorManager;
import me.emiel.esclockupcells.Helper.MessageSender;
import me.emiel.esclockupcells.Models.Cell;
import me.emiel.esclockupcells.Models.Size;
import org.apache.logging.log4j.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class CreateCell implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can execute this command!");
            return true;
        }
        Player player = (Player) sender;

        //check if the arguments are right
        if(!CheckArgs(args)){
            MessageSender.SendErrorWithPrefix(player, "Please use the command like this: /createcell <cell name> <small,medium,large>");
            return true;
        }

        //check if template has been initialized in the world where the player is.
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        ProtectedRegion pr = container.get(BukkitAdapter.adapt(player.getWorld())).getRegion(EscLockupCells.get_instance().getConfig().getString("cell-template.name"));
        if(pr == null){
            MessageSender.SendMessage(player, "----------------------------------------------");
            MessageSender.SendErrorWithPrefix(player, "The cells plugin has not been initialized in this world.");
            MessageSender.SendErrorWithPrefix(player, "&fPlease do &l&b/initializecells");
            MessageSender.SendMessage(player, "----------------------------------------------");

            return true;
        }

        String name = args[0];
        String size = args[1];

        if(CellManager.findCellByName(name) != null){
            MessageSender.SendErrorWithPrefix(player, "This cell already exists!");
            return true;
        }

        Actor actor = BukkitAdapter.adapt(player); // Worldedits player class
        SessionManager manager = WorldEdit.getInstance().getSessionManager();
        LocalSession localSession = manager.get(actor);

        //get the region
        Region region;
        try {
            region = localSession.getSelection();
            //check if valid region
            if(region == null){
                MessageSender.SendErrorWithPrefix(player, "Please select a valid region before executing this command!");
                return true;
            }
        } catch (IncompleteRegionException e) {
            MessageSender.SendErrorWithPrefix(player, "Please select a valid region before executing this command!");
            return true;
        }

        Cell createdCell;

        if(size.equalsIgnoreCase("small")){
            createdCell = CellManager.CreateCell(region, name, Size.Small);

        }else if(size.equalsIgnoreCase("medium")){
            createdCell = CellManager.CreateCell(region, name, Size.Medium);

        }else if(size.equalsIgnoreCase("large")){
            createdCell = CellManager.CreateCell(region, name, Size.Large);
        }
        else{
            MessageSender.SendErrorWithPrefix(player, "Please use the command like this: /createcell <cell name> <small,medium,large>");
            return true;
        }



        MessageSender.SendMessageWithPrefix(player, "&aSuccessfully created cell!");
        //give door to player
        player.getInventory().addItem(DoorManager.getDoor(createdCell.get_cellid()));

        return true;

    }

    private boolean CheckArgs(String[] args) {
        if(args.length != 2) return false;
        if(!args[1].equalsIgnoreCase("small") && !args[1].equalsIgnoreCase("medium") && !args[1].equalsIgnoreCase("large")) return false;
        return true;
    }


}
