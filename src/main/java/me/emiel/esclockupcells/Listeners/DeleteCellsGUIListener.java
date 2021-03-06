package me.emiel.esclockupcells.Listeners;

import me.emiel.esclockupcells.CellManager;
import me.emiel.esclockupcells.EscLockupCells;
import me.emiel.esclockupcells.GUI.DoorOwnedGUI;
import me.emiel.esclockupcells.GUI.DoorTrustedGUI;
import me.emiel.esclockupcells.GUI.DoorUnownedGUI;
import me.emiel.esclockupcells.GUI.RemoveAllCellsGUI;
import me.emiel.esclockupcells.Models.Cell;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DeleteCellsGUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        final Player p = (Player) e.getWhoClicked();
        if (!IsDeleteCellsGUI(e.getInventory(), p)) return;
        e.setCancelled(true);
        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        RemoveAllCellsGUI.clicked(p, e.getRawSlot());
    }


    private boolean IsDeleteCellsGUI(Inventory inventory, HumanEntity p) {
        ItemStack item = inventory.getItem(0);
        if(item == null) return false;
        ItemMeta im = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(EscLockupCells.get_instance(), "gui-id");
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        if(pdc.has(key, PersistentDataType.STRING)){
            if(pdc.get(key, PersistentDataType.STRING).equalsIgnoreCase("deleteallcells")) return true;
        }
        return false;
    }
}
