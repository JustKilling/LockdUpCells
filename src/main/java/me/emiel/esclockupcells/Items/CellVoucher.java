package me.emiel.esclockupcells.Items;

import me.emiel.esclockupcells.EscLockupCells;
import me.emiel.esclockupcells.Models.Size;
import me.emiel.esclockupeconomy.EscLockupEconomy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class CellVoucher {
    public static ItemStack getVoucher (Size size){
        EscLockupCells plugin = EscLockupCells.get_instance();

        ItemStack itemStack = new ItemStack(Material.PAPER, 1);
        itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&fThis can be used to buy a &b&l" + size.name() + "&r&f cell."));
        if(itemMeta != null){
            itemMeta.setLore(lore);
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&r&6&l" + size.name() + " &r&fcell." ));
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            NamespacedKey nk = new NamespacedKey(plugin, "vouchertype");
            itemMeta.getPersistentDataContainer().set(nk, PersistentDataType.STRING, size.name());

        }


        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
