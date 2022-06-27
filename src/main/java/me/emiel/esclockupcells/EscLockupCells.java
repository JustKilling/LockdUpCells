package me.emiel.esclockupcells;

import com.jeff_media.customblockdata.CustomBlockData;
import me.emiel.esclockupcells.Commands.*;


import me.emiel.esclockupcells.Listeners.DeleteCellsGUIListener;
import me.emiel.esclockupcells.Listeners.DoorClickListener;
import me.emiel.esclockupcells.Listeners.DoorGUIListener;
import me.emiel.esclockupcells.Listeners.DoorPlaceListener;
import net.royawesome.jlibnoise.module.combiner.Add;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class EscLockupCells extends JavaPlugin {

    private static EscLockupCells _instance;

    @Override
    public void onEnable() {

        _instance = this;

        try {
            CellManager.loadCells();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.getCommand("createcell").setExecutor(new CreateCell());
        this.getCommand("listcells").setExecutor(new ListCells());
        this.getCommand("deletecell").setExecutor(new DeleteCell());
        this.getCommand("initializecells").setExecutor(new InitializeEscLockupCells());
        this.getCommand("getcelldoor").setExecutor(new GetCellDoor());
        this.getCommand("getcellvoucher").setExecutor(new GetCellVoucher());
        this.getCommand("deleteallcells").setExecutor(new RemoveAllCells());
        this.getCommand("trust").setExecutor(new AddTrust());
        this.getCommand("untrust").setExecutor(new RemoveTrust());


        this.getServer().getPluginManager().registerEvents(new DoorClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new DoorPlaceListener(), this);
        this.getServer().getPluginManager().registerEvents(new DoorGUIListener(), this);
        this.getServer().getPluginManager().registerEvents(new DeleteCellsGUIListener(), this);
       // this.getServer().getPluginManager().registerEvents(new TestListener(), this);
        this.saveDefaultConfig();


        startCellScheduler();
    }

    private void startCellScheduler() {
        //Save every 5 minutes
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                try {
                    CellManager.saveCells();
                    Bukkit.getServer().getConsoleSender().sendMessage("----------------------");
                    Bukkit.getServer().getConsoleSender().sendMessage("Saving cells....");
                    Bukkit.getServer().getConsoleSender().sendMessage("----------------------");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0L, 20L * 300); //first is delay time, second is repeating time so 5 min.

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                CellManager.removeSecond();
            }
        }, 0L, 20L); //first is delay time, second is repeating time
    }

    @Override
    public void onDisable() {
        try {
            CellManager.saveCells();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static EscLockupCells get_instance() {
        return _instance;
    }
    public static PersistentDataContainer getCustomData(Block block, Player p) {
        if(block.getBlockData() instanceof Bisected) {
            Bisected.Half half = ((Bisected)block.getBlockData()).getHalf();
            if(half == Bisected.Half.TOP) {
              return new CustomBlockData(block.getRelative(BlockFace.DOWN,1), get_instance());
            }
        }
        return new CustomBlockData(block,get_instance());
    }
    //mfnalex — Today at 01:14
    //are you talking about my lib CustomBlockData?
    //Imajin — Today at 01:14
    //he is
    //GucciFox — Today at 01:14
    //I never thought of thinking of this, ty
    //JustKilling2 — Today at 01:14
    //yes
    //mfnalex — Today at 01:14
    //well a door is 2 blocks high. If you wanna get the data and you only have the upper block, simply get the data from the lower block?
    //If you have a door, you can get the blockstate and cast it to door. Then check if its the upper or lower half. if it's the upper, just get the data fro mthe lower one instead
    //JustKilling2 — Today at 01:15
    //okay i'll try that, thanks!
    //mfnalex — Today at 01:15
    //np
    //mfnalex — Today at 01:16
    //oh wait
    //the method is in Bisected
    //and it's called getHalf
    //it returns Bisected.Half.TOP or Bisected.Half.BOTTOM
    //basically just sth like this. Always gets the block's data, unless it's the "upper version" of a 2 high block - then it get's the data of the lower half
    //    public static PersistentDataContainer getCustomData(Block block) {
    //        if(block.getState() instanceof Bisected) {
    //            Bisected.Half half = ((Bisected)block.getState()).getHalf();
    //            if(half == Bisected.Half.TOP) return new CustomBlockData(block.getRelative(BlockFace.DOWN), this);
    //        }
    //        return new CustomBlockData(block,this);
    //    }
}
