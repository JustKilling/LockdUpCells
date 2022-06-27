package me.emiel.esclockupcells.Commands;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.util.WorldEditRegionConverter;
import me.emiel.esclockupcells.CellManager;
import me.emiel.esclockupcells.Helper.MessageSender;
import me.emiel.esclockupcells.Models.Cell;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ListCells implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can execute this command!");
            return true;
        }
        Player p = (Player) sender;
        MessageSender.SendMessage(p, "-------------- All current cells --------------");
        ArrayList<Cell> cells = CellManager.getCells();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();

        for (Cell c :
                cells) {

            World world = Bukkit.getServer().getWorld(c.get_world());


            RegionManager rm = container.get( BukkitAdapter.adapt(world));
            Region region = WorldEditRegionConverter.convertToRegion(rm.getRegion(c.get_cellRegionID()));

            MessageSender.SendMessage(p, "Name: &l&9" + c.get_cellName());
            MessageSender.SendMessage(p, "Size: &l&9" + c.get_size().name());
            MessageSender.SendMessage(p, "Corner 1: &l&9" + region.getMinimumPoint());
            MessageSender.SendMessage(p, "Corner 2: &l&9" + region.getMaximumPoint());
            if(c.get_owner() == null){
                MessageSender.SendMessage(p, "Owner: &l&9" + "none");
            }else{
                MessageSender.SendMessage(p, "Owner: &l&9" + Bukkit.getServer().getOfflinePlayer(c.get_owner()).getName());
            }
            MessageSender.SendMessage(p, "Trusted:");

            for (UUID uuid : c.get_trusted()) {
                MessageSender.SendMessage(p, "   - &l&9" + Bukkit.getServer().getOfflinePlayer(uuid));
            }
            MessageSender.SendMessage(p, "-------------- All current cells --------------");
        }
        return true;
    }
}
