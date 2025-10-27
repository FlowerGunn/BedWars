package org.screamingsandals.bedwars.listener;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.screamingsandals.bedwars.Main;

public class ItemAdderLoadChecker implements Listener {


    @EventHandler( priority = EventPriority.HIGH )
    public void onBlockBreak(ItemsAdderLoadDataEvent event) {
        Main.getInstance().setIALoaded();
    }
}
