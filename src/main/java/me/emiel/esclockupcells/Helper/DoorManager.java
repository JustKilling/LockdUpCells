package me.emiel.esclockupcells.Helper;

import me.emiel.esclockupcells.CellManager;
import me.emiel.esclockupcells.EscLockupCells;
import me.emiel.esclockupcells.Models.Cell;
import me.emiel.esclockupcells.Models.Size;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DoorManager {

    public static ItemStack getDoor(String cellId){
        ItemStack door = new ItemStack(Material.IRON_DOOR);
        door.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        ItemMeta doorMeta = door.getItemMeta();
        doorMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        doorMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        doorMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"Door for cell &b&l" + CellManager.findCell(cellId).get_cellName()));

        PersistentDataContainer pdc = doorMeta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(EscLockupCells.get_instance(), "cell-door-id");
        pdc.set(key, PersistentDataType.STRING, cellId);
        door.setItemMeta(doorMeta);
        return door;
    }


    public static int getCellPrice(Size size) {
        EscLockupCells plugin = EscLockupCells.get_instance();
        switch (size){
            case Small:
                return plugin.getConfig().getInt("cellprices.small");
            case Medium:
                return plugin.getConfig().getInt("cellprices.medium");
            case Large:
                return plugin.getConfig().getInt("cellprices.large");
        }
        return 99999999;
    }
}
