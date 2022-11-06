package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;

public class FireworkListener implements Listener {

    @EventHandler
    public void onFireworkUse(PlayerInteractEvent event) {



        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if ( itemStack == null ) return;

//        Bukkit.getConsoleSender().sendMessage("interact " + itemStack.getType() + "   " + player.isGliding());



        if ( itemStack.getType() == Material.FIREWORK_ROCKET && player.isGliding() ) {
            event.setCancelled(true);
            player.sendTitle("", i18n("firework_with_elytra_failed", "You can\'t do that.", false), 5, 20, 5);
        }

    }

}
