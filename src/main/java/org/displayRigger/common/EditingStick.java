package org.displayRigger.common;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.displayRigger.DisplayRigger;

public class EditingStick {
    public static final NamespacedKey EDITING_STICK_KEY = new NamespacedKey(JavaPlugin.getPlugin(DisplayRigger.class), "editing_stick");
    public static ItemStack generateEditingStick() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(EDITING_STICK_KEY, org.bukkit.persistence.PersistentDataType.BYTE, (byte) 1);
            itemMeta.setDisplayName("§f§l编辑棒");
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }
}
