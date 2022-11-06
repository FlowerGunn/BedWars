    package org.screamingsandals.bedwars.utils.flowergun.customgui.guiutils;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.screamingsandals.bedwars.Main;

public class InventoryGUICleaner implements Listener {


    public InventoryGUICleaner() {

    }

    @EventHandler (priority = EventPriority.LOW)
    public void onInventoryClose(InventoryCloseEvent event) {

        //blockParty.getPlugin().getLogger().info("closed inventory");

        Inventory inventory = event.getPlayer().getInventory();
        ItemStack[] playerItems = inventory.getStorageContents();
        //BlockParty.getInstance().getPlugin().getServer().getLogger().info(String.valueOf(playerItems.length));
        int player_items_length = playerItems.length;
        PersistentDataContainer itemData;
        String actionType;

        for (int i = 0; i < player_items_length; i++) {
            if (playerItems[i] == null) continue;
            itemData = playerItems[i].getItemMeta().getPersistentDataContainer();
            actionType = itemData.get(new NamespacedKey( Main.getInstance(), "GUIActionType"), PersistentDataType.STRING);
            if (actionType != null) inventory.clear(i);
//            event.getPlayer().sendMessage("Duplicates removed!");
        }
    }

}
