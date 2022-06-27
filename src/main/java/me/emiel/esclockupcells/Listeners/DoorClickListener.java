package me.emiel.esclockupcells.Listeners;

import me.emiel.esclockupcells.CellManager;
import me.emiel.esclockupcells.EscLockupCells;
import me.emiel.esclockupcells.GUI.DoorOwnedGUI;
import me.emiel.esclockupcells.GUI.DoorTrustedGUI;
import me.emiel.esclockupcells.GUI.DoorUnownedGUI;
import me.emiel.esclockupcells.Helper.MessageSender;
import me.emiel.esclockupcells.Models.Cell;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DoorClickListener implements Listener {



    @EventHandler
    public void onDoorClick(PlayerInteractEvent e) {
        if(e.getHand().equals(EquipmentSlot.OFF_HAND))  return;
        if(e.getClickedBlock() == null) return;
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            EscLockupCells plugin = EscLockupCells.get_instance();

                PersistentDataContainer customBlockData = EscLockupCells.getCustomData(e.getClickedBlock(), p);

                NamespacedKey nk = new NamespacedKey(plugin, "cell-door-id");
                if(!customBlockData.has(nk, PersistentDataType.STRING)) return;
                String cellId = customBlockData.get(nk, PersistentDataType.STRING);
                Cell cell =CellManager.findCell(cellId);
                if(cell == null) return;
                if(cell.get_owner() == null){

                }else if(!CellManager.isPlayerTrusted(cellId, p.getUniqueId())){
                    return;
                }

                Block b = e.getClickedBlock();
                BlockData blockData = b.getBlockData();
                Openable door = (Openable) blockData ;
                door.setOpen(!door.isOpen());
                b.setBlockData(door);

        }else if(e.getAction() == Action.LEFT_CLICK_BLOCK){
            if(e.getPlayer().isSneaking()){
                EscLockupCells plugin = EscLockupCells.get_instance();
                PersistentDataContainer customBlockData = EscLockupCells.getCustomData(e.getClickedBlock(), p);
                NamespacedKey nk = new NamespacedKey(plugin, "cell-door-id");
                if(!customBlockData.has(nk, PersistentDataType.STRING)) return;
                String cellId = customBlockData.get(nk, PersistentDataType.STRING);
                Cell cell =CellManager.findCell(cellId);
                if(cell == null) return;

                MessageSender.SendMessageWithPrefix(p, "Broken cell door: &b" + cell.get_cellName());
                customBlockData.remove(nk);
                nk = new NamespacedKey(plugin, "cell-door-id");
                customBlockData.remove(nk);

            }else{
                EscLockupCells plugin = EscLockupCells.get_instance();
                PersistentDataContainer customBlockData = EscLockupCells.getCustomData(e.getClickedBlock(), p);
                NamespacedKey nk = new NamespacedKey(plugin, "cell-door-id");
                if(!customBlockData.has(nk, PersistentDataType.STRING)) return;
                String cellId = customBlockData.get(nk, PersistentDataType.STRING);
                Cell cell =CellManager.findCell(cellId);
                if(cell == null) return;
                e.setCancelled(true);
                if(cell.get_owner() == null){
                    DoorUnownedGUI gui = new DoorUnownedGUI();
                    gui.openInventory(p, cell);
                } else if(CellManager.isPlayerTrusted(cellId, p.getUniqueId())){
                    DoorTrustedGUI doorGUI = new DoorTrustedGUI();
                    doorGUI.openInventory(p, cell);
                }else {
                    DoorOwnedGUI gui = new DoorOwnedGUI();
                    gui.openInventory(p, cell);
                }


            }


        }
    }
}
