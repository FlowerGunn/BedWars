package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;

public class CombustListener implements Listener {

    @EventHandler
    public void onFireworkUse(EntityCombustEvent event) {

        if (event instanceof EntityCombustByBlockEvent || event instanceof EntityCombustByEntityEvent) return;
        else event.setCancelled(true);

    }

}
