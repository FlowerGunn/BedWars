package org.screamingsandals.bedwars.utils.flowergun.customgui.guiutils;

import org.bukkit.inventory.meta.ItemMeta;
import org.screamingsandals.bedwars.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.sql.SQLException;

public class InventoryGUIClickListener implements Listener {


    public InventoryGUIClickListener() {

    }

    @EventHandler
    public void onInventoryGUIClick(InventoryClickEvent event) throws SQLException {

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        String title = event.getView().getTitle();

        if (item == null) return;
        if (item.getItemMeta() == null) return;

        for ( ItemStack itemStack : player.getOpenInventory().getTopInventory() ) {
            if (itemStack != null) {
                if ( itemStack.getItemMeta() != null ) {
                    PersistentDataContainer itemData = itemStack.getItemMeta().getPersistentDataContainer();
                    String type = itemData.get(new NamespacedKey( Main.getInstance() , "GUIActionType"), PersistentDataType.STRING);
                    if (type == null) continue;
                    if (type.equals("BLOCKER")) event.setCancelled(true);
                    break;
                }
            }
        }

        PersistentDataContainer itemData = item.getItemMeta().getPersistentDataContainer();
        String type = itemData.get(new NamespacedKey( Main.getInstance() , "GUIActionType"), PersistentDataType.STRING);
        String arg = itemData.get(new NamespacedKey( Main.getInstance(), "GUIActionArg"), PersistentDataType.STRING);
        String arg2 = itemData.get(new NamespacedKey( Main.getInstance(), "GUIActionArg2"), PersistentDataType.STRING);

        if ( type != null && event.getView().getType() == InventoryType.CHEST)
        {
//            player.sendMessage("GUI click detected!");
            event.setCancelled(true);
            Action action = null;
            if (arg2 == null)
            {
                action = new Action(player, type, arg);
            }
            else
            {
                action = new Action(player, type, arg, arg2);
            }

            if (event.isLeftClick()) action.isLeft = true;
            if (event.isRightClick()) action.isRight = true;
            if (event.isShiftClick()) action.isShift = true;

            action.start();
        }



    }
}
